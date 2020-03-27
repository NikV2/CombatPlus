package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerRegen implements Listener {
    private Map<UUID, Long> healTimes = new HashMap<>();
    CombatPlus plugin = CombatPlus.getPlugin(CombatPlus.class);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegen(EntityRegainHealthEvent e) {
        if (!Config.get().getBoolean("combat.settings.old_player_regen")) return;
        if (e.getEntityType() != EntityType.PLAYER || e.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED)
            return;
        final Player p = (Player) e.getEntity();
        for (String world : Config.get().getStringList("combat.settings.disabled_worlds")) {
            if (e.getEntity().getWorld().getName().equalsIgnoreCase(world)) return;
        }
        e.setCancelled(true);
        long currentTime = System.currentTimeMillis() / 1000;
        long lastHealTime = healTimes.computeIfAbsent(p.getUniqueId(), id -> System.currentTimeMillis() / 1000);
        if (currentTime - lastHealTime < Config.get().getInt("advanced.settings.old_regen.frequency")) return;
        double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (p.getHealth() < maxHealth) {
            p.setHealth(clamp(p.getHealth() + Config.get().getInt("advanced.settings.old_regen.amount"), 0.0, maxHealth));
            healTimes.put(p.getUniqueId(), currentTime);
            //disable heal check from anticheat
        }
        final float previousExhaustion = p.getExhaustion();
        final float exhaustionToApply = (float) Config.get().getDouble("advanced.settings.old_regen.exhaustion");
        if (!Config.get().getBoolean("settings.async")) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    p.setExhaustion(previousExhaustion + exhaustionToApply);
                    if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
                        if (p.hasPermission("cp.admin")) {
                            p.sendMessage(Messenger.prefix(ChatColor.AQUA + "Listener: " + "PlayerRegen" + ChatColor.GREEN + " Old exhaustion: " + previousExhaustion + ChatColor.YELLOW + " New exhaustion: " + p.getExhaustion() + ChatColor.DARK_GREEN + " Saturation: " + p.getSaturation() + ChatColor.WHITE + " Async: " + "False"));
                            cancel();
                        } else cancel();
                    } else cancel();
                }
            }.runTaskLater(plugin, 1);
        } else {
            new BukkitRunnable() {

                @Override
                public void run() {
                    p.setExhaustion(previousExhaustion + exhaustionToApply);
                    if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
                        if (p.hasPermission("cp.admin")) {
                            p.sendMessage(Messenger.prefix(ChatColor.AQUA + "Listener: " + "PlayerRegen" + ChatColor.GREEN + " Old exhaustion: " + previousExhaustion + ChatColor.YELLOW + " New exhaustion: " + p.getExhaustion() + ChatColor.DARK_GREEN + " Saturation: " + p.getSaturation() + ChatColor.WHITE + " Async: " + "True"));
                            cancel();
                        } else cancel();
                    } else cancel();
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
