package me.nik.combatplus.modules.impl;

import me.nik.combatplus.api.events.CombatPlusLoadEvent;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.StatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OldPvP extends Module {

    public OldPvP() {
        super("Old PvP", Config.Setting.OLD_PVP.getBoolean());
    }

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        StatUtils.setAttackSpeed(e.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        StatUtils.resetAttackSpeed(e.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        StatUtils.setAttackSpeed(e.getPlayer());
    }

    @EventHandler
    public void onStartup(CombatPlusLoadEvent e) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p == null) continue; //Just in case
            StatUtils.setAttackSpeed(p);
        }
    }
}