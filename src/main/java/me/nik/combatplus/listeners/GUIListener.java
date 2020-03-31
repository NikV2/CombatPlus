package me.nik.combatplus.listeners;

import me.nik.combatplus.handlers.CombatPlusHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        if (!(e.getInventory().getHolder() instanceof CombatPlusHolder)) return;
        if (null == clickedItem) return;
        switch (clickedItem.getType()) {
            case BOOK:
                System.out.println("clicked the plugin settings");
                break;
            case DIAMOND_CHESTPLATE:
                System.out.println("clicked the combat settings");
                break;
            case NAME_TAG:
                System.out.println("clicked the general settings");
                break;
            case DIAMOND:
                System.out.println("clicked support");
                break;
            case BARRIER:
                System.out.println("clicked exit");
                break;
        }
        e.setCancelled(true);
        if (e.getClick() == ClickType.SHIFT_RIGHT) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onMenuOpenedClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!(e.getInventory().getHolder() instanceof Player)) return;
        if (!(player.getOpenInventory().getTopInventory().getHolder() instanceof CombatPlusHolder)) return;
        e.setCancelled(true);
    }
}