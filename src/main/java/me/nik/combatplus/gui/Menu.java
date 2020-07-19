package me.nik.combatplus.gui;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu implements InventoryHolder {

    protected final CombatPlus plugin;

    protected Inventory inventory;

    protected PlayerMenuUtility playerMenuUtility;

    public Menu(PlayerMenuUtility playerMenuUtility, CombatPlus plugin) {
        this.playerMenuUtility = playerMenuUtility;
        this.plugin = plugin;
    }

    protected abstract String getMenuName();

    protected abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    protected abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

        this.setMenuItems();

        playerMenuUtility.getOwner().openInventory(inventory);
    }

    protected ItemStack makeItem(Material material, int amount, String displayName, List<String> lore) {

        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Messenger.format(displayName));
        if (lore != null) {
            List<String> loreList = new ArrayList<>();
            for (String l : lore) {
                loreList.add(Messenger.format(l));
            }
            itemMeta.setLore(loreList);
        }
        item.setItemMeta(itemMeta);

        return item;
    }

    protected void changeConfigBoolean(String path) {
        plugin.getConfiguration().set(path, !plugin.getConfiguration().getBoolean(path));
        plugin.getConfiguration().save();
        plugin.getConfiguration().reloadConfig();
    }

    protected boolean getConfigValue(String path) {
        return plugin.getConfiguration().getBoolean(path);
    }

    /**
     * This class is now the Inventory Holder, Woo!
     *
     * @return The inventory holder
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}