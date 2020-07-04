package me.nik.combatplus.gui.menus;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.gui.Menu;
import me.nik.combatplus.gui.PlayerMenuUtility;
import me.nik.combatplus.managers.MsgType;
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
        return MsgType.GUI_PLUGIN.getMessage();
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        switch (e.getSlot()) {
            case 31:
                p.closeInventory();
                new MainGui(playerMenuUtility, plugin).open();
                break;
            case 14:
                booleanSet("settings.developer_mode", !configBoolean("settings.developer_mode"));
                saveAndReload();
                break;
            case 12:
                booleanSet("settings.check_for_updates", !configBoolean("settings.check_for_updates"));
                saveAndReload();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack back = makeItem(Material.BARRIER, 1, "&cBack", null);

        inventory.setItem(31, back);

        new BukkitRunnable() {
            public void run() {
                if (!(inventory.getHolder() instanceof Menu)) {
                    cancel();
                    return;
                }
                ArrayList<String> updatesLore = new ArrayList<>();
                updatesLore.add("");
                updatesLore.add("&7Currently set to: &a" + isEnabled("settings.check_for_updates"));
                updatesLore.add("");
                updatesLore.add("&fWould you like the plugin");
                updatesLore.add("&fTo Check for Updates?");
                ArrayList<String> devLore = new ArrayList<>();
                devLore.add("");
                devLore.add("&7Currently set to: &a" + isEnabled("settings.developer_mode"));
                devLore.add("");
                devLore.add("&fWould you like to receive");
                devLore.add("&fDebugging Information?");
                ItemStack dev = makeItem(Material.PAPER, 1, "&6Developer Mode", devLore);
                ItemStack checkUpdates = makeItem(Material.PAPER, 1, "&6Check for Updates", updatesLore);

                inventory.setItem(12, checkUpdates);
                inventory.setItem(14, dev);
            }
        }.runTaskTimer(plugin, 1, 5);
    }
}