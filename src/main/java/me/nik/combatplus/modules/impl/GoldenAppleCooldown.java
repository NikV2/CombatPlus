package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.managers.Permissions;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.MiscUtils;
import me.nik.combatplus.utils.TaskUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GoldenAppleCooldown extends Module {

    private final Map<UUID, Long> cooldowns = new ConcurrentHashMap<>();

    public GoldenAppleCooldown() {
        super(Config.Setting.GOLDEN_APPLE_ENABLED.getBoolean());

        TaskUtils.taskTimerAsync(() -> this.cooldowns.keySet().forEach(uuid -> {

            Player player = Bukkit.getPlayer(uuid);

            if (player == null) {

                this.cooldowns.remove(uuid);

                return;
            }

            long secondsleft = getSecondsLeft(uuid);

            if (secondsleft == 0L || !Config.Setting.GOLDEN_APPLE_ACTIONBAR.getBoolean()) return;

            String message = MsgType.GOLDEN_APPLE_COOLDOWN_ACTIONBAR.getMessage().replaceAll("%seconds%", String.valueOf(secondsleft));

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

        }), 0L, 20L);
    }

    private long getSecondsLeft(UUID uuid) {

        long secondsLeft = ((this.cooldowns.get(uuid) / 1000L) + Config.Setting.GOLDEN_APPLE_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000L);

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
    public void onEatGoldenApple(PlayerItemConsumeEvent e) {
        if (Config.Setting.GOLDEN_APPLE_DISABLED_WORLDS.getStringList().stream().anyMatch(world -> world.equals(e.getPlayer().getWorld().getName()))
                || !e.getItem().isSimilar(MiscUtils.GOLDEN_APPLE)
                || (!Config.Setting.DISABLE_BYPASS_PERMISSIONS.getBoolean()
                && e.getPlayer().hasPermission(Permissions.BYPASS_GAPPLE.getPermission()))) return;

        final UUID uuid = e.getPlayer().getUniqueId();

        final Player player = e.getPlayer();

        if (this.cooldowns.containsKey(uuid)) {

            long secondsleft = getSecondsLeft(uuid);

            if (secondsleft == 0L) return;

            e.setCancelled(true);

            player.sendMessage(MsgType.GOLDEN_APPLE_COOLDOWN.getMessage().replaceAll("%seconds%", String.valueOf(secondsleft)));

        } else {

            this.cooldowns.put(uuid, System.currentTimeMillis());

            debug(player, "&6Added to cooldown");
        }
    }
}