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
import java.util.List;

public class CombatGui extends Menu {
    public CombatGui(PlayerMenu playerMenu, CombatPlus plugin) {
        super(playerMenu, plugin);
    }

    @Override
    protected String getMenuName() {
        return MsgType.GUI_COMBAT.getMessage();
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
                changeConfigBoolean(Config.Setting.CUSTOM_ATTACK_SPEED_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 12:
                changeConfigBoolean(Config.Setting.DAMAGE_MODIFIERS_OLD_SHARPNESS.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 14:
                changeConfigBoolean(Config.Setting.DAMAGE_MODIFIERS_DISABLE_SWEEP.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 16:
                changeConfigBoolean(Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 28:
                changeConfigBoolean(Config.Setting.DISABLE_ARROW_BOOST.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 30:
                changeConfigBoolean(Config.Setting.CUSTOM_PLAYER_REGENERATION_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 32:
                changeConfigBoolean(Config.Setting.SWORD_BLOCKING_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 34:
                changeConfigBoolean(Config.Setting.CUSTOM_PLAYER_KNOCKBACK_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 49:
                p.closeInventory();
                new MainGui(playerMenu, plugin).open();
                break;
        }
    }

    @Override
    protected void setMenuItems() {

        ItemStack back = makeItem(Material.BARRIER, 1, "&cBack", null);

        inventory.setItem(49, back);

        List<String> customAttackSpeedLore = new ArrayList<>();
        customAttackSpeedLore.add("");
        customAttackSpeedLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.CUSTOM_ATTACK_SPEED_ENABLED.getKey()));
        customAttackSpeedLore.add("");
        customAttackSpeedLore.add("&fWould you like to use");
        customAttackSpeedLore.add("&fCustom attack speed?");
        ItemStack customAttackSpeed = makeItem(Material.PAPER, 1, "&6Custom Attack Speed", customAttackSpeedLore);

        List<String> customToolDamagesLore = new ArrayList<>();
        customToolDamagesLore.add("");
        customToolDamagesLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_ENABLED.getKey()));
        customToolDamagesLore.add("");
        customToolDamagesLore.add("&fWould you like to use");
        customToolDamagesLore.add("&fCustom tool damages?");
        ItemStack customToolDamages = makeItem(Material.PAPER, 1, "&6Custom Tool Damages", customToolDamagesLore);

        List<String> oldSharpnessLore = new ArrayList<>();
        oldSharpnessLore.add("");
        oldSharpnessLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.DAMAGE_MODIFIERS_OLD_SHARPNESS.getKey()));
        oldSharpnessLore.add("");
        oldSharpnessLore.add("&fWould you like to use");
        oldSharpnessLore.add("&f1.8 sharpness behavior?");
        ItemStack oldSharpness = makeItem(Material.PAPER, 1, "&6Old Sharpness", oldSharpnessLore);

        List<String> disableSweepLore = new ArrayList<>();
        disableSweepLore.add("");
        disableSweepLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.DAMAGE_MODIFIERS_DISABLE_SWEEP.getKey()));
        disableSweepLore.add("");
        disableSweepLore.add("&fWould you like to disable");
        disableSweepLore.add("&fThe sweep effect?");
        ItemStack disableSweep = makeItem(Material.PAPER, 1, "&6Disable Sweep", disableSweepLore);

        List<String> disableBoostLore = new ArrayList<>();
        disableBoostLore.add("");
        disableBoostLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.DISABLE_ARROW_BOOST.getKey()));
        disableBoostLore.add("");
        disableBoostLore.add("&fWould you like to disable");
        disableBoostLore.add("&fBow boosting?");
        ItemStack disableBoost = makeItem(Material.PAPER, 1, "&6Disable Arrow Boost", disableBoostLore);

        List<String> customRegenLore = new ArrayList<>();
        customRegenLore.add("");
        customRegenLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.CUSTOM_PLAYER_REGENERATION_ENABLED.getKey()));
        customRegenLore.add("");
        customRegenLore.add("&fWould you like to use");
        customRegenLore.add("&fCustom player regeneration?");
        ItemStack customRegen = makeItem(Material.PAPER, 1, "&6Custom Player Regeneration", customRegenLore);

        List<String> blockLore = new ArrayList<>();
        blockLore.add("");
        blockLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.SWORD_BLOCKING_ENABLED.getKey()));
        blockLore.add("");
        blockLore.add("&fGives Resistance While");
        blockLore.add("&fRight Clicking With a Sword");
        ItemStack block = makeItem(Material.PAPER, 1, "&6Sword Blocking", blockLore);

        List<String> kbLore = new ArrayList<>();
        kbLore.add("");
        kbLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.CUSTOM_PLAYER_KNOCKBACK_ENABLED.getKey()));
        kbLore.add("");
        kbLore.add("&fWould you like to use");
        kbLore.add("&fCustom player knockback?");
        ItemStack kb = makeItem(Material.PAPER, 1, "&6Custom Player Knockback", kbLore);

        inventory.setItem(10, customAttackSpeed);
        inventory.setItem(12, oldSharpness);
        inventory.setItem(14, disableSweep);
        inventory.setItem(16, customToolDamages);
        inventory.setItem(28, disableBoost);
        inventory.setItem(30, customRegen);
        inventory.setItem(32, block);
        inventory.setItem(34, kb);
    }
}