package me.nik.combatplus.modules.impl;

import me.nik.combatplus.Permissions;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.TaskUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CombatLog extends Module {

    private final Map<UUID, Long> taggedPlayers;
    private final int timer;
    private final boolean disableFly;
    private final boolean actionbar;
    private final boolean tagForMobs;
    private final boolean tagForProjectiles;
    private final List<String> excludedCommands;
    private final boolean broadcast;
    private boolean isRunning;

    public CombatLog() {
        super("CombatLog", Config.Setting.COMBATLOG_ENABLED.getBoolean());

        this.taggedPlayers = new ConcurrentHashMap<>();

        this.timer = Config.Setting.COMBATLOG_COOLDOWN.getInt();
        this.disableFly = Config.Setting.COMBATLOG_DISABLE_FLY.getBoolean();
        this.actionbar = Config.Setting.COMBATLOG_ACTIONBAR.getBoolean();
        this.tagForMobs = Config.Setting.COMBATLOG_MOBS.getBoolean();
        this.tagForProjectiles = Config.Setting.COMBATLOG_PROJECTILES.getBoolean();
        this.excludedCommands = Config.Setting.COMBATLOG_COMMANDS_EXCLUDED.getStringList();
        this.broadcast = Config.Setting.COMBATLOG_BROADCAST.getBoolean();

        if (this.isRunning) return; //Just in case since i did this the lazy way

        this.isRunning = true;
        TaskUtils.taskTimerAsync(() -> {
            if (this.taggedPlayers.isEmpty()) return;

            for (UUID uuid : this.taggedPlayers.keySet()) {
                Player p = Bukkit.getPlayer(uuid);
                if (p == null) continue;

                long secondsLeft = ((taggedPlayers.get(uuid) / 1000) + timer) - (System.currentTimeMillis() / 1000);
                if (secondsLeft > 0) {
                    if (actionbar) {
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(MsgType.COMBATLOG_ACTIONBAR.getMessage().replace("%seconds%", String.valueOf(secondsLeft))));
                    }
                } else {
                    taggedPlayers.remove(uuid);
                    p.sendMessage(MsgType.COMBATLOG_UNTAGGED.getMessage());
                }
            }
        }, 20, 20);
    }

    public String getCooldown(UUID uuid) {
        if (taggedPlayers.containsKey(uuid)) {
            long secondsleft = ((taggedPlayers.get(uuid) / 1000) + Config.Setting.COMBATLOG_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
            if (secondsleft < 1) {
                taggedPlayers.remove(uuid);
                return "Ready";
            }
            return secondsleft + "s";
        }
        return "Ready";
    }

    private boolean isTagged(Player player) {
        return taggedPlayers.containsKey(player.getUniqueId());
    }

    private void tagPlayer(Player player) {

        if (player.hasPermission(Permissions.BYPASS_COMBATLOG.getPermission())) return;

        if (disableFly) {
            player.setFlying(false);
            player.setAllowFlight(false);
        }

        if (!taggedPlayers.containsKey(player.getUniqueId())) {
            player.sendMessage(MsgType.COMBATLOG_TAGGED.getMessage());
        }

        taggedPlayers.put(player.getUniqueId(), System.currentTimeMillis());
    }

    private void unTagPlayer(Player player) {
        taggedPlayers.remove(player.getUniqueId());
    }

    //Stuff

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCombat(EntityDamageByEntityEvent e) {

        final Entity target = e.getEntity();

        if (!(e.getEntity() instanceof LivingEntity)) return;

        if (e.getDamager() instanceof Projectile) {

            if (!tagForProjectiles) return;

            final ProjectileSource shooter = ((Projectile) e.getDamager()).getShooter();

            if (!(shooter instanceof Player)) return;

            final Player p = (Player) shooter;

            if (!tagForMobs && target instanceof Mob) return;

            tagPlayer(p);

            if (!(target instanceof Player)) return;
            tagPlayer((Player) target);
        } else if (e.getDamager() instanceof Player) {

            if (!tagForMobs && target instanceof Mob) return;

            final Player p = (Player) e.getDamager();

            tagPlayer(p);

            if (!(target instanceof Player)) return;
            tagPlayer((Player) target);
        } else if (e.getDamager() instanceof Mob) {
            if (!tagForMobs) return;
            if (!(target instanceof Player)) return;
            final Player p = (Player) e.getEntity();

            tagPlayer(p);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (!Config.Setting.COMBATLOG_COMMANDS_ENABLED.getBoolean()) return;

        final Player p = e.getPlayer();

        if (!isTagged(p)) return;

        final String command = e.getMessage().replace("/", "");

        for (String cmd : excludedCommands) {
            if (cmd.contains(command)) return;
        }

        e.setCancelled(true);
        p.sendMessage(MsgType.COMBATLOG_COMMAND.getMessage());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {

        final Player p = e.getPlayer();

        if (!isTagged(p)) return;

        p.setHealth(0);
        unTagPlayer(p);

        if (broadcast) {
            Bukkit.broadcastMessage(MsgType.COMBATLOG_BROADCAST.getMessage().replace("%player%", p.getName()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent e) {
        if (!Config.Setting.COMBATLOG_PREVENT_DROPPING_ITEMS.getBoolean()) return;

        final Player p = e.getPlayer();

        if (!isTagged(p)) return;

        e.setCancelled(true);
        p.sendMessage(MsgType.COMBATLOG_ITEM_DROP.getMessage());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemPick(EntityPickupItemEvent e) {
        if (!Config.Setting.COMBATLOG_PREVENT_PICKING_ITEMS.getBoolean()) return;
        if (!(e.getEntity() instanceof Player)) return;
        final Player p = (Player) e.getEntity();

        if (!isTagged(p)) return;

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent e) {
        if (!Config.Setting.COMBATLOG_PREVENT_TELEPORTATIONS.getBoolean()) return;
        final Player p = e.getPlayer();

        if (!isTagged(p)) return;

        e.setCancelled(true);
        p.sendMessage(MsgType.COMBATLOG_TELEPORT.getMessage());
    }
}