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
import java.util.Arrays;

public class GeneralGui extends Menu {
    public GeneralGui(PlayerMenuUtility playerMenuUtility, CombatPlus plugin) {
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
            case 49:
                p.closeInventory();
                new MainGui(playerMenuUtility, plugin).open();
                break;
            case 10:
                changeConfigBoolean(Config.Setting.COOLDOWN_GOLDEN_APPLE_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 12:
                changeConfigBoolean(Config.Setting.COOLDOWN_ENCHANTED_APPLE_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 14:
                changeConfigBoolean(Config.Setting.DISABLED_ITEMS_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 28:
                changeConfigBoolean(Config.Setting.HEALTHBAR_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 30:
                changeConfigBoolean(Config.Setting.DISABLE_OFFHAND_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 32:
                changeConfigBoolean(Config.Setting.FIX_PROJECTILES.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 16:
                changeConfigBoolean(Config.Setting.CUSTOM_PLAYER_HEALTH_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 34:
                changeConfigBoolean(Config.Setting.ENDERPEARL_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 53:
                p.closeInventory();
                new GeneralTwoGui(playerMenuUtility, plugin).open();
                break;
        }
    }

    @Override
    protected void setMenuItems() {
        ItemStack back = makeItem(Material.BARRIER, 1, "&cBack", null);
        ItemStack next = makeItem(Material.BOOK, 1, "&eNext Page 1/2", null);

        inventory.setItem(49, back);
        inventory.setItem(53, next);
        ArrayList<String> gappleLore = new ArrayList<>();
        gappleLore.add("");
        gappleLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.COOLDOWN_GOLDEN_APPLE_ENABLED.getKey()));
        gappleLore.add("");
        gappleLore.add("&fWould you like a Cooldown");
        gappleLore.add("&fBetween eating Golden Apples?");
        gappleLore.add("");
        gappleLore.add("&7More options in the Config.yml");
        ItemStack gapple = makeItem(Material.PAPER, 1, "&6Golden Apple Cooldown", gappleLore);

        ArrayList<String> gappleELore = new ArrayList<>();
        gappleELore.add("");
        gappleELore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.COOLDOWN_ENCHANTED_APPLE_ENABLED.getKey()));
        gappleELore.add("");
        gappleELore.add("&fWould you like a Cooldown");
        gappleELore.add("&fBetween eating Enchanted Golden Apples?");
        gappleELore.add("");
        gappleELore.add("&7More options in the Config.yml");
        ItemStack gappleE = makeItem(Material.PAPER, 1, "&6Enchanted Golden Apple Cooldown", gappleELore);

        ArrayList<String> disItemsLore = new ArrayList<>();
        disItemsLore.add("");
        disItemsLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.DISABLED_ITEMS_ENABLED.getKey()));
        disItemsLore.add("");
        disItemsLore.add("&fWould you like to Disable specific");
        disItemsLore.add("&fItems from being Crafted?");
        disItemsLore.add("");
        disItemsLore.add("&7More options in the Config.yml");
        ItemStack disItems = makeItem(Material.PAPER, 1, "&6Disabled Items", disItemsLore);

        ArrayList<String> offhLore = new ArrayList<>();
        offhLore.add("");
        offhLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.DISABLE_OFFHAND_ENABLED.getKey()));
        offhLore.add("");
        offhLore.add("&fWould you like to Prevent");
        offhLore.add("&fPlayers from using the Offhand?");
        ItemStack offh = makeItem(Material.PAPER, 1, "&6Disable Offhand", offhLore);

        ArrayList<String> projLore = new ArrayList<>();
        projLore.add("");
        projLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.FIX_PROJECTILES.getKey()));
        projLore.add("");
        projLore.add("&fFixes a Bug with Projectiles");
        projLore.add("&fNot always going straight");
        ItemStack proj = makeItem(Material.PAPER, 1, "&6Projectile Fixer", projLore);

        ArrayList<String> healthLore = new ArrayList<>();
        healthLore.add("");
        healthLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.CUSTOM_PLAYER_HEALTH_ENABLED.getKey()));
        healthLore.add("");
        healthLore.add("&fWould you like your Players");
        healthLore.add("&fTo have Customized Max Health?");
        healthLore.add("");
        healthLore.add("&7More options in the Config.yml");
        ItemStack health = makeItem(Material.PAPER, 1, "&6Custom Player Health", healthLore);

        ItemStack bar = makeItem(Material.PAPER, 1, "&6Health Bar", Arrays.asList(
                "",
                "&7Currently set to: &a" + getConfigValue(Config.Setting.HEALTHBAR_ENABLED.getKey()),
                "",
                "&fWould you like CombatPlus to send",
                "&fAn Actionbar message to the Damager",
                "&fIndicating the Target's Health and Damage Dealt?"
        ));

        ArrayList<String> epearlLore = new ArrayList<>();
        epearlLore.add("");
        epearlLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.ENDERPEARL_ENABLED.getKey()));
        epearlLore.add("");
        epearlLore.add("&fWould you like a Cooldown");
        epearlLore.add("&fBetween using Ender Pearls?");
        epearlLore.add("");
        epearlLore.add("&7More options in the Config.yml");
        ItemStack epearl = makeItem(Material.PAPER, 1, "&6Ender Pearl Cooldown", epearlLore);

        inventory.setItem(10, gapple);
        inventory.setItem(12, gappleE);
        inventory.setItem(14, disItems);
        inventory.setItem(16, health);
        inventory.setItem(28, bar);
        inventory.setItem(30, offh);
        inventory.setItem(32, proj);
        inventory.setItem(34, epearl);
    }
}