package me.nik.combatplus.listeners;

import me.nik.combatplus.api.Manager;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
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

    // This Listener Adds a cooldown between using Ender Pearls

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent e) {
        if (!configBoolean("enderpearl_cooldown.enabled")) return;
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || !(e.getAction() == Action.RIGHT_CLICK_BLOCK))) return;
        Player p = e.getPlayer();
        if (!(p.getInventory().getItemInMainHand().getType() == Material.ENDER_PEARL || !(p.getInventory().getItemInOffHand().getType() == Material.ENDER_PEARL)))
            return;
        if (p.hasPermission("cp.bypass.epearl")) return;
        UUID pUUID = p.getUniqueId();
        if (cooldown.containsKey(pUUID)) {
            e.setCancelled(true);
            long secondsLeft = ((cooldown.get(pUUID) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
            p.sendMessage(Messenger.message("enderpearl_cooldown") + secondsLeft + " Seconds.");
        } else {
            taskRun(e);
            if (debug(p)) {
                p.sendMessage(Messenger.prefix(ChatColor.AQUA + "Ender Pearl Cooldown: " + ChatColor.GREEN + "Added to cooldown: True" + ChatColor.GOLD + " Player: " + p.getName()));
            }
        }
    }
}
