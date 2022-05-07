package me.nik.combatplus.modules.impl;

import me.nik.combatplus.api.events.CombatPlusLoadEvent;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CustomAttackSpeed extends Module {

    public CustomAttackSpeed() {
        super(Config.Setting.CUSTOM_ATTACK_SPEED_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        setAttackSpeed(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Config.Setting.CUSTOM_ATTACK_SPEED_DEFAULT_ATTACK_SPEED.getDouble());
        player.saveData();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        setAttackSpeed(e.getPlayer());
    }

    @EventHandler
    public void onStartup(CombatPlusLoadEvent e) {
        Bukkit.getOnlinePlayers().forEach(this::setAttackSpeed);
    }

    private void setAttackSpeed(Player player) {
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Config.Setting.CUSTOM_ATTACK_SPEED_ATTACK_SPEED.getDouble());
        player.saveData();
    }
}