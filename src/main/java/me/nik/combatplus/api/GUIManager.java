package me.nik.combatplus.api;

import me.nik.combatplus.files.Lang;
import me.nik.combatplus.handlers.CombatPlusHolder;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIManager {

    private static Inventory mainGUI;

    public static void openMainGUI(Player p) {
        Inventory mainGUI = Bukkit.createInventory(new CombatPlusHolder(), 36, Messenger.format(Lang.get().getString("gui.main")));
        ItemStack settings = new ItemStack(Material.BOOK, 1);
        ItemMeta settings_meta = settings.getItemMeta();
        settings_meta.setDisplayName(ChatColor.GOLD + "Plugin Settings");
        settings.setItemMeta(settings_meta);

        ItemStack combatSettings = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemMeta combatSettings_meta = combatSettings.getItemMeta();
        combatSettings_meta.setDisplayName(ChatColor.YELLOW + "Combat Settings");
        combatSettings.setItemMeta(combatSettings_meta);

        ItemStack generalSettings = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta generalSettings_meta = generalSettings.getItemMeta();
        generalSettings_meta.setDisplayName(ChatColor.GREEN + "General Settings");
        generalSettings.setItemMeta(generalSettings_meta);

        ItemStack reload = new ItemStack(Material.DIAMOND, 1);
        ItemMeta reload_meta = reload.getItemMeta();
        reload_meta.setDisplayName(ChatColor.AQUA + "Looking for Support?");
        reload.setItemMeta(reload_meta);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta close_meta = close.getItemMeta();
        close_meta.setDisplayName(ChatColor.RED + "Reload and Exit");
        close.setItemMeta(close_meta);

        mainGUI.setItem(31, close);
        mainGUI.setItem(16, reload);
        mainGUI.setItem(14, generalSettings);
        mainGUI.setItem(12, combatSettings);
        mainGUI.setItem(10, settings);
        p.openInventory(mainGUI);
    }
}
