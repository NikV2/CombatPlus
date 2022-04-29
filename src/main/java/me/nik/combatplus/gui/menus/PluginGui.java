package me.nik.combatplus.gui.menus;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.gui.Menu;
import me.nik.combatplus.gui.PlayerMenu;
import me.nik.combatplus.managers.MsgType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PluginGui extends Menu {

    public PluginGui(PlayerMenu playerMenu, CombatPlus plugin) {
        super(playerMenu, plugin);
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
                new MainGui(playerMenu, plugin).open();
                break;
            case 11:
                changeConfigBoolean(Config.Setting.CHECK_FOR_UPDATES.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 13:
                changeConfigBoolean(Config.Setting.DISABLE_BYPASS_PERMISSIONS.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 15:
                changeConfigBoolean(Config.Setting.DEVELOPER_MODE.getKey());
                getInventory().clear();
                setMenuItems();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack back = makeItem(Material.BARRIER, 1, "&cBack", null);

        inventory.setItem(31, back);

        List<String> updatesLore = new ArrayList<>();
        updatesLore.add("");
        updatesLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.CHECK_FOR_UPDATES.getKey()));
        updatesLore.add("");
        updatesLore.add("&fWould you like the plugin");
        updatesLore.add("&fTo Check for Updates?");

        List<String> bypassLore = new ArrayList<>();
        bypassLore.add("");
        bypassLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.DISABLE_BYPASS_PERMISSIONS.getKey()));
        bypassLore.add("");
        bypassLore.add("&fWould you like to disable");
        bypassLore.add("&fThe bypass permissions?");

        List<String> devLore = new ArrayList<>();
        devLore.add("");
        devLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.DEVELOPER_MODE.getKey()));
        devLore.add("");
        devLore.add("&fWould you like to receive");
        devLore.add("&fDebugging Information?");

        ItemStack updates = makeItem(Material.PAPER, 1, "&6Check for Updates", updatesLore);
        ItemStack bypass = makeItem(Material.PAPER, 1, "&6Disable Bypass Permissions", bypassLore);
        ItemStack dev = makeItem(Material.PAPER, 1, "&6Developer Mode", devLore);

        inventory.setItem(11, updates);
        inventory.setItem(13, bypass);
        inventory.setItem(15, dev);
    }
}