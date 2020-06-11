package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.MiscUtils;
import me.nik.combatplus.utils.WorldUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerRegen implements Listener {

    private final CombatPlus plugin;
    private final WorldUtils worldUtils;

    private final Map<UUID, Long> healTimes = new HashMap<>();
    private final int oldRegenFrequency;
    private final int oldRegenAmount;
    private final float oldRegenExhaustion;

    public PlayerRegen(CombatPlus plugin) {
        this.plugin = plugin;
        this.worldUtils = new WorldUtils(plugin);
        this.oldRegenFrequency = plugin.getConfig().getInt("advanced.settings.old_regen.frequency");
        this.oldRegenAmount = plugin.getConfig().getInt("advanced.settings.old_regen.amount");
        this.oldRegenExhaustion = (float) plugin.getConfig().getInt("advanced.settings.old_regen.exhaustion");
    }

    /*
     This Listener Makes the player's health regen work just like in 1.8
     */

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegen(EntityRegainHealthEvent e) {
        if (e.getEntityType() != EntityType.PLAYER || e.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED)
            return;
        final Player p = (Player) e.getEntity();
        if (worldUtils.combatDisabledWorlds(p)) return;
        final UUID playerID = p.getUniqueId();
        double playerHealth = p.getHealth();
        double playerSaturation = p.getSaturation();
        e.setCancelled(true);
        long currentTime = System.currentTimeMillis() / 1000;
        long lastHealTime = healTimes.computeIfAbsent(playerID, id -> System.currentTimeMillis() / 1000);
        if (currentTime - lastHealTime < oldRegenFrequency) return;
        final double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (playerHealth < maxHealth) {
            p.setHealth(MiscUtils.clamp(playerHealth + oldRegenAmount, maxHealth));
            healTimes.put(playerID, currentTime);
        }
        final float previousExhaustion = p.getExhaustion();
        final float exhaustionToApply = oldRegenExhaustion;
        new BukkitRunnable() {

            @Override
            public void run() {
                p.setExhaustion(previousExhaustion + exhaustionToApply);
                Messenger.debug(p, "&3Regeneration &f&l>> &6Old exhaustion: &a" + previousExhaustion + " &6New exhaustion: &a" + exhaustionToApply + " &6Saturation: &a" + playerSaturation);
            }
        }.runTaskLater(plugin, 1);
    }

    @EventHandler
    public void cleanHashMap(PlayerQuitEvent e) {
        UUID pUuid = e.getPlayer().getUniqueId();
        if (!healTimes.containsKey(pUuid)) return;
        healTimes.remove(pUuid);
    }
}