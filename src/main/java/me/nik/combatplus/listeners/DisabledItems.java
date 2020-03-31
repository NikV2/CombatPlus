package me.nik.combatplus.listeners;

import me.nik.combatplus.api.Manager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DisabledItems extends Manager {

    private List<String> disabledItems = configStringList("general.settings.disabled_items.items");

    // This Listener Disables the crafting of the items defined in the Config

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCraft(PrepareItemCraftEvent e) {
        if (((Player) e.getViewers()).hasPermission("cp.bypass.items")) return;
        if (e.getViewers().size() < 1) return;
        if (disabledItems == null) return;
        World world = e.getViewers().get(0).getWorld();
        if (noCraftingDisabledWorlds(world)) return;
        CraftingInventory inv = e.getInventory();
        ItemStack result = inv.getResult();
        if (result != null && disabledItems.contains(result.getType().name().toLowerCase())) {
            inv.setResult(null);
        }
    }
}
