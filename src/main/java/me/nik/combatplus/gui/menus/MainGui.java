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

public class MainGui extends Menu {

    public MainGui(PlayerMenuUtility playerMenuUtility, CombatPlus plugin) {
        super(playerMenuUtility, plugin);
    }

    @Override
    public String getMenuName() {
        return Messenger.format(Lang.get().getString("gui.main"));
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
            case "§ePlugin Settings":
                p.closeInventory();
                new PluginGui(playerMenuUtility, plugin).open();
                break;
            case "§eCombat Settings":
                p.closeInventory();
                new CombatGui(playerMenuUtility, plugin).open();
                break;
            case "§aGeneral Settings":
                p.closeInventory();
                new GeneralGui(playerMenuUtility, plugin).open();
                break;
            case "§bLooking for Support?":
                p.closeInventory();
                p.sendMessage(ChatColor.BLUE + ">> " + ChatColor.WHITE + "https://discordapp.com/invite/m7j2Y9H" + ChatColor.BLUE + " <<");
                break;
            case "§cReload and Exit":
                p.closeInventory();
                p.sendMessage(Messenger.message("reloading"));
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                plugin.getServer().getPluginManager().enablePlugin(plugin);
                p.sendMessage(Messenger.message("reloaded"));
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack pluginSettings = makeItem(Material.BOOK, 1, "§ePlugin Settings", null);
        ItemStack combatSettings = makeItem(Material.DIAMOND_CHESTPLATE, 1, "§eCombat Settings", null);
        ItemStack generalSettings = makeItem(Material.NAME_TAG, 1, "§aGeneral Settings", null);
        ItemStack support = makeItem(Material.DIAMOND, 1, "§bLooking for Support?", null);
        ItemStack close = makeItem(Material.BARRIER, 1, "§cReload and Exit", null);

        inventory.setItem(31, close);
        inventory.setItem(16, support);
        inventory.setItem(14, generalSettings);
        inventory.setItem(12, combatSettings);
        inventory.setItem(10, pluginSettings);
    }
}
