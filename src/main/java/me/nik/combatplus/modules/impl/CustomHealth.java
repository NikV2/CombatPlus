package me.nik.combatplus.modules.impl;

import me.nik.combatplus.api.events.CombatPlusLoadEvent;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.StatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CustomHealth extends Module {
    public CustomHealth() {
        super("Custom Player Health", Config.Setting.CUSTOM_PLAYER_HEALTH_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(final PlayerJoinEvent e) {
        StatUtils.setMaxHealth(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLeave(final PlayerQuitEvent e) {
        StatUtils.resetMaxHealth(e.getPlayer());
    }

    @EventHandler
    public void onStartup(final CombatPlusLoadEvent e) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p == null) continue; //Just in case
            StatUtils.setMaxHealth(p);
        }
    }
}