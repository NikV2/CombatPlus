package me.nik.combatplus.api;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.handlers.CombatPlusHolder;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class GUIManager {

    private static Inventory mainGUI;
    private static Inventory pluginGUI;
    private static Inventory combatGUI;
    private static Inventory generalGUI;

    public static void openMainGUI(Player p) {
        Inventory mainGUI = Bukkit.createInventory(new CombatPlusHolder(), 36, Messenger.format(Lang.get().getString("gui.main")));
        ItemStack settings = new ItemStack(Material.BOOK, 1);
        ItemMeta settings_meta = settings.getItemMeta();
        settings_meta.setDisplayName("§6Plugin Settings");
        settings.setItemMeta(settings_meta);

        ItemStack combatSettings = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemMeta combatSettings_meta = combatSettings.getItemMeta();
        combatSettings_meta.setDisplayName("§eCombat Settings");
        combatSettings.setItemMeta(combatSettings_meta);

        ItemStack generalSettings = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta generalSettings_meta = generalSettings.getItemMeta();
        generalSettings_meta.setDisplayName("§aGeneral Settings");
        generalSettings.setItemMeta(generalSettings_meta);

        ItemStack support = new ItemStack(Material.DIAMOND, 1);
        ItemMeta support_meta = support.getItemMeta();
        support_meta.setDisplayName("§bLooking for Support?");
        support.setItemMeta(support_meta);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta close_meta = close.getItemMeta();
        close_meta.setDisplayName("§cReload and Exit");
        close.setItemMeta(close_meta);

        mainGUI.setItem(31, close);
        mainGUI.setItem(16, support);
        mainGUI.setItem(14, generalSettings);
        mainGUI.setItem(12, combatSettings);
        mainGUI.setItem(10, settings);
        p.openInventory(mainGUI);
    }

    public static void openPluginGUI(Player p) {
        CombatPlus plugin = CombatPlus.getPlugin(CombatPlus.class);
        Inventory pluginGUI = Bukkit.createInventory(new CombatPlusHolder(), 36, Messenger.format(Lang.get().getString("gui.plugin")));
        p.openInventory(pluginGUI);
        final Player pNon = p;
        new BukkitRunnable() {

            @Override
            public void run() {
                InventoryView guiView = pNon.getOpenInventory();
                if (!(guiView.getTopInventory().getHolder() instanceof CombatPlusHolder)) {
                    cancel();
                    return;
                }
                ItemStack checkUpdates = new ItemStack(Material.PAPER, 1);
                ItemMeta checkUpdates_meta = checkUpdates.getItemMeta();
                checkUpdates_meta.setDisplayName("§6Check for Updates");
                ArrayList<String> updatesLore = new ArrayList<>();
                updatesLore.add("");
                updatesLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("settings.check_for_updates"));
                updatesLore.add("");
                updatesLore.add(ChatColor.WHITE + "Would you like the plugin");
                updatesLore.add(ChatColor.WHITE + "To Check for Updates?");
                checkUpdates_meta.setLore(updatesLore);
                checkUpdates.setItemMeta(checkUpdates_meta);

                ItemStack async = new ItemStack(Material.PAPER, 1);
                ItemMeta async_meta = async.getItemMeta();
                async_meta.setDisplayName("§6Run Asynchronously");
                ArrayList<String> asyncLore = new ArrayList<>();
                asyncLore.add("");
                asyncLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("settings.async"));
                asyncLore.add("");
                asyncLore.add(ChatColor.WHITE + "Would you like the plugin");
                asyncLore.add(ChatColor.WHITE + "To run tasks Asynchronously?");
                async_meta.setLore(asyncLore);
                async.setItemMeta(async_meta);

                ItemStack dev = new ItemStack(Material.PAPER, 1);
                ItemMeta dev_meta = dev.getItemMeta();
                dev_meta.setDisplayName("§6Developer Mode");
                ArrayList<String> devLore = new ArrayList<>();
                devLore.add("");
                devLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("settings.developer_mode"));
                devLore.add("");
                devLore.add(ChatColor.WHITE + "Would you like to receive");
                devLore.add(ChatColor.WHITE + "Debugging Information?");
                dev_meta.setLore(devLore);
                dev.setItemMeta(dev_meta);

                ItemStack back = new ItemStack(Material.BARRIER, 1);
                ItemMeta back_meta = back.getItemMeta();
                back_meta.setDisplayName("§cBack");
                back.setItemMeta(back_meta);

                pluginGUI.setItem(11, checkUpdates);
                pluginGUI.setItem(13, async);
                pluginGUI.setItem(15, dev);
                pluginGUI.setItem(31, back);
            }
        }.runTaskTimer(plugin, 1, 5);
    }

    public static void openCombatGUI(Player p) {
        CombatPlus plugin = CombatPlus.getPlugin(CombatPlus.class);
        Inventory combatGUI = Bukkit.createInventory(new CombatPlusHolder(), 45, Messenger.format(Lang.get().getString("gui.combat")));
        p.openInventory(combatGUI);
        final Player pNon = p;
        new BukkitRunnable() {

            @Override
            public void run() {
                InventoryView guiView = pNon.getOpenInventory();
                if (!(guiView.getTopInventory().getHolder() instanceof CombatPlusHolder)) {
                    cancel();
                    return;
                }
                //old att speed
                ItemStack oldPvp = new ItemStack(Material.PAPER, 1);
                ItemMeta oldPvp_meta = oldPvp.getItemMeta();
                oldPvp_meta.setDisplayName("§6Old PvP");
                ArrayList<String> oldPvpLore = new ArrayList<>();
                oldPvpLore.add("");
                oldPvpLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("combat.settings.old_pvp"));
                oldPvpLore.add("");
                oldPvpLore.add(ChatColor.WHITE + "Would you like your server to use");
                oldPvpLore.add(ChatColor.WHITE + "1.8 PvP Combat?");
                oldPvp_meta.setLore(oldPvpLore);
                oldPvp.setItemMeta(oldPvp_meta);
                //old wep dmg
                ItemStack oldWepDmg = new ItemStack(Material.PAPER, 1);
                ItemMeta oldWepDmg_meta = oldWepDmg.getItemMeta();
                oldWepDmg_meta.setDisplayName("§6Old Weapon Damage");
                ArrayList<String> oldWepDmgLore = new ArrayList<>();
                oldWepDmgLore.add("");
                oldWepDmgLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("combat.settings.old_weapon_damage"));
                oldWepDmgLore.add("");
                oldWepDmgLore.add(ChatColor.WHITE + "Would you like Swords to Deal");
                oldWepDmgLore.add(ChatColor.WHITE + "Damage just like in 1.8?");
                oldWepDmg_meta.setLore(oldWepDmgLore);
                oldWepDmg.setItemMeta(oldWepDmg_meta);
                //old tool dmg
                ItemStack oldToolDmg = new ItemStack(Material.PAPER, 1);
                ItemMeta oldToolDmg_meta = oldToolDmg.getItemMeta();
                oldToolDmg_meta.setDisplayName("§6Old Tool Damage");
                ArrayList<String> oldToolDmgLore = new ArrayList<>();
                oldToolDmgLore.add("");
                oldToolDmgLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("combat.settings.old_tool_damage"));
                oldToolDmgLore.add("");
                oldToolDmgLore.add(ChatColor.WHITE + "Would you like Tools to Deal");
                oldToolDmgLore.add(ChatColor.WHITE + "Damage just like in 1.8?");
                oldToolDmg_meta.setLore(oldToolDmgLore);
                oldToolDmg.setItemMeta(oldToolDmg_meta);
                //old sharp
                ItemStack oldSharp = new ItemStack(Material.PAPER, 1);
                ItemMeta oldSharp_meta = oldSharp.getItemMeta();
                oldSharp_meta.setDisplayName("§6Old Sharpness");
                ArrayList<String> oldSharpLore = new ArrayList<>();
                oldSharpLore.add("");
                oldSharpLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("combat.settings.old_sharpness"));
                oldSharpLore.add("");
                oldSharpLore.add(ChatColor.WHITE + "Would you like Sharpness");
                oldSharpLore.add(ChatColor.WHITE + "To work just like in 1.8?");
                oldSharp_meta.setLore(oldSharpLore);
                oldSharp.setItemMeta(oldSharp_meta);
                //disable sweep
                ItemStack disableSweep = new ItemStack(Material.PAPER, 1);
                ItemMeta disableSweep_meta = disableSweep.getItemMeta();
                disableSweep_meta.setDisplayName("§6Disable Sweep Attacks");
                ArrayList<String> disableSweepLore = new ArrayList<>();
                disableSweepLore.add("");
                disableSweepLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("combat.settings.disable_sweep_attacks"));
                disableSweepLore.add("");
                disableSweepLore.add(ChatColor.WHITE + "Would you like to Disable");
                disableSweepLore.add(ChatColor.WHITE + "Sweep Attacks?");
                disableSweep_meta.setLore(disableSweepLore);
                disableSweep.setItemMeta(disableSweep_meta);
                //bow boost
                ItemStack disableBoost = new ItemStack(Material.PAPER, 1);
                ItemMeta disableBoost_meta = disableBoost.getItemMeta();
                disableBoost_meta.setDisplayName("§6Disable Arrow Boost");
                ArrayList<String> disableBoostLore = new ArrayList<>();
                disableBoostLore.add("");
                disableBoostLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("combat.settings.disable_arrow_boost"));
                disableBoostLore.add("");
                disableBoostLore.add(ChatColor.WHITE + "Would you like to Prevent");
                disableBoostLore.add(ChatColor.WHITE + "Players from Boosting themselves?");
                disableBoost_meta.setLore(disableBoostLore);
                disableBoost.setItemMeta(disableBoost_meta);
                //old regen
                ItemStack oldRegen = new ItemStack(Material.PAPER, 1);
                ItemMeta oldRegen_meta = oldRegen.getItemMeta();
                oldRegen_meta.setDisplayName("§6Old Player Regen");
                ArrayList<String> oldRegenLore = new ArrayList<>();
                oldRegenLore.add("");
                oldRegenLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("combat.settings.old_player_regen"));
                oldRegenLore.add("");
                oldRegenLore.add(ChatColor.WHITE + "Would you like Regeneration");
                oldRegenLore.add(ChatColor.WHITE + "To work just like in 1.8?");
                oldRegen_meta.setLore(oldRegenLore);
                oldRegen.setItemMeta(oldRegen_meta);

                ItemStack back = new ItemStack(Material.BARRIER, 1);
                ItemMeta back_meta = back.getItemMeta();
                back_meta.setDisplayName("§cBack");
                back.setItemMeta(back_meta);

                combatGUI.setItem(10, oldPvp);
                combatGUI.setItem(12, oldWepDmg);
                combatGUI.setItem(14, oldToolDmg);
                combatGUI.setItem(16, oldSharp);
                combatGUI.setItem(20, disableSweep);
                combatGUI.setItem(22, disableBoost);
                combatGUI.setItem(24, oldRegen);
                combatGUI.setItem(40, back);
            }
        }.runTaskTimer(plugin, 1, 5);
    }

    public static void openGeneralGUI(Player p) {
        CombatPlus plugin = CombatPlus.getPlugin(CombatPlus.class);
        Inventory generalGUI = Bukkit.createInventory(new CombatPlusHolder(), 54, Messenger.format(Lang.get().getString("gui.general")));
        p.openInventory(generalGUI);
        final Player pNon = p;
        new BukkitRunnable() {

            @Override
            public void run() {
                InventoryView guiView = pNon.getOpenInventory();
                if (!(guiView.getTopInventory().getHolder() instanceof CombatPlusHolder)) {
                    cancel();
                    return;
                }
                //golden apple
                ItemStack gapple = new ItemStack(Material.PAPER, 1);
                ItemMeta gapple_meta = gapple.getItemMeta();
                gapple_meta.setDisplayName("§6Golden Apple Cooldown");
                ArrayList<String> gappleLore = new ArrayList<>();
                gappleLore.add("");
                gappleLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("golden_apple_cooldown.golden_apple.enabled"));
                gappleLore.add("");
                gappleLore.add(ChatColor.WHITE + "Would you like a Cooldown");
                gappleLore.add(ChatColor.WHITE + "Between eating Golden Apples?");
                gappleLore.add("");
                gappleLore.add(ChatColor.GRAY + "More options in the Config.yml");
                gapple_meta.setLore(gappleLore);
                gapple.setItemMeta(gapple_meta);
                //god apple
                ItemStack gappleE = new ItemStack(Material.PAPER, 1);
                ItemMeta gappleE_meta = gappleE.getItemMeta();
                gappleE_meta.setDisplayName("§6Enchanted Golden Apple Cooldown");
                ArrayList<String> gappleELore = new ArrayList<>();
                gappleELore.add("");
                gappleELore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("golden_apple_cooldown.enchanted_golden_apple.enabled"));
                gappleELore.add("");
                gappleELore.add(ChatColor.WHITE + "Would you like a Cooldown");
                gappleELore.add(ChatColor.WHITE + "Between eating Enchanted Golden Apples?");
                gappleELore.add("");
                gappleELore.add(ChatColor.GRAY + "More options in the Config.yml");
                gappleE_meta.setLore(gappleELore);
                gappleE.setItemMeta(gappleE_meta);
                //disabled items
                ItemStack disItems = new ItemStack(Material.PAPER, 1);
                ItemMeta disItems_meta = disItems.getItemMeta();
                disItems_meta.setDisplayName("§6Disabled Items");
                ArrayList<String> disItemsLore = new ArrayList<>();
                disItemsLore.add("");
                disItemsLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("disabled_items.enabled"));
                disItemsLore.add("");
                disItemsLore.add(ChatColor.WHITE + "Would you like to Disable specific");
                disItemsLore.add(ChatColor.WHITE + "Items from being Crafted?");
                disItemsLore.add("");
                disItemsLore.add(ChatColor.GRAY + "More options in the Config.yml");
                disItems_meta.setLore(disItemsLore);
                disItems.setItemMeta(disItems_meta);
                //disable item frame rotation
                ItemStack disRotate = new ItemStack(Material.PAPER, 1);
                ItemMeta disRotate_meta = disRotate.getItemMeta();
                disRotate_meta.setDisplayName("§6Disable Item Frame Rotation");
                ArrayList<String> disRotateLore = new ArrayList<>();
                disRotateLore.add("");
                disRotateLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("disable_item_frame_rotation.enabled"));
                disRotateLore.add("");
                disRotateLore.add(ChatColor.WHITE + "Would you like to Prevent");
                disRotateLore.add(ChatColor.WHITE + "Items inside Item Frames from Rotating?");
                disRotate_meta.setLore(disRotateLore);
                disRotate.setItemMeta(disRotate_meta);
                //disable offhand
                ItemStack offh = new ItemStack(Material.PAPER, 1);
                ItemMeta offh_meta = offh.getItemMeta();
                offh_meta.setDisplayName("§6Disable Offhand");
                ArrayList<String> offhLore = new ArrayList<>();
                offhLore.add("");
                offhLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("disable_offhand.enabled"));
                offhLore.add("");
                offhLore.add(ChatColor.WHITE + "Would you like to Prevent");
                offhLore.add(ChatColor.WHITE + "Players from using the Offhand?");
                offh_meta.setLore(offhLore);
                offh.setItemMeta(offh_meta);
                //projectile fixer
                ItemStack proj = new ItemStack(Material.PAPER, 1);
                ItemMeta proj_meta = proj.getItemMeta();
                proj_meta.setDisplayName("§6Projectile Fixer");
                ArrayList<String> projLore = new ArrayList<>();
                projLore.add("");
                projLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + Config.get().getBoolean("fixes.projectile_fixer"));
                projLore.add("");
                projLore.add(ChatColor.WHITE + "Fixes a Bug with Projectiles");
                projLore.add(ChatColor.WHITE + "Not always going straight");
                proj_meta.setLore(projLore);
                proj.setItemMeta(proj_meta);

                ItemStack back = new ItemStack(Material.BARRIER, 1);
                ItemMeta back_meta = back.getItemMeta();
                back_meta.setDisplayName("§cBack");
                back.setItemMeta(back_meta);

                generalGUI.setItem(11, gapple);
                generalGUI.setItem(13, gappleE);
                generalGUI.setItem(15, disItems);
                generalGUI.setItem(29, disRotate);
                generalGUI.setItem(31, offh);
                generalGUI.setItem(33, proj);
                generalGUI.setItem(49, back);
            }
        }.runTaskTimer(plugin, 1, 5);
    }
}
