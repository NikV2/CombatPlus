package me.nik.combatplus.listeners;

import me.nik.combatplus.api.Manager;
import me.nik.combatplus.utils.ResetStats;
import me.nik.combatplus.utils.SetAttackSpeed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AttackSpeed extends Manager {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        if (!configBoolean("combat.settings.old_pvp")) return;
        final Player player = e.getPlayer();
        new SetAttackSpeed().setAttackSpd(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent e) {
        if (!configBoolean("combat.settings.old_pvp")) return;
        final Player player = e.getPlayer();
        new ResetStats().Reset(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (!configBoolean("combat.settings.old_pvp")) return;
        final Player player = e.getPlayer();
        new SetAttackSpeed().setAttackSpd(player);
    }
}
