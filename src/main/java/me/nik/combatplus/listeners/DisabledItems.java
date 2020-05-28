package me.nik.combatplus.listeners;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DisabledItems implements Listener {

    private final List<String> disabledItems = Config.get().getStringList("disabled_items.items");

    /*
     This Listener Disables the crafting of the items defined in the Config
     */

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e) {
        if (e.getViewers().size() < 1) return;
        if (disabledItems == null) return;
        if (e.getViewers().get(0).hasPermission("cp.bypass.items")) return;
        CraftingInventory inv = e.getInventory();
        ItemStack result = inv.getResult();
        Player p = (Player) e.getViewers().get(0);
        if (result != null && disabledItems.contains(result.getType().name().toLowerCase())) {
            inv.setResult(null);
            p.sendMessage(Messenger.message("cannot_craft_this"));
            Messenger.debug(p, "&3Disabled Items &f&l>> &6Canceled: &atrue");
        }
    }
}
