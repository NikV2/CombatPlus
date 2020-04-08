package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.Manager;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DisabledItems extends Manager {

    private List<String> disabledItems = configStringList("disabled_items.items");

    public DisabledItems(CombatPlus plugin) {
        super(plugin);
    }

    // This Listener Disables the crafting of the items defined in the Config

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCraft(PrepareItemCraftEvent e) {
        if (e.getViewers().size() < 1) return;
        if (disabledItems == null) return;
        if (e.getViewers().get(0).hasPermission("cp.bypass.items")) return;
        if (noCraftingDisabledWorlds((Player) e.getViewers().get(0))) return;
        CraftingInventory inv = e.getInventory();
        ItemStack result = inv.getResult();
        Player p = (Player) e.getViewers().get(0);
        if (result != null && disabledItems.contains(result.getType().name().toLowerCase())) {
            inv.setResult(null);
            p.sendMessage(Messenger.message("cannot_craft_this"));
            if (debug(p)) {
                p.sendMessage(Messenger.prefix(ChatColor.AQUA + "Crafting Canceled: " + "True" + ChatColor.GREEN + " Player: " + p.getName()));
            }
        }
    }
}
