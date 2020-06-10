package me.nik.combatplus.gui.menus;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.gui.Menu;
import me.nik.combatplus.gui.PlayerMenuUtility;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
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
        return Messenger.message(MsgType.GUI_GENERAL);
    }

    @Override
    protected int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
            case "§6Enchanted Golden Apple Crafting":
                booleanSet("recipes.enchanted_golden_apple", !configBoolean("recipes.enchanted_golden_apple"));
                saveAndReload();
                break;
            case "§6Fishing Rod Knockback":
                booleanSet("knockback.fishing_rod.enabled", !configBoolean("knockback.fishing_rod.enabled"));
                saveAndReload();
                break;
            case "§ePrevious Page 2/2":
                p.closeInventory();
                new GeneralGui(playerMenuUtility, plugin).open();
                break;
        }
    }

    @Override
    protected void setMenuItems() {
        ItemStack back = makeItem(Material.BARRIER, 1, "§cBack", null);
        ItemStack pp = makeItem(Material.BOOK, 1, "§ePrevious Page 2/2", null);

        inventory.setItem(45, pp);
        inventory.setItem(49, back);

        new BukkitRunnable() {
            public void run() {
                if (!(inventory.getHolder() instanceof Menu)) {
                    cancel();
                    return;
                }
                ArrayList<String> egaLore = new ArrayList<>();
                egaLore.add("");
                egaLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("recipes.enchanted_golden_apple"));
                egaLore.add("");
                egaLore.add(ChatColor.WHITE + "Makes Enchanted Golden Apples");
                egaLore.add(ChatColor.WHITE + "Able to be Crafted");
                ItemStack ega = makeItem(Material.PAPER, 1, "§6Enchanted Golden Apple Crafting", egaLore);

                ArrayList<String> frkLore = new ArrayList<>();
                frkLore.add("");
                frkLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("knockback.fishing_rod.enabled"));
                frkLore.add("");
                frkLore.add(ChatColor.WHITE + "Makes Fishing Rods Knockback");
                frkLore.add(ChatColor.WHITE + "Players just like in 1.8");
                frkLore.add("");
                frkLore.add(ChatColor.GRAY + "More options in the Config.yml");
                ItemStack frk = makeItem(Material.PAPER, 1, "§6Fishing Rod Knockback", frkLore);

                inventory.setItem(10, ega);
                inventory.setItem(12, frk);
            }
        }.runTaskTimer(plugin, 1, 5);
    }
}
