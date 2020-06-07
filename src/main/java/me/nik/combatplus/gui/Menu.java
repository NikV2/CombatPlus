package me.nik.combatplus.gui;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

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

    protected ItemStack makeItem(Material material, int amount, String displayName, ArrayList<String> lore) {

        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    protected boolean isEnabled(String path) {
        return Config.get().getBoolean(path);
    }

    protected boolean configBoolean(String booleans) {
        return Config.get().getBoolean(booleans);
    }

    protected void booleanSet(String path, boolean value) {
        Config.get().set(path, value);
    }

    protected void saveAndReload() {
        plugin.config.save();
        plugin.config.reload();
        plugin.config.setup();
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
