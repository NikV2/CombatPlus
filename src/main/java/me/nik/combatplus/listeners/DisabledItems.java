package me.nik.combatplus.listeners;

import me.nik.combatplus.Permissions;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class DisabledItems implements Listener {

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e) {
        if (e.getViewers().size() < 1) return;
        Player p = (Player) e.getViewers().get(0);
        if (p.hasPermission(Permissions.BYPASS_ITEMS.getPermission())) return;
        CraftingInventory inv = e.getInventory();
        ItemStack result = inv.getResult();
        if (result != null && Config.Setting.DISABLED_ITEMS_LIST.getStringList().contains(result.getType().name().toLowerCase())) {
            inv.setResult(null);
            p.sendMessage(MsgType.CANNOT_CRAFT_THIS.getMessage());
            Messenger.debug(p, "&3Disabled Items &f&l>> &6Canceled: &atrue");
        }
    }
}