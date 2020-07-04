package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DisabledItems implements Listener {

    private final List<String> disabledItems;

    public DisabledItems(CombatPlus plugin) {
        this.disabledItems = plugin.getConfig().getStringList("disabled_items.items");
    }

    /*
     This Listener Disables the crafting of the items defined in the Config
     */

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e) {
        if (e.getViewers().size() < 1) return;
        Player p = (Player) e.getViewers().get(0);
        if (p.hasPermission("cp.bypass.items")) return;
        CraftingInventory inv = e.getInventory();
        ItemStack result = inv.getResult();
        if (result != null && disabledItems.contains(result.getType().name().toLowerCase())) {
            inv.setResult(null);
            p.sendMessage(MsgType.CANNOT_CRAFT_THIS.getMessage());
            Messenger.debug(p, "&3Disabled Items &f&l>> &6Canceled: &atrue");
        }
    }
}