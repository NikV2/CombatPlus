package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.HashMap;
import java.util.UUID;

public class EnchGapple implements Listener {
    CombatPlus plugin = CombatPlus.getPlugin(CombatPlus.class);
    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    private int cdtime = Config.get().getInt("general.settings.golden_apple_cooldown.cooldown");

    private void taskRun(PlayerItemConsumeEvent e) {
        cooldown.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                cooldown.remove(e.getPlayer().getUniqueId());
            }
        }, cdtime * 20);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEat(PlayerItemConsumeEvent e) {
        if (e.isCancelled()) return;
        if (!Config.get().getBoolean("general.settings.golden_apple_cooldown.enabled")) return;
        if (Config.get().getBoolean("general.settings.golden_apple_cooldown.enchanted_golden_apple")) {
            if (e.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                if (cooldown.containsKey(e.getPlayer().getUniqueId())) {
                    e.setCancelled(true);
                    long secondsleft = ((cooldown.get(e.getPlayer().getUniqueId()) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                    e.getPlayer().sendMessage(Messenger.message("enchanted_golden_apple_cooldown") + secondsleft + " Seconds.");
                } else {
                    taskRun(e);
                }
            } else if (e.getItem().getType() == Material.GOLDEN_APPLE) {
                if (Config.get().getBoolean("general.settings.golden_apple_cooldown.golden_apple")) {
                    if (cooldown.containsKey(e.getPlayer().getUniqueId())) {
                        e.setCancelled(true);
                        long secondsleft = ((cooldown.get(e.getPlayer().getUniqueId()) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                        e.getPlayer().sendMessage(Messenger.message("enchanted_golden_apple_cooldown") + secondsleft + " Seconds.");
                    } else {
                        taskRun(e);
                    }
                }
            }
        } else if (Config.get().getBoolean("general.settings.golden_apple_cooldown.golden_apple")) {
            if (e.getItem().getType() == Material.GOLDEN_APPLE) {
                if (cooldown.containsKey(e.getPlayer().getUniqueId())) {
                    e.setCancelled(true);
                    long secondsleft = ((cooldown.get(e.getPlayer().getUniqueId()) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                    e.getPlayer().sendMessage(Messenger.message("golden_apple_cooldown") + secondsleft + " Seconds.");
                } else {
                    taskRun(e);
                }
            }
        }
    }
}