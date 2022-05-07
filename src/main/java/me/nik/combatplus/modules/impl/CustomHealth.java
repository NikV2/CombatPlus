package me.nik.combatplus.modules.impl;

import me.nik.combatplus.api.events.CombatPlusLoadEvent;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CustomHealth extends Module {
    public CustomHealth() {
        super(Config.Setting.CUSTOM_PLAYER_HEALTH_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        setMaxHealth(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent e) {

        Player player = e.getPlayer();

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Config.Setting.CUSTOM_PLAYER_HEALTH_DEFAULT_MAX_HEALTH.getDouble());

        player.saveData();
    }

    @EventHandler
    public void onStartup(CombatPlusLoadEvent e) {
        Bukkit.getOnlinePlayers().forEach(this::setMaxHealth);
    }

    private void setMaxHealth(Player player) {

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Config.Setting.CUSTOM_PLAYER_HEALTH_HEALTH.getDouble());

        player.saveData();
    }
}