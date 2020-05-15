package me.nik.combatplus.listeners;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.ResetStats;
import me.nik.combatplus.utils.SetAttackSpeed;
import me.nik.combatplus.utils.SetCustomHealth;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AttributesSet implements Listener {

    /*
     Changes the attribute of the player to the Old Attack Speed (On Join)
     */

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        if (Config.get().getBoolean("combat.settings.old_pvp")) {
            final Player player = e.getPlayer();
            new SetAttackSpeed().setAttackSpd(player);
        } else {
            final Player player = e.getPlayer();
            new ResetStats().resetAttackSpeed(player);
        }

        if (Config.get().getBoolean("custom.player_health.enabled")) {
            final Player player = e.getPlayer();
            new SetCustomHealth().setHealth(player);
        } else {
            final Player player = e.getPlayer();
            new ResetStats().resetMaxHealth(player);
        }

    }

    /*
     Resets the attribute of the player to the New Attack Speed (On Leave)
     */

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onLeave(PlayerQuitEvent e) {
        if (Config.get().getBoolean("combat.settings.old_pvp")) {
            final Player player = e.getPlayer();
            new ResetStats().resetAttackSpeed(player);
        }
        if (Config.get().getBoolean("custom.player_health.enabled")) {
            final Player player = e.getPlayer();
            new ResetStats().resetMaxHealth(player);
        }
    }

    /*
     Changes the attribute of the player to the Old Attack Speed (On World Change)
     */

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (!Config.get().getBoolean("combat.settings.old_pvp")) return;
        final Player player = e.getPlayer();
        new SetAttackSpeed().setAttackSpd(player);
    }
}
