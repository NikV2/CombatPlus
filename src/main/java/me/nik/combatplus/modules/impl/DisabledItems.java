package me.nik.combatplus.modules.impl;

import me.nik.combatplus.Permissions;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.modules.Module;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class DisabledItems extends Module {

    public DisabledItems() {
        super("Disabled Items", Config.Setting.DISABLED_ITEMS_ENABLED.getBoolean());
    }

    @EventHandler
    public void onCraft(final PrepareItemCraftEvent e) {
        if (e.getViewers().size() < 1) return;

        Player p = (Player) e.getViewers().get(0);

        if (p.hasPermission(Permissions.BYPASS_ITEMS.getPermission())) return;

        CraftingInventory inv = e.getInventory();

        ItemStack result = inv.getResult();

        if (result != null && Config.Setting.DISABLED_ITEMS_LIST.getStringList().contains(result.getType().name().toLowerCase())) {

            inv.setResult(null);

            p.sendMessage(MsgType.CANNOT_CRAFT_THIS.getMessage());

            debug(p, "&6Cancelled: &a" + (inv.getResult() == null));
        }
    }
}