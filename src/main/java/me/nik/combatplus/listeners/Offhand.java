package me.nik.combatplus.listeners;

import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.WorldUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class Offhand implements Listener {

    private final WorldUtils worldUtils = new WorldUtils();

    private final static int OFFHANDSLOT = 40;

    /*
     This Listener completely disables the use of the Offhand
     */

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent e) {
        if (worldUtils.offhandDisabledWorlds(e.getPlayer())) return;
        Player p = e.getPlayer();
        if (p.hasPermission("cp.bypass.offhand")) return;
        e.setCancelled(true);
        Messenger.debug(p, "&3Offhand &f&l>> &6Canceled: &a" + e.isCancelled());
    }

    @EventHandler
    public void onClickOffHand(InventoryClickEvent e) {
        if (worldUtils.offhandDisabledWorlds((Player) e.getWhoClicked())) return;
        if (e.getWhoClicked().hasPermission("cp.bypass.offhand")) return;
        if (e.getInventory().getType() != InventoryType.CRAFTING || e.getSlot() != OFFHANDSLOT) return;
        if (e.getClick().equals(ClickType.NUMBER_KEY) || itemCheck(e.getCursor())) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            Messenger.debug((Player) e.getWhoClicked(), "&3Offhand &f&l>> &6Canceled: &a" + e.isCancelled());
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (worldUtils.offhandDisabledWorlds((Player) e.getWhoClicked())) return;
        if (e.getWhoClicked().hasPermission("cp.bypass.offhand")) return;
        if (e.getInventory().getType() != InventoryType.CRAFTING || !e.getInventorySlots().contains(OFFHANDSLOT))
            return;
        if (itemCheck(e.getOldCursor())) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
            Messenger.debug((Player) e.getWhoClicked(), "&3Offhand &f&l>> &6Canceled: &a" + e.isCancelled());
        }
    }

    private boolean itemCheck(ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }
}