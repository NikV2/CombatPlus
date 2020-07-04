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
                booleanSet("recipes.enchanted_golden_apple", !configBoolean("recipes.enchanted_golden_apple"));
                saveAndReload();
                break;
            case 12:
                booleanSet("knockback.fishing_rod.enabled", !configBoolean("knockback.fishing_rod.enabled"));
                saveAndReload();
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

        new BukkitRunnable() {
            public void run() {
                if (!(inventory.getHolder() instanceof Menu)) {
                    cancel();
                    return;
                }
                ArrayList<String> egaLore = new ArrayList<>();
                egaLore.add("");
                egaLore.add("&7Currently set to: &a" + isEnabled("recipes.enchanted_golden_apple"));
                egaLore.add("");
                egaLore.add("&fMakes Enchanted Golden Apples");
                egaLore.add("&fAble to be Crafted");
                ItemStack ega = makeItem(Material.PAPER, 1, "&6Enchanted Golden Apple Crafting", egaLore);

                ArrayList<String> frkLore = new ArrayList<>();
                frkLore.add("");
                frkLore.add("&7Currently set to: &a" + isEnabled("knockback.fishing_rod.enabled"));
                frkLore.add("");
                frkLore.add("&fMakes Fishing Rods Knockback");
                frkLore.add("&fPlayers just like in 1.8");
                frkLore.add("");
                frkLore.add("&7More options in the Config.yml");
                ItemStack frk = makeItem(Material.PAPER, 1, "&6Fishing Rod Knockback", frkLore);

                inventory.setItem(10, ega);
                inventory.setItem(12, frk);
            }
        }.runTaskTimer(plugin, 1, 5);
    }
}