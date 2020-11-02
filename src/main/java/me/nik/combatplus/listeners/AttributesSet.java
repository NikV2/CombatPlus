package me.nik.combatplus.listeners;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.StatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AttributesSet implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        if (Config.Setting.OLD_PVP.getBoolean()) {
            StatUtils.setAttackSpeed(e.getPlayer());
        } else {
            StatUtils.resetAttackSpeed(e.getPlayer());
        }

        if (Config.Setting.CUSTOM_PLAYER_HEALTH_ENABLED.getBoolean()) {
            StatUtils.setMaxHealth(e.getPlayer());
        } else {
            StatUtils.resetMaxHealth(e.getPlayer());
        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (Config.Setting.OLD_PVP.getBoolean()) {
            StatUtils.resetAttackSpeed(e.getPlayer());
        }
        if (Config.Setting.CUSTOM_PLAYER_HEALTH_ENABLED.getBoolean()) {
            StatUtils.resetMaxHealth(e.getPlayer());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (!Config.Setting.OLD_PVP.getBoolean()) return;
        StatUtils.setAttackSpeed(e.getPlayer());
    }
}