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

public class CombatGui extends Menu {
    public CombatGui(PlayerMenuUtility playerMenuUtility, CombatPlus plugin) {
        super(playerMenuUtility, plugin);
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
            case 12:
                changeConfigBoolean(Config.Setting.OLD_PVP.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 14:
                changeConfigBoolean(Config.Setting.OLD_WEAPON_DAMAGE.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 16:
                changeConfigBoolean(Config.Setting.OLD_TOOL_DAMAGE.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 28:
                changeConfigBoolean(Config.Setting.OLD_SHARPNESS.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 30:
                changeConfigBoolean(Config.Setting.DISABLE_SWEEP_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 32:
                changeConfigBoolean(Config.Setting.DISABLE_ARROW_BOOST.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 34:
                changeConfigBoolean(Config.Setting.OLD_REGEN.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 10:
                changeConfigBoolean(Config.Setting.SWORD_BLOCKING_ENABLED.getKey());
                getInventory().clear();
                setMenuItems();
                break;
            case 49:
                p.closeInventory();
                new MainGui(playerMenuUtility, plugin).open();
                break;
        }
    }

    @Override
    protected void setMenuItems() {
        ItemStack back = makeItem(Material.BARRIER, 1, "&cBack", null);

        inventory.setItem(49, back);
        ArrayList<String> oldPvpLore = new ArrayList<>();
        oldPvpLore.add("");
        oldPvpLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.OLD_PVP.getKey()));
        oldPvpLore.add("");
        oldPvpLore.add("&fWould you like your server to use");
        oldPvpLore.add("&f1.8 PvP Combat?");
        ItemStack oldPvp = makeItem(Material.PAPER, 1, "&6Old PvP", oldPvpLore);

        ArrayList<String> oldWepDmgLore = new ArrayList<>();
        oldWepDmgLore.add("");
        oldWepDmgLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.OLD_WEAPON_DAMAGE.getKey()));
        oldWepDmgLore.add("");
        oldWepDmgLore.add("&fWould you like Swords to Deal");
        oldWepDmgLore.add("&fDamage just like in 1.8?");
        ItemStack oldWepDmg = makeItem(Material.PAPER, 1, "&6Old Weapon Damage", oldWepDmgLore);

        ArrayList<String> oldToolDmgLore = new ArrayList<>();
        oldToolDmgLore.add("");
        oldToolDmgLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.OLD_TOOL_DAMAGE.getKey()));
        oldToolDmgLore.add("");
        oldToolDmgLore.add("&fWould you like Tools to Deal");
        oldToolDmgLore.add("&fDamage just like in 1.8?");
        ItemStack oldToolDmg = makeItem(Material.PAPER, 1, "&6Old Tool Damage", oldToolDmgLore);

        ArrayList<String> oldSharpLore = new ArrayList<>();
        oldSharpLore.add("");
        oldSharpLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.OLD_SHARPNESS.getKey()));
        oldSharpLore.add("");
        oldSharpLore.add("&fWould you like Sharpness");
        oldSharpLore.add("&fTo work just like in 1.8?");
        ItemStack oldSharp = makeItem(Material.PAPER, 1, "&6Old Sharpness", oldSharpLore);

        ArrayList<String> disableSweepLore = new ArrayList<>();
        disableSweepLore.add("");
        disableSweepLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.DISABLE_SWEEP_ENABLED.getKey()));
        disableSweepLore.add("");
        disableSweepLore.add("&fWould you like to Disable");
        disableSweepLore.add("&fSweep Attacks?");
        ItemStack disableSweep = makeItem(Material.PAPER, 1, "&6Disable Sweep Attacks", disableSweepLore);

        ArrayList<String> disableBoostLore = new ArrayList<>();
        disableBoostLore.add("");
        disableBoostLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.DISABLE_ARROW_BOOST.getKey()));
        disableBoostLore.add("");
        disableBoostLore.add("&fWould you like to Prevent");
        disableBoostLore.add("&fPlayers from Boosting themselves?");
        ItemStack disableBoost = makeItem(Material.PAPER, 1, "&6Disable Arrow Boost", disableBoostLore);

        ArrayList<String> oldRegenLore = new ArrayList<>();
        oldRegenLore.add("");
        oldRegenLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.OLD_REGEN.getKey()));
        oldRegenLore.add("");
        oldRegenLore.add("&fWould you like Regeneration");
        oldRegenLore.add("&fTo work just like in 1.8?");
        ItemStack oldRegen = makeItem(Material.PAPER, 1, "&6Old Player Regen", oldRegenLore);

        ArrayList<String> blockLore = new ArrayList<>();
        blockLore.add("");
        blockLore.add("&7Currently set to: &a" + getConfigValue(Config.Setting.SWORD_BLOCKING_ENABLED.getKey()));
        blockLore.add("");
        blockLore.add("&fGives Resistance While");
        blockLore.add("&fRight Clicking With a Sword");
        blockLore.add("");
        blockLore.add("&7More options in the Config.yml");
        ItemStack block = makeItem(Material.PAPER, 1, "&6Sword Blocking", blockLore);

        inventory.setItem(10, block);
        inventory.setItem(12, oldPvp);
        inventory.setItem(14, oldWepDmg);
        inventory.setItem(16, oldToolDmg);
        inventory.setItem(28, oldSharp);
        inventory.setItem(30, disableSweep);
        inventory.setItem(32, disableBoost);
        inventory.setItem(34, oldRegen);
    }
}