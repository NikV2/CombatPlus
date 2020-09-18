package me.nik.combatplus.gui.menus;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.gui.Menu;
import me.nik.combatplus.gui.PlayerMenuUtility;
import me.nik.combatplus.managers.MsgType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GeneralTwoGui extends Menu {
    public GeneralTwoGui(PlayerMenuUtility playerMenuUtility, CombatPlus plugin) {
        super(playerMenuUtility, plugin);
    }

    @Override
    protected String getMenuName() {
        return MsgType.GUI_GENERAL.getMessage();
    }

    @Override
    protected int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        switch (e.getSlot()) {
            case 10:
                changeConfigBoolean(Config.Setting.ENCHANTED_APPLE_CRAFTING.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 12:
                changeConfigBoolean(Config.Setting.FISHING_ROD_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 45:
                p.closeInventory();
                new GeneralGui(playerMenuUtility, plugin).open();
                break;
        }
    }

    @Override
    protected void setMenuItems() {
        ItemStack pp = makeItem(Material.BOOK, 1, "&ePrevious Page 2/2", null);

        inventory.setItem(45, pp);
        ArrayList<String> egaLore = new ArrayList<>();
        egaLore.add("");
        egaLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.ENCHANTED_APPLE_CRAFTING.getKey()));
        egaLore.add("");
        egaLore.add("&fMakes Enchanted Golden Apples");
        egaLore.add("&fAble to be Crafted");
        ItemStack ega = makeItem(Material.PAPER, 1, "&6Enchanted Golden Apple Crafting", egaLore);

        ArrayList<String> frkLore = new ArrayList<>();
        frkLore.add("");
        frkLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.FISHING_ROD_ENABLED.getKey()));
        frkLore.add("");
        frkLore.add("&fMakes Fishing Rods Knockback");
        frkLore.add("&fPlayers just like in 1.8");
        frkLore.add("");
        frkLore.add("&7More options in the Config.yml");
        ItemStack frk = makeItem(Material.PAPER, 1, "&6Fishing Rod Knockback", frkLore);

        inventory.setItem(10, ega);
        inventory.setItem(12, frk);
    }
}