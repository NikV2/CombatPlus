package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Gapple implements Listener {
    CombatPlus plugin = CombatPlus.getPlugin(CombatPlus.class);
    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    private int cdtime = Config.get().getInt("general.settings.golden_apple_cooldown.cooldown");

    private void taskRun(PlayerItemConsumeEvent e) {
        cooldown.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
        new BukkitRunnable() {

            @Override
            public void run() {
                cooldown.remove(e.getPlayer().getUniqueId());
            }
        }.runTaskLaterAsynchronously(plugin, cdtime * 20);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEat(PlayerItemConsumeEvent e) {
        if (!Config.get().getBoolean("general.settings.golden_apple_cooldown.enabled")) return;
        for (String world : Config.get().getStringList("general.settings.golden_apple_cooldown.disabled_worlds")) {
            if (e.getPlayer().getWorld().getName().equalsIgnoreCase(world)) return;
        }
        if (e.getItem().getType().name().contains("GOLDEN_APPLE")) {
            if (cooldown.containsKey(e.getPlayer().getUniqueId())) {
                e.setCancelled(true);
                long secondsleft = ((cooldown.get(e.getPlayer().getUniqueId()) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                e.getPlayer().sendMessage(Messenger.message("golden_apple_cooldown") + secondsleft + " Seconds.");
            } else {
                taskRun(e);
                if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
                    if (e.getPlayer().hasPermission("cp.admin")) {
                        e.getPlayer().sendMessage(Messenger.prefix(ChatColor.AQUA + "Eating Canceled: " + "True" + ChatColor.GREEN + " Player: " + e.getPlayer().getName() + ChatColor.YELLOW + " Added to cooldown: " + cooldown.containsKey(e.getPlayer().getUniqueId())));
                    }
                }
            }
        }
    }
}