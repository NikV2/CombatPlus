package me.nik.combatplus.listeners;

import me.nik.combatplus.api.Manager;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Enderpearl extends Manager {
    private final HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    private final int cdtime = configInt("enderpearl_cooldown.cooldown");

    private void taskRun(PlayerInteractEvent e) {
        cooldown.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
        new BukkitRunnable() {

            @Override
            public void run() {
                cooldown.remove(e.getPlayer().getUniqueId());
            }
        }.runTaskLaterAsynchronously(plugin, cdtime * 20);
    }

    private boolean holdsEnderPearl(Player p) {
        return p.getInventory().getItemInMainHand().getType() == Material.ENDER_PEARL || p.getInventory().getItemInOffHand().getType() == Material.ENDER_PEARL;
    }

    // This Listener Adds a cooldown between using Ender Pearls

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player p = e.getPlayer();
            if (holdsEnderPearl(p)) {
                if (p.hasPermission("cp.bypass.epearl")) return;
                UUID pUUID = p.getUniqueId();
                if (cooldown.containsKey(pUUID)) {
                    e.setCancelled(true);
                    p.updateInventory();
                    long secondsLeft = ((cooldown.get(pUUID) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                    p.sendMessage(Messenger.message("enderpearl_cooldown").replaceAll("%seconds%", String.valueOf(secondsLeft)));
                } else {
                    taskRun(e);
                    debug(p, "&3Ender Pearl Cooldown &f&l>> &6Added to cooldown: &atrue");
                }
            }
        }
    }
}