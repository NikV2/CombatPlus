package me.nik.combatplus.listeners;

import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.WorldUtils;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ItemFrameRotate implements Listener {

    private final WorldUtils worldUtils = new WorldUtils();

    /*
     This Listener removes the ability to rotate item frames
     */

    @EventHandler(priority = EventPriority.NORMAL)
    public void onRotate(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof ItemFrame)) return;
        if (e.getPlayer().hasPermission("cp.bypass.rotate")) return;
        if (worldUtils.itemFrameRotationDisabledWorlds(e.getPlayer())) return;
        if (((ItemFrame) e.getRightClicked()).getItem().getType().equals(Material.AIR)) return;
        e.setCancelled(true);
        Messenger.debug(e.getPlayer(), "&3Item Frame Rotation &f&l>> &6Canceled: &a" + e.isCancelled());
    }
}
