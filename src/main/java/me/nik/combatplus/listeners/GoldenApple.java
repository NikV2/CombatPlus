package me.nik.combatplus.listeners;

import me.nik.combatplus.api.Manager;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class GoldenApple extends Manager {
    private final HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    private final int cdtime = configInt("golden_apple_cooldown.golden_apple.cooldown");

    private void taskRun(PlayerItemConsumeEvent e) {
        cooldown.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
        new BukkitRunnable() {

            @Override
            public void run() {
                cooldown.remove(e.getPlayer().getUniqueId());
            }
        }.runTaskLaterAsynchronously(plugin, cdtime * 20);
    }

    // This Listener adds a cooldown between eating Golden Apples

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEatGoldenApple(PlayerItemConsumeEvent e) {
        if (gappleDisabledWorlds(e.getPlayer())) return;
        if (e.getPlayer().hasPermission("cp.bypass.gapple")) return;
        final Material handItem = e.getItem().getType();
        if (handItem == Material.GOLDEN_APPLE) {
            final UUID p = e.getPlayer().getUniqueId();
            final Player player = e.getPlayer();
            if (cooldown.containsKey(p)) {
                e.setCancelled(true);
                long secondsleft = ((cooldown.get(p) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                player.sendMessage(Messenger.message("golden_apple_cooldown").replaceAll("%seconds%", String.valueOf(secondsleft)));
            } else {
                taskRun(e);
                debug(player, "&3Golden Apple Cooldown &f&l>> &6Added to cooldown: &atrue");
            }
        }
    }
}