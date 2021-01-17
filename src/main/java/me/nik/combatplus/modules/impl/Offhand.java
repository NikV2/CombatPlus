package me.nik.combatplus.modules.impl;

import me.nik.combatplus.Permissions;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.WorldUtils;
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

public class Offhand extends Module {

    private static final int OFFHANDSLOT = 40;

    public Offhand() {
        super("Offhand", Config.Setting.DISABLE_OFFHAND_ENABLED.getBoolean());
    }

    @EventHandler
    public void onSwapHands(final PlayerSwapHandItemsEvent e) {
        if (WorldUtils.offhandDisabledWorlds(e.getPlayer())) return;

        Player p = e.getPlayer();

        if (p.hasPermission(Permissions.BYPASS_OFFHAND.getPermission())) return;

        e.setCancelled(true);

        debug(p, "&6Cancelled: &a" + e.isCancelled());
    }

    @EventHandler
    public void onClickOffHand(final InventoryClickEvent e) {
        if (WorldUtils.offhandDisabledWorlds((Player) e.getWhoClicked())) return;

        if (e.getWhoClicked().hasPermission(Permissions.BYPASS_OFFHAND.getPermission())) return;

        if (e.getInventory().getType() != InventoryType.CRAFTING || e.getSlot() != OFFHANDSLOT) return;

        if (e.getClick().equals(ClickType.NUMBER_KEY) || itemCheck(e.getCursor())) {

            e.setResult(Event.Result.DENY);

            e.setCancelled(true);

            debug((Player) e.getWhoClicked(), "&6Cancelled: &a" + e.isCancelled());
        }
    }

    @EventHandler
    public void onDrag(final InventoryDragEvent e) {
        if (WorldUtils.offhandDisabledWorlds((Player) e.getWhoClicked())) return;

        if (e.getWhoClicked().hasPermission(Permissions.BYPASS_OFFHAND.getPermission())) return;

        if (e.getInventory().getType() != InventoryType.CRAFTING || !e.getInventorySlots().contains(OFFHANDSLOT))
            return;

        if (itemCheck(e.getOldCursor())) {

            e.setResult(Event.Result.DENY);

            e.setCancelled(true);

            debug((Player) e.getWhoClicked(), "&6Cancelled: &a" + e.isCancelled());
        }
    }

    private boolean itemCheck(ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }
}