package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.Manager;
import me.nik.combatplus.utils.ResetStats;
import me.nik.combatplus.utils.SetAttackSpeed;
import me.nik.combatplus.utils.SetCustomHealth;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AttributesSet extends Manager {

    public AttributesSet(CombatPlus plugin) {
        super(plugin);
    }

    // Changes the attribute of the player to the Old Attack Speed (On Join)

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        if (configBoolean("combat.settings.old_pvp")) {
            final Player player = e.getPlayer();
            new SetAttackSpeed(plugin).setAttackSpd(player);
        } else {
            final Player player = e.getPlayer();
            new ResetStats(plugin).resetAttackSpeed(player);
        }

        if (configBoolean("custom.player_health.enabled")) {
            final Player player = e.getPlayer();
            new SetCustomHealth(plugin).setHealth(player);
        } else {
            final Player player = e.getPlayer();
            new ResetStats(plugin).resetMaxHealth(player);
        }

    }
    // Resets the attribute of the player to the New Attack Speed (On Leave)

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLeave(PlayerQuitEvent e) {
        if (configBoolean("combat.settings.old_pvp")) {
            final Player player = e.getPlayer();
            new ResetStats(plugin).resetAttackSpeed(player);
        }
        if (configBoolean("custom.player_health.enabled")) {
            final Player player = e.getPlayer();
            new ResetStats(plugin).resetMaxHealth(player);
        }
    }

    // Changes the attribute of the player to the Old Attack Speed (On World Change)

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (!configBoolean("combat.settings.old_pvp")) return;
        final Player player = e.getPlayer();
        new SetAttackSpeed(plugin).setAttackSpd(player);
    }
}
