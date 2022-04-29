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
import java.util.Arrays;
import java.util.List;

public class GeneralGui extends Menu {
    public GeneralGui(PlayerMenu playerMenu, CombatPlus plugin) {
        super(playerMenu, plugin);
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
                new MainGui(playerMenu, plugin).open();
                break;
            case 10:
                changeConfigBoolean(Config.Setting.GOLDEN_APPLE_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 12:
                changeConfigBoolean(Config.Setting.ENCHANTED_APPLE_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 14:
                changeConfigBoolean(Config.Setting.CUSTOM_PLAYER_HEALTH_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 16:
                changeConfigBoolean(Config.Setting.HEALTHBAR_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 28:
                changeConfigBoolean(Config.Setting.DISABLE_OFFHAND.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 30:
                changeConfigBoolean(Config.Setting.COMBATLOG_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 32:
                changeConfigBoolean(Config.Setting.ENDERPEARL_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 34:
                changeConfigBoolean(Config.Setting.FISHING_ROD_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
        }
    }

    @Override
    protected void setMenuItems() {
        ItemStack back = makeItem(Material.BARRIER, 1, "&cBack", null);

        inventory.setItem(49, back);
        List<String> gappleLore = new ArrayList<>();
        gappleLore.add("");
        gappleLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.GOLDEN_APPLE_ENABLED.getKey()));
        gappleLore.add("");
        gappleLore.add("&fWould you like a Cooldown");
        gappleLore.add("&fBetween eating Golden Apples?");
        gappleLore.add("");
        gappleLore.add("&7More options in the Config.yml");
        ItemStack gapple = makeItem(Material.PAPER, 1, "&6Golden Apple Cooldown", gappleLore);

        List<String> gappleELore = new ArrayList<>();
        gappleELore.add("");
        gappleELore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.ENCHANTED_APPLE_ENABLED.getKey()));
        gappleELore.add("");
        gappleELore.add("&fWould you like a Cooldown");
        gappleELore.add("&fBetween eating Enchanted Golden Apples?");
        gappleELore.add("");
        gappleELore.add("&7More options in the Config.yml");
        ItemStack gappleE = makeItem(Material.PAPER, 1, "&6Enchanted Golden Apple Cooldown", gappleELore);

        List<String> offhLore = new ArrayList<>();
        offhLore.add("");
        offhLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.DISABLE_OFFHAND.getKey()));
        offhLore.add("");
        offhLore.add("&fWould you like to Prevent");
        offhLore.add("&fPlayers from using the Offhand?");
        ItemStack offh = makeItem(Material.PAPER, 1, "&6Disable Offhand", offhLore);

        List<String> combatlogLore = new ArrayList<>();
        combatlogLore.add("");
        combatlogLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.COMBATLOG_ENABLED.getKey()));
        combatlogLore.add("");
        combatlogLore.add("&fPrevents players from logging out");
        combatlogLore.add("&fWhile in Combat");
        combatlogLore.add("");
        combatlogLore.add("&7More options in the Config.yml");

        ItemStack cl = makeItem(Material.PAPER, 1, "&6CombatLog", combatlogLore);

        List<String> healthLore = new ArrayList<>();
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

        List<String> epearlLore = new ArrayList<>();
        epearlLore.add("");
        epearlLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.ENDERPEARL_ENABLED.getKey()));
        epearlLore.add("");
        epearlLore.add("&fWould you like a Cooldown");
        epearlLore.add("&fBetween using Ender Pearls?");
        epearlLore.add("");
        epearlLore.add("&7More options in the Config.yml");
        ItemStack epearl = makeItem(Material.PAPER, 1, "&6Ender Pearl Cooldown", epearlLore);

        List<String> frkLore = new ArrayList<>();
        frkLore.add("");
        frkLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.FISHING_ROD_ENABLED.getKey()));
        frkLore.add("");
        frkLore.add("&fMakes Fishing Rods Knockback");
        frkLore.add("&fPlayers just like in 1.8");
        frkLore.add("");
        frkLore.add("&7More options in the Config.yml");

        ItemStack frk = makeItem(Material.PAPER, 1, "&6Fishing Rod Knockback", frkLore);

        inventory.setItem(10, gapple);
        inventory.setItem(12, gappleE);
        inventory.setItem(14, health);
        inventory.setItem(16, bar);
        inventory.setItem(28, offh);
        inventory.setItem(30, cl);
        inventory.setItem(32, epearl);
        inventory.setItem(34, frk);
    }
}