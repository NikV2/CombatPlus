package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.Permissions;
import me.nik.combatplus.modules.Module;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class DisableOffhand extends Module {

    private static final int OFFHANDSLOT = 40;

    public DisableOffhand() {
        super(Config.Setting.DISABLE_OFFHAND.getBoolean());
    }

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent e) {
        if (shouldReturn(e.getPlayer())) return;

        e.setCancelled(true);

        debug(e.getPlayer(), "&6Cancelled: &a" + e.isCancelled());
    }

    @EventHandler
    public void onClickOffHand(InventoryClickEvent e) {
        if (shouldReturn((Player) e.getWhoClicked())
                || e.getInventory().getType() != InventoryType.CRAFTING
                || e.getSlot() != OFFHANDSLOT) return;

        if (e.getClick().equals(ClickType.NUMBER_KEY) || itemCheck(e.getCursor())) {

            e.setResult(Event.Result.DENY);

            e.setCancelled(true);

            debug((Player) e.getWhoClicked(), "&6Cancelled");
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (shouldReturn((Player) e.getWhoClicked())
                || e.getInventory().getType() != InventoryType.CRAFTING
                || !e.getInventorySlots().contains(OFFHANDSLOT)) return;

        if (itemCheck(e.getOldCursor())) {

            e.setResult(Event.Result.DENY);

            e.setCancelled(true);

            debug((Player) e.getWhoClicked(), "&6Cancelled");
        }
    }

    private boolean shouldReturn(Player player) {
        return !Config.Setting.DISABLE_BYPASS_PERMISSIONS.getBoolean() && player.hasPermission(Permissions.BYPASS_OFFHAND.getPermission());
    }

    private boolean itemCheck(ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }
}