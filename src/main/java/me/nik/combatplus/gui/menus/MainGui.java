package me.nik.combatplus.gui.menus;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.gui.Menu;
import me.nik.combatplus.gui.PlayerMenu;
import me.nik.combatplus.managers.MsgType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MainGui extends Menu {

    public MainGui(PlayerMenu playerMenu, CombatPlus plugin) {
        super(playerMenu, plugin);
    }

    @Override
    public String getMenuName() {
        return MsgType.GUI_MAIN.getMessage();
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        switch (e.getSlot()) {
            case 10:
                p.closeInventory();
                new PluginGui(playerMenu, plugin).open();
                break;
            case 12:
                p.closeInventory();
                new CombatGui(playerMenu, plugin).open();
                break;
            case 14:
                p.closeInventory();
                new GeneralGui(playerMenu, plugin).open();
                break;
            case 16:
                p.closeInventory();
                p.sendMessage(ChatColor.BLUE + ">> " + ChatColor.WHITE + "https://github.com/NikV2/CombatPlus/issues/new/choose" + ChatColor.BLUE + " <<");
                break;
            case 31:
                p.closeInventory();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack pluginSettings = makeItem(Material.BOOK, 1, "&ePlugin Settings", null);
        ItemStack combatSettings = makeItem(Material.DIAMOND_CHESTPLATE, 1, "&eCombat Settings", null);
        ItemStack generalSettings = makeItem(Material.NAME_TAG, 1, "&aGeneral Settings", null);
        ItemStack support = makeItem(Material.DIAMOND, 1, "&bLooking for Support?", null);
        ItemStack close = makeItem(Material.BARRIER, 1, "&cExit", null);

        inventory.setItem(31, close);
        inventory.setItem(16, support);
        inventory.setItem(14, generalSettings);
        inventory.setItem(12, combatSettings);
        inventory.setItem(10, pluginSettings);
    }
}