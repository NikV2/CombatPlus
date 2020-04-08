package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.Manager;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ItemFrameRotate extends Manager {
    public ItemFrameRotate(CombatPlus plugin) {
        super(plugin);
    }

    // This Listener removes the ability to rotate item frames

    @EventHandler(priority = EventPriority.HIGH)
    public void onRotate(PlayerInteractEntityEvent e) {
        if (e.isCancelled()) return;
        if (!(e.getRightClicked() instanceof ItemFrame)) return;
        if (e.getPlayer().hasPermission("cp.bypass.rotate")) return;
        if (itemFrameRotationDisabledWorlds(e.getPlayer())) return;
        if (((ItemFrame) e.getRightClicked()).getItem().getType().equals(Material.AIR)) return;
        e.setCancelled(true);
        if (debug(e.getPlayer())) {
            e.getPlayer().sendMessage(Messenger.prefix(ChatColor.AQUA + "Rotation Canceled: " + "True" + ChatColor.GREEN + " Player: " + e.getPlayer().getName()));
        }
    }
}
