package me.nik.combatplus.listeners;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.ResetStats;
import me.nik.combatplus.utils.SetAttackSpeed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AttackSpeed implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        if (!Config.get().getBoolean("combat.settings.old_pvp")) return;
        new SetAttackSpeed().setAttackSpd(e.getPlayer());
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent e){
        if (!Config.get().getBoolean("combat.settings.old_pvp")) return;
        new ResetStats().Reset(e.getPlayer());
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChange(PlayerChangedWorldEvent e){
        if (!Config.get().getBoolean("combat.settings.old_pvp")) return;
        new SetAttackSpeed().setAttackSpd(e.getPlayer());
    }
}
