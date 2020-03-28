package me.nik.combatplus.listeners;

import me.nik.combatplus.api.Manager;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Gapple extends Manager {
    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    private int cdtime = configInt("general.settings.golden_apple_cooldown.cooldown");

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
        if (!configBoolean("general.settings.golden_apple_cooldown.enabled")) return;
        if (gappleDisabledWorlds(e.getPlayer())) return;
        final String handItem = e.getItem().getType().name();
        if (handItem.contains("GOLDEN_APPLE")) {
            final UUID p = e.getPlayer().getUniqueId();
            final Player player = e.getPlayer();
            if (cooldown.containsKey(p)) {
                e.setCancelled(true);
                long secondsleft = ((cooldown.get(p) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                player.sendMessage(Messenger.message("golden_apple_cooldown") + secondsleft + " Seconds.");
            } else {
                taskRun(e);
                if (debug(player)) {
                    player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Eating Canceled: " + "True" + ChatColor.GREEN + " Player: " + player + ChatColor.YELLOW + " Added to cooldown: " + cooldown.containsKey(p)));
                }
            }
        }
    }
}