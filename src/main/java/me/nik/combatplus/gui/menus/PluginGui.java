package me.nik.combatplus.gui.menus;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.gui.Menu;
import me.nik.combatplus.gui.PlayerMenuUtility;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PluginGui extends Menu {

    public PluginGui(PlayerMenuUtility playerMenuUtility, CombatPlus plugin) {
        super(playerMenuUtility, plugin);
    }

    @Override
    public String getMenuName() {
        return Messenger.format(Lang.get().getString("gui.plugin"));
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
            case "§cBack":
                p.closeInventory();
                new MainGui(playerMenuUtility, plugin).open();
                break;
            case "§6Developer Mode":
                booleanSet("settings.developer_mode", !configBoolean("settings.developer_mode"));
                saveAndReload();
                break;
            case "§6Check for Updates":
                booleanSet("settings.check_for_updates", !configBoolean("settings.check_for_updates"));
                saveAndReload();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack back = makeItem(Material.BARRIER, 1, "§cBack", null);

        inventory.setItem(31, back);

        new BukkitRunnable() {
            public void run() {
                if (!(inventory.getHolder() instanceof Menu)) {
                    cancel();
                    return;
                }
                ArrayList<String> updatesLore = new ArrayList<>();
                updatesLore.add("");
                updatesLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("settings.check_for_updates"));
                updatesLore.add("");
                updatesLore.add(ChatColor.WHITE + "Would you like the plugin");
                updatesLore.add(ChatColor.WHITE + "To Check for Updates?");
                ArrayList<String> devLore = new ArrayList<>();
                devLore.add("");
                devLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("settings.developer_mode"));
                devLore.add("");
                devLore.add(ChatColor.WHITE + "Would you like to receive");
                devLore.add(ChatColor.WHITE + "Debugging Information?");
                ItemStack dev = makeItem(Material.PAPER, 1, "§6Developer Mode", devLore);
                ItemStack checkUpdates = makeItem(Material.PAPER, 1, "§6Check for Updates", updatesLore);

                inventory.setItem(12, checkUpdates);
                inventory.setItem(14, dev);
            }
        }.runTaskTimer(plugin, 1, 5);
    }
}
