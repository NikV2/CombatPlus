package me.nik.combatplus.listeners;

import me.nik.combatplus.api.Manager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class Offhand extends Manager {

    private static final int OFFHAND_SLOT = 40;

    // This Listener completely disables the use of the Offhand

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSwapHands(PlayerSwapHandItemsEvent e) {
        if (e.getPlayer().hasPermission("cp.bypass.offhand")) return;
        if (offHandDisabledWorlds(e.getPlayer())) return;
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickOffHand(InventoryClickEvent e) {
        if (e.getWhoClicked().hasPermission("cp.bypass.offhand")) return;
        if (offHandDisabledWorlds((Player) e.getWhoClicked())) return;
        if (e.getInventory().getType() != InventoryType.CRAFTING || e.getSlot() != OFFHAND_SLOT) return;
        if (e.getClick().equals(ClickType.NUMBER_KEY) || itemCheck(e.getCursor())) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrag(InventoryDragEvent e) {
        if (e.getWhoClicked().hasPermission("cp.bypass.offhand")) return;
        if (offHandDisabledWorlds((Player) e.getWhoClicked())) return;
        if (e.getInventory().getType() != InventoryType.CRAFTING || !e.getInventorySlots().contains(OFFHAND_SLOT))
            return;
        if (itemCheck(e.getOldCursor())) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
        }
    }

    private boolean itemCheck(ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }
}
