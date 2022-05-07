package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.managers.Permissions;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.TaskUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CombatLog extends Module {

    private final Map<UUID, Long> taggedPlayers = new ConcurrentHashMap<>();

    public CombatLog() {
        super(Config.Setting.COMBATLOG_ENABLED.getBoolean());

        TaskUtils.taskTimerAsync(() -> this.taggedPlayers.keySet().forEach(uuid -> {

            Player player = Bukkit.getPlayer(uuid);

            if (player == null) {

                this.taggedPlayers.remove(uuid);

                return;
            }

            long secondsleft = getSecondsLeft(uuid);

            if (secondsleft == 0L || !Config.Setting.COMBATLOG_ACTIONBAR.getBoolean()) return;

            String message = MsgType.COMBATLOG_ACTIONBAR.getMessage().replaceAll("%seconds%", String.valueOf(secondsleft));

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

        }), 0L, 20L);
    }

    private long getSecondsLeft(UUID uuid) {

        long secondsLeft = ((this.taggedPlayers.get(uuid) / 1000L) + Config.Setting.COMBATLOG_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000L);

        if (secondsLeft < 1L) {

            this.taggedPlayers.remove(uuid);

            return 0L;
        }

        return secondsLeft;
    }

    public String getCooldown(UUID uuid) {

        if (this.taggedPlayers.containsKey(uuid)) {

            long secondsleft = getSecondsLeft(uuid);

            return secondsleft == 0L ? "Ready" : (secondsleft + "s");
        }

        return "Ready";
    }

    private void tagPlayer(Player player) {
        if (Config.Setting.COMBATLOG_DISABLED_WORLDS.getStringList().stream().anyMatch(world -> world.equals(player.getWorld().getName()))
                || (!Config.Setting.DISABLE_BYPASS_PERMISSIONS.getBoolean()
                && player.hasPermission(Permissions.BYPASS_COMBATLOG.getPermission()))) return;

        if (Config.Setting.COMBATLOG_DISABLE_FLY.getBoolean() && (player.getAllowFlight() || player.isFlying())) {

            player.setFlying(false);

            player.setAllowFlight(false);

            debug(player, "&6Disabled flight");
        }

        if (!this.taggedPlayers.containsKey(player.getUniqueId())) {
            player.sendMessage(MsgType.COMBATLOG_TAGGED.getMessage());
        }

        this.taggedPlayers.put(player.getUniqueId(), System.currentTimeMillis());

        debug(player, "&6Tagged");
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCombat(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity)) return;

        Entity target = e.getEntity();

        if (!Config.Setting.COMBATLOG_MOBS.getBoolean() && (!(target instanceof Player))) return;

        Entity damager = e.getDamager();

        Player player = null;

        if (damager instanceof Projectile) {

            if (((Projectile) damager).getShooter() instanceof Player && Config.Setting.COMBATLOG_PROJECTILES.getBoolean()) {

                player = (Player) ((Projectile) damager).getShooter();
            }

        } else if (damager instanceof Player) {

            player = (Player) damager;

        } else {

            if (Config.Setting.COMBATLOG_MOBS.getBoolean() && target instanceof Player) {

                player = (Player) target;
            }
        }

        if (player == null) return;

        tagPlayer(player);

        if (target instanceof Player) tagPlayer((Player) target);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (!Config.Setting.COMBATLOG_COMMANDS_ENABLED.getBoolean() || !this.taggedPlayers.containsKey(e.getPlayer().getUniqueId()))
            return;

        String command = e.getMessage().replace("/", "");

        if (Config.Setting.COMBATLOG_COMMANDS_EXCLUDED.getStringList().stream().noneMatch(excluded -> excluded.contains(command))) {

            e.setCancelled(true);

            Player player = e.getPlayer();

            player.sendMessage(MsgType.COMBATLOG_COMMAND.getMessage());

            debug(player, "&6Cancelled: &a" + e.getEventName());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {

        Player player = e.getPlayer();

        UUID uuid = player.getUniqueId();

        if (!this.taggedPlayers.containsKey(uuid)) return;

        player.setHealth(0);

        this.taggedPlayers.remove(uuid);

        if (Config.Setting.COMBATLOG_BROADCAST.getBoolean()) {
            Bukkit.broadcastMessage(MsgType.COMBATLOG_BROADCAST.getMessage().replace("%player%", player.getName()));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent e) {
        if (!Config.Setting.COMBATLOG_PREVENT_DROPPING_ITEMS.getBoolean() || !this.taggedPlayers.containsKey(e.getPlayer().getUniqueId()))
            return;

        e.setCancelled(true);

        Player player = e.getPlayer();

        player.sendMessage(MsgType.COMBATLOG_ITEM_DROP.getMessage());

        debug(player, "&6Cancelled: &a" + e.getEventName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent e) {

        Player player = e.getEntity();

        this.taggedPlayers.remove(player.getUniqueId());

        debug(player, "&6Removed from the cooldown");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemPick(EntityPickupItemEvent e) {
        if (!Config.Setting.COMBATLOG_PREVENT_PICKING_ITEMS.getBoolean()
                || !(e.getEntity() instanceof Player)
                || !this.taggedPlayers.containsKey(e.getEntity().getUniqueId())) return;

        e.setCancelled(true);

        debug((Player) e.getEntity(), "&6Cancelled: &a" + e.getEventName());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent e) {
        if (!Config.Setting.COMBATLOG_PREVENT_TELEPORTATIONS.getBoolean()
                || !this.taggedPlayers.containsKey(e.getPlayer().getUniqueId())
                //Laggy - Buggy occasions
                || e.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN) return;

        e.setCancelled(true);

        Player player = e.getPlayer();

        player.sendMessage(MsgType.COMBATLOG_TELEPORT.getMessage());

        debug(player, "&6Cancelled: &a" + e.getEventName());
    }
}