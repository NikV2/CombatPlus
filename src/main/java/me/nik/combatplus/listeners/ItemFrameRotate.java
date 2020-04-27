package me.nik.combatplus.listeners;

import me.nik.combatplus.api.Manager;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ItemFrameRotate extends Manager {

    // This Listener removes the ability to rotate item frames

    @EventHandler(priority = EventPriority.HIGH)
    public void onRotate(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof ItemFrame)) return;
        if (e.getPlayer().hasPermission("cp.bypass.rotate")) return;
        if (itemFrameRotationDisabledWorlds(e.getPlayer())) return;
        if (((ItemFrame) e.getRightClicked()).getItem().getType().equals(Material.AIR)) return;
        e.setCancelled(true);
        debug(e.getPlayer(), "&3Item Frame Rotation &f&l>> &6Canceled: &a" + e.isCancelled());
    }
}
