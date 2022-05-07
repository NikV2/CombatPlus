package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.managers.Permissions;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.TaskUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EnderpearlCooldown extends Module {

    private final Map<UUID, Long> cooldowns = new ConcurrentHashMap<>();

    public EnderpearlCooldown() {
        super(Config.Setting.ENDERPEARL_ENABLED.getBoolean());

        TaskUtils.taskTimerAsync(() -> this.cooldowns.keySet().forEach(uuid -> {

            Player player = Bukkit.getPlayer(uuid);

            if (player == null) {

                this.cooldowns.remove(uuid);

                return;
            }

            long secondsleft = getSecondsLeft(uuid);

            if (secondsleft == 0L || !Config.Setting.ENDERPEARL_ACTIONBAR.getBoolean()) return;

            String message = MsgType.ENDERPEARL_COOLDOWN_ACTIONBAR.getMessage().replaceAll("%seconds%", String.valueOf(secondsleft));

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

        }), 0L, 20L);
    }

    public long getSecondsLeft(UUID uuid) {

        long secondsLeft = ((this.cooldowns.get(uuid) / 1000L) + Config.Setting.ENDERPEARL_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000L);

        if (secondsLeft < 1L) {

            this.cooldowns.remove(uuid);

            return 0L;
        }

        return secondsLeft;
    }

    public String getCooldown(UUID uuid) {

        if (this.cooldowns.containsKey(uuid)) {

            long secondsleft = getSecondsLeft(uuid);

            return secondsleft == 0L ? "Ready" : (secondsleft + "s");
        }

        return "Ready";
    }

    @EventHandler(ignoreCancelled = true)
    public void onLaunch(ProjectileLaunchEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player)
                || !(e.getEntity().getType() == EntityType.ENDER_PEARL)
                || Config.Setting.ENDERPEARL_DISABLED_WORLDS.getStringList()
                .stream().anyMatch(world -> world.equals(e.getEntity().getLocation().getWorld().getName()))
                || (!Config.Setting.DISABLE_BYPASS_PERMISSIONS.getBoolean()
                && ((Player) e.getEntity().getShooter()).hasPermission(Permissions.BYPASS_EPEARL.getPermission())))
            return;

        Player player = (Player) e.getEntity().getShooter();

        final UUID uuid = player.getUniqueId();

        if (cooldowns.containsKey(uuid)) {

            long secondsleft = getSecondsLeft(uuid);

            if (secondsleft == 0L) return;

            e.setCancelled(true);

            player.sendMessage(MsgType.ENDERPEARL_COOLDOWN.getMessage().replaceAll("%seconds%", String.valueOf(secondsleft)));

        } else {

            cooldowns.put(uuid, System.currentTimeMillis());

            debug(player, "&6Added to cooldown");
        }
    }
}