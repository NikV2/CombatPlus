package me.nik.combatplus.listeners;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.ResetStats;
import me.nik.combatplus.utils.SetAttackSpeed;
import me.nik.combatplus.utils.SetCustomHealth;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AttributesSet implements Listener {

    private final SetAttackSpeed setAttackSpeed = new SetAttackSpeed();
    private final ResetStats resetStats = new ResetStats();
    private final SetCustomHealth setCustomHealth = new SetCustomHealth();

    private final boolean isOldPvpEnabled = Config.get().getBoolean("combat.settings.old_pvp");
    private final boolean isCustomHealthEnabled = Config.get().getBoolean("custom.player_health.enabled");

    /*
     Changes the attribute of the player to the Old Attack Speed (On Join)
     */

    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        if (isOldPvpEnabled) {
            setAttackSpeed.setAttackSpd(e.getPlayer());
        } else {
            resetStats.resetAttackSpeed(e.getPlayer());
        }

        if (isCustomHealthEnabled) {
            setCustomHealth.setHealth(e.getPlayer());
        } else {
            resetStats.resetMaxHealth(e.getPlayer());
        }

    }

    /*
     Resets the attribute of the player to the New Attack Speed (On Leave)
     */

    @EventHandler(ignoreCancelled = true)
    public void onLeave(PlayerQuitEvent e) {
        if (isOldPvpEnabled) {
            resetStats.resetAttackSpeed(e.getPlayer());
        }
        if (isCustomHealthEnabled) {
            resetStats.resetMaxHealth(e.getPlayer());
        }
    }

    /*
     Changes the attribute of the player to the Old Attack Speed (On World Change)
     */

    @EventHandler(ignoreCancelled = true)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (!isOldPvpEnabled) return;
        setAttackSpeed.setAttackSpd(e.getPlayer());
    }
}
