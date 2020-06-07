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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class CombatGui extends Menu {
    public CombatGui(PlayerMenuUtility playerMenuUtility, CombatPlus plugin) {
        super(playerMenuUtility, plugin);
    }

    @Override
    protected String getMenuName() {
        return Messenger.format(Lang.get().getString("gui.combat"));
    }

    @Override
    protected int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
            case "§6Old PvP":
                booleanSet("combat.settings.old_pvp", !configBoolean("combat.settings.old_pvp"));
                saveAndReload();
                break;
            case "§6Old Weapon Damage":
                booleanSet("combat.settings.old_weapon_damage", !configBoolean("combat.settings.old_weapon_damage"));
                saveAndReload();
                break;
            case "§6Old Tool Damage":
                booleanSet("combat.settings.old_tool_damage", !configBoolean("combat.settings.old_tool_damage"));
                saveAndReload();
                break;
            case "§6Old Sharpness":
                booleanSet("combat.settings.old_sharpness", !configBoolean("combat.settings.old_sharpness"));
                saveAndReload();
                break;
            case "§6Disable Sweep Attacks":
                booleanSet("combat.settings.disable_sweep_attacks.enabled", !configBoolean("combat.settings.disable_sweep_attacks.enabled"));
                saveAndReload();
                break;
            case "§6Disable Arrow Boost":
                booleanSet("combat.settings.disable_arrow_boost", !configBoolean("combat.settings.disable_arrow_boost"));
                saveAndReload();
                break;
            case "§6Old Player Regen":
                booleanSet("combat.settings.old_player_regen", !configBoolean("combat.settings.old_player_regen"));
                saveAndReload();
                break;
            case "§6Sword Blocking":
                booleanSet("combat.settings.sword_blocking.enabled", !configBoolean("combat.settings.sword_blocking.enabled"));
                saveAndReload();
                break;
            case "§cBack":
                p.closeInventory();
                new MainGui(playerMenuUtility, plugin).open();
                break;
        }
    }

    @Override
    protected void setMenuItems() {
        ItemStack back = makeItem(Material.BARRIER, 1, "§cBack", null);

        inventory.setItem(49, back);

        new BukkitRunnable() {
            public void run() {
                if (!(inventory.getHolder() instanceof Menu)) {
                    cancel();
                    return;
                }
                ArrayList<String> oldPvpLore = new ArrayList<>();
                oldPvpLore.add("");
                oldPvpLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.old_pvp"));
                oldPvpLore.add("");
                oldPvpLore.add(ChatColor.WHITE + "Would you like your server to use");
                oldPvpLore.add(ChatColor.WHITE + "1.8 PvP Combat?");
                ItemStack oldPvp = makeItem(Material.PAPER, 1, "§6Old PvP", oldPvpLore);

                ArrayList<String> oldWepDmgLore = new ArrayList<>();
                oldWepDmgLore.add("");
                oldWepDmgLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.old_weapon_damage"));
                oldWepDmgLore.add("");
                oldWepDmgLore.add(ChatColor.WHITE + "Would you like Swords to Deal");
                oldWepDmgLore.add(ChatColor.WHITE + "Damage just like in 1.8?");
                ItemStack oldWepDmg = makeItem(Material.PAPER, 1, "§6Old Weapon Damage", oldWepDmgLore);

                ArrayList<String> oldToolDmgLore = new ArrayList<>();
                oldToolDmgLore.add("");
                oldToolDmgLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.old_tool_damage"));
                oldToolDmgLore.add("");
                oldToolDmgLore.add(ChatColor.WHITE + "Would you like Tools to Deal");
                oldToolDmgLore.add(ChatColor.WHITE + "Damage just like in 1.8?");
                ItemStack oldToolDmg = makeItem(Material.PAPER, 1, "§6Old Tool Damage", oldToolDmgLore);

                ArrayList<String> oldSharpLore = new ArrayList<>();
                oldSharpLore.add("");
                oldSharpLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.old_sharpness"));
                oldSharpLore.add("");
                oldSharpLore.add(ChatColor.WHITE + "Would you like Sharpness");
                oldSharpLore.add(ChatColor.WHITE + "To work just like in 1.8?");
                ItemStack oldSharp = makeItem(Material.PAPER, 1, "§6Old Sharpness", oldSharpLore);

                ArrayList<String> disableSweepLore = new ArrayList<>();
                disableSweepLore.add("");
                disableSweepLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.disable_sweep_attacks.enabled"));
                disableSweepLore.add("");
                disableSweepLore.add(ChatColor.WHITE + "Would you like to Disable");
                disableSweepLore.add(ChatColor.WHITE + "Sweep Attacks?");
                ItemStack disableSweep = makeItem(Material.PAPER, 1, "§6Disable Sweep Attacks", disableSweepLore);

                ArrayList<String> disableBoostLore = new ArrayList<>();
                disableBoostLore.add("");
                disableBoostLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.disable_arrow_boost"));
                disableBoostLore.add("");
                disableBoostLore.add(ChatColor.WHITE + "Would you like to Prevent");
                disableBoostLore.add(ChatColor.WHITE + "Players from Boosting themselves?");
                ItemStack disableBoost = makeItem(Material.PAPER, 1, "§6Disable Arrow Boost", disableBoostLore);

                ArrayList<String> oldRegenLore = new ArrayList<>();
                oldRegenLore.add("");
                oldRegenLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.old_player_regen"));
                oldRegenLore.add("");
                oldRegenLore.add(ChatColor.WHITE + "Would you like Regeneration");
                oldRegenLore.add(ChatColor.WHITE + "To work just like in 1.8?");
                ItemStack oldRegen = makeItem(Material.PAPER, 1, "§6Old Player Regen", oldRegenLore);

                ArrayList<String> blockLore = new ArrayList<>();
                blockLore.add("");
                blockLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.sword_blocking.enabled"));
                blockLore.add("");
                blockLore.add(ChatColor.WHITE + "Gives Resistance While");
                blockLore.add(ChatColor.WHITE + "Right Clicking With a Sword");
                blockLore.add("");
                blockLore.add(ChatColor.GRAY + "More options in the Config.yml");
                ItemStack block = makeItem(Material.PAPER, 1, "§6Sword Blocking", blockLore);

                inventory.setItem(10, block);
                inventory.setItem(12, oldPvp);
                inventory.setItem(14, oldWepDmg);
                inventory.setItem(16, oldToolDmg);
                inventory.setItem(28, oldSharp);
                inventory.setItem(30, disableSweep);
                inventory.setItem(32, disableBoost);
                inventory.setItem(34, oldRegen);
            }
        }.runTaskTimer(plugin, 1, 5);
    }
}
