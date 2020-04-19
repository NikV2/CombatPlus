package me.nik.combatplus.listeners;

import me.nik.combatplus.api.Manager;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class EnchantedGoldenApple extends Manager {
    private final HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    private final int cdtime = configInt("golden_apple_cooldown.enchanted_golden_apple.cooldown");

    private void taskRun(PlayerItemConsumeEvent e) {
        cooldown.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
        new BukkitRunnable() {

            @Override
            public void run() {
                cooldown.remove(e.getPlayer().getUniqueId());
            }
        }.runTaskLaterAsynchronously(plugin, cdtime * 20);
    }

    // This Listener adds a cooldown between eating Enchanted Golden Apples

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEatEnchantedGoldenApple(PlayerItemConsumeEvent e) {
        if (gappleDisabledWorlds(e.getPlayer())) return;
        if (e.getPlayer().hasPermission("cp.bypass.gapple")) return;
        final Material handItem = e.getItem().getType();
        if (handItem == Material.ENCHANTED_GOLDEN_APPLE) {
            final UUID p = e.getPlayer().getUniqueId();
            final Player player = e.getPlayer();
            if (cooldown.containsKey(p)) {
                e.setCancelled(true);
                long secondsleft = ((cooldown.get(p) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                player.sendMessage(Messenger.message("enchanted_golden_apple_cooldown") + secondsleft + " Seconds.");
            } else {
                taskRun(e);
                if (debug(player)) {
                    player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Enchanted Golden Apple Cooldown:" + ChatColor.GREEN + " Player: " + player.getName() + ChatColor.YELLOW + " Added to cooldown: " + cooldown.containsKey(p)));
                }
            }
        }
    }
}
