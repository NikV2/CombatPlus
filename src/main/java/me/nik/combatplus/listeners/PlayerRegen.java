package me.nik.combatplus.listeners;

import me.nik.combatplus.api.Manager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerRegen extends Manager {
    private final Map<UUID, Long> healTimes = new HashMap<>();

    // This Listener Makes the player's health regen work just like in 1.8

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegen(EntityRegainHealthEvent e) {
        if (e.getEntityType() != EntityType.PLAYER || e.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED) {
            return;
        }
        final Player p = (Player) e.getEntity();
        final UUID playerID = p.getUniqueId();
        double playerHealth = p.getHealth();
        double playerSaturation = p.getSaturation();
        if (combatDisabledWorlds(p)) return;
        e.setCancelled(true);
        long currentTime = System.currentTimeMillis() / 1000;
        long lastHealTime = healTimes.computeIfAbsent(playerID, id -> System.currentTimeMillis() / 1000);
        if (currentTime - lastHealTime < configInt("advanced.settings.old_regen.frequency")) return;
        final double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (playerHealth < maxHealth) {
            p.setHealth(clamp(playerHealth + configInt("advanced.settings.old_regen.amount"), 0.0, maxHealth));
            healTimes.put(playerID, currentTime);
            //disable heal check from anticheat
        }
        final float previousExhaustion = p.getExhaustion();
        final float exhaustionToApply = (float) configDouble("advanced.settings.old_regen.exhaustion");
        if (!isAsync()) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    p.setExhaustion(previousExhaustion + exhaustionToApply);
                    debug(p, "&3Regeneration &f&l>> &6Old exhaustion: &a" + previousExhaustion + " &6New exhaustion: &a" + exhaustionToApply + " &6Saturation: &a" + playerSaturation);
                }
            }.runTaskLater(plugin, 1);
        } else {
            new BukkitRunnable() {

                @Override
                public void run() {
                    p.setExhaustion(previousExhaustion + exhaustionToApply);
                    debug(p, "&3Regeneration &f&l>> &6Old exhaustion: &a" + previousExhaustion + " &6New exhaustion: &a" + exhaustionToApply + " &6Saturation: &a" + playerSaturation + " &bAsync: true");
                }
            }.runTaskLaterAsynchronously(plugin, 1);
        }
    }

    private double clamp(double value, double min, double max) {
        double realMin = Math.min(min, max);
        double realMax = Math.max(min, max);
        if (value < realMin) {
            value = realMin;
        }
        if (value > realMax) {
            value = realMax;
        }
        return value;
    }
}
