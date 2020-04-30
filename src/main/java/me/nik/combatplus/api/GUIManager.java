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

    private final CombatPlus plugin = CombatPlus.getInstance();

    public void openMainGUI(Player p) {
        Inventory mainGUI = Bukkit.createInventory(new CombatPlusHolder(), 36, Messenger.format(Lang.get().getString("gui.main")));
        ItemStack settings = new ItemStack(Material.BOOK, 1);
        ItemMeta settingsMeta = settings.getItemMeta();
        settingsMeta.setDisplayName("§6Plugin Settings");
        settings.setItemMeta(settingsMeta);

        ItemStack combatSettings = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemMeta combatSettingsMeta = combatSettings.getItemMeta();
        combatSettingsMeta.setDisplayName("§eCombat Settings");
        combatSettings.setItemMeta(combatSettingsMeta);

        ItemStack generalSettings = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta generalSettingsMeta = generalSettings.getItemMeta();
        generalSettingsMeta.setDisplayName("§aGeneral Settings");
        generalSettings.setItemMeta(generalSettingsMeta);

        ItemStack support = new ItemStack(Material.DIAMOND, 1);
        ItemMeta supportMeta = support.getItemMeta();
        supportMeta.setDisplayName("§bLooking for Support?");
        support.setItemMeta(supportMeta);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName("§cReload and Exit");
        close.setItemMeta(closeMeta);

        mainGUI.setItem(31, close);
        mainGUI.setItem(16, support);
        mainGUI.setItem(14, generalSettings);
        mainGUI.setItem(12, combatSettings);
        mainGUI.setItem(10, settings);
        p.openInventory(mainGUI);
    }

    public void openPluginGUI(Player p) {
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
                ItemMeta checkUpdatesMeta = checkUpdates.getItemMeta();
                checkUpdatesMeta.setDisplayName("§6Check for Updates");
                ArrayList<String> updatesLore = new ArrayList<>();
                updatesLore.add("");
                updatesLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("settings.check_for_updates"));
                updatesLore.add("");
                updatesLore.add(ChatColor.WHITE + "Would you like the plugin");
                updatesLore.add(ChatColor.WHITE + "To Check for Updates?");
                checkUpdatesMeta.setLore(updatesLore);
                checkUpdates.setItemMeta(checkUpdatesMeta);

                ItemStack async = new ItemStack(Material.PAPER, 1);
                ItemMeta asyncMeta = async.getItemMeta();
                asyncMeta.setDisplayName("§6Run Asynchronously");
                ArrayList<String> asyncLore = new ArrayList<>();
                asyncLore.add("");
                asyncLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("settings.async"));
                asyncLore.add("");
                asyncLore.add(ChatColor.WHITE + "Would you like the plugin");
                asyncLore.add(ChatColor.WHITE + "To run tasks Asynchronously?");
                asyncMeta.setLore(asyncLore);
                async.setItemMeta(asyncMeta);

                ItemStack dev = new ItemStack(Material.PAPER, 1);
                ItemMeta devMeta = dev.getItemMeta();
                devMeta.setDisplayName("§6Developer Mode");
                ArrayList<String> devLore = new ArrayList<>();
                devLore.add("");
                devLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("settings.developer_mode"));
                devLore.add("");
                devLore.add(ChatColor.WHITE + "Would you like to receive");
                devLore.add(ChatColor.WHITE + "Debugging Information?");
                devMeta.setLore(devLore);
                dev.setItemMeta(devMeta);

                ItemStack back = new ItemStack(Material.BARRIER, 1);
                ItemMeta backMeta = back.getItemMeta();
                backMeta.setDisplayName("§cBack");
                back.setItemMeta(backMeta);

                pluginGUI.setItem(11, checkUpdates);
                pluginGUI.setItem(13, async);
                pluginGUI.setItem(15, dev);
                pluginGUI.setItem(31, back);
            }
        }.runTaskTimer(plugin, 1, 5);
    }

    public void openCombatGUI(Player p) {
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
                ItemMeta oldPvpMeta = oldPvp.getItemMeta();
                oldPvpMeta.setDisplayName("§6Old PvP");
                ArrayList<String> oldPvpLore = new ArrayList<>();
                oldPvpLore.add("");
                oldPvpLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.old_pvp"));
                oldPvpLore.add("");
                oldPvpLore.add(ChatColor.WHITE + "Would you like your server to use");
                oldPvpLore.add(ChatColor.WHITE + "1.8 PvP Combat?");
                oldPvpMeta.setLore(oldPvpLore);
                oldPvp.setItemMeta(oldPvpMeta);
                //old wep dmg
                ItemStack oldWepDmg = new ItemStack(Material.PAPER, 1);
                ItemMeta oldWepDmgMeta = oldWepDmg.getItemMeta();
                oldWepDmgMeta.setDisplayName("§6Old Weapon Damage");
                ArrayList<String> oldWepDmgLore = new ArrayList<>();
                oldWepDmgLore.add("");
                oldWepDmgLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.old_weapon_damage"));
                oldWepDmgLore.add("");
                oldWepDmgLore.add(ChatColor.WHITE + "Would you like Swords to Deal");
                oldWepDmgLore.add(ChatColor.WHITE + "Damage just like in 1.8?");
                oldWepDmgMeta.setLore(oldWepDmgLore);
                oldWepDmg.setItemMeta(oldWepDmgMeta);
                //old tool dmg
                ItemStack oldToolDmg = new ItemStack(Material.PAPER, 1);
                ItemMeta oldToolDmgMeta = oldToolDmg.getItemMeta();
                oldToolDmgMeta.setDisplayName("§6Old Tool Damage");
                ArrayList<String> oldToolDmgLore = new ArrayList<>();
                oldToolDmgLore.add("");
                oldToolDmgLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.old_tool_damage"));
                oldToolDmgLore.add("");
                oldToolDmgLore.add(ChatColor.WHITE + "Would you like Tools to Deal");
                oldToolDmgLore.add(ChatColor.WHITE + "Damage just like in 1.8?");
                oldToolDmgMeta.setLore(oldToolDmgLore);
                oldToolDmg.setItemMeta(oldToolDmgMeta);
                //old sharp
                ItemStack oldSharp = new ItemStack(Material.PAPER, 1);
                ItemMeta oldSharpMeta = oldSharp.getItemMeta();
                oldSharpMeta.setDisplayName("§6Old Sharpness");
                ArrayList<String> oldSharpLore = new ArrayList<>();
                oldSharpLore.add("");
                oldSharpLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.old_sharpness"));
                oldSharpLore.add("");
                oldSharpLore.add(ChatColor.WHITE + "Would you like Sharpness");
                oldSharpLore.add(ChatColor.WHITE + "To work just like in 1.8?");
                oldSharpMeta.setLore(oldSharpLore);
                oldSharp.setItemMeta(oldSharpMeta);
                //disable sweep
                ItemStack disableSweep = new ItemStack(Material.PAPER, 1);
                ItemMeta disableSweepMeta = disableSweep.getItemMeta();
                disableSweepMeta.setDisplayName("§6Disable Sweep Attacks");
                ArrayList<String> disableSweepLore = new ArrayList<>();
                disableSweepLore.add("");
                disableSweepLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.disable_sweep_attacks.enabled"));
                disableSweepLore.add("");
                disableSweepLore.add(ChatColor.WHITE + "Would you like to Disable");
                disableSweepLore.add(ChatColor.WHITE + "Sweep Attacks?");
                disableSweepMeta.setLore(disableSweepLore);
                disableSweep.setItemMeta(disableSweepMeta);
                //bow boost
                ItemStack disableBoost = new ItemStack(Material.PAPER, 1);
                ItemMeta disableBoostMeta = disableBoost.getItemMeta();
                disableBoostMeta.setDisplayName("§6Disable Arrow Boost");
                ArrayList<String> disableBoostLore = new ArrayList<>();
                disableBoostLore.add("");
                disableBoostLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.disable_arrow_boost"));
                disableBoostLore.add("");
                disableBoostLore.add(ChatColor.WHITE + "Would you like to Prevent");
                disableBoostLore.add(ChatColor.WHITE + "Players from Boosting themselves?");
                disableBoostMeta.setLore(disableBoostLore);
                disableBoost.setItemMeta(disableBoostMeta);
                //old regen
                ItemStack oldRegen = new ItemStack(Material.PAPER, 1);
                ItemMeta oldRegenMeta = oldRegen.getItemMeta();
                oldRegenMeta.setDisplayName("§6Old Player Regen");
                ArrayList<String> oldRegenLore = new ArrayList<>();
                oldRegenLore.add("");
                oldRegenLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("combat.settings.old_player_regen"));
                oldRegenLore.add("");
                oldRegenLore.add(ChatColor.WHITE + "Would you like Regeneration");
                oldRegenLore.add(ChatColor.WHITE + "To work just like in 1.8?");
                oldRegenMeta.setLore(oldRegenLore);
                oldRegen.setItemMeta(oldRegenMeta);

                ItemStack back = new ItemStack(Material.BARRIER, 1);
                ItemMeta backMeta = back.getItemMeta();
                backMeta.setDisplayName("§cBack");
                back.setItemMeta(backMeta);

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

    public void openGeneralGUI(Player p) {
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
                ItemMeta gappleMeta = gapple.getItemMeta();
                gappleMeta.setDisplayName("§6Golden Apple Cooldown");
                ArrayList<String> gappleLore = new ArrayList<>();
                gappleLore.add("");
                gappleLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("golden_apple_cooldown.golden_apple.enabled"));
                gappleLore.add("");
                gappleLore.add(ChatColor.WHITE + "Would you like a Cooldown");
                gappleLore.add(ChatColor.WHITE + "Between eating Golden Apples?");
                gappleLore.add("");
                gappleLore.add(ChatColor.GRAY + "More options in the Config.yml");
                gappleMeta.setLore(gappleLore);
                gapple.setItemMeta(gappleMeta);
                //god apple
                ItemStack gappleE = new ItemStack(Material.PAPER, 1);
                ItemMeta gappleEMeta = gappleE.getItemMeta();
                gappleEMeta.setDisplayName("§6Enchanted Golden Apple Cooldown");
                ArrayList<String> gappleELore = new ArrayList<>();
                gappleELore.add("");
                gappleELore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("golden_apple_cooldown.enchanted_golden_apple.enabled"));
                gappleELore.add("");
                gappleELore.add(ChatColor.WHITE + "Would you like a Cooldown");
                gappleELore.add(ChatColor.WHITE + "Between eating Enchanted Golden Apples?");
                gappleELore.add("");
                gappleELore.add(ChatColor.GRAY + "More options in the Config.yml");
                gappleEMeta.setLore(gappleELore);
                gappleE.setItemMeta(gappleEMeta);
                //disabled items
                ItemStack disItems = new ItemStack(Material.PAPER, 1);
                ItemMeta disItemsMeta = disItems.getItemMeta();
                disItemsMeta.setDisplayName("§6Disabled Items");
                ArrayList<String> disItemsLore = new ArrayList<>();
                disItemsLore.add("");
                disItemsLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("disabled_items.enabled"));
                disItemsLore.add("");
                disItemsLore.add(ChatColor.WHITE + "Would you like to Disable specific");
                disItemsLore.add(ChatColor.WHITE + "Items from being Crafted?");
                disItemsLore.add("");
                disItemsLore.add(ChatColor.GRAY + "More options in the Config.yml");
                disItemsMeta.setLore(disItemsLore);
                disItems.setItemMeta(disItemsMeta);
                //disable item frame rotation
                ItemStack disRotate = new ItemStack(Material.PAPER, 1);
                ItemMeta disRotateMeta = disRotate.getItemMeta();
                disRotateMeta.setDisplayName("§6Disable Item Frame Rotation");
                ArrayList<String> disRotateLore = new ArrayList<>();
                disRotateLore.add("");
                disRotateLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("disable_item_frame_rotation.enabled"));
                disRotateLore.add("");
                disRotateLore.add(ChatColor.WHITE + "Would you like to Prevent");
                disRotateLore.add(ChatColor.WHITE + "Items inside Item Frames from Rotating?");
                disRotateMeta.setLore(disRotateLore);
                disRotate.setItemMeta(disRotateMeta);
                //disable offhand
                ItemStack offh = new ItemStack(Material.PAPER, 1);
                ItemMeta offhMeta = offh.getItemMeta();
                offhMeta.setDisplayName("§6Disable Offhand");
                ArrayList<String> offhLore = new ArrayList<>();
                offhLore.add("");
                offhLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("disable_offhand.enabled"));
                offhLore.add("");
                offhLore.add(ChatColor.WHITE + "Would you like to Prevent");
                offhLore.add(ChatColor.WHITE + "Players from using the Offhand?");
                offhMeta.setLore(offhLore);
                offh.setItemMeta(offhMeta);
                //projectile fixer
                ItemStack proj = new ItemStack(Material.PAPER, 1);
                ItemMeta projMeta = proj.getItemMeta();
                projMeta.setDisplayName("§6Projectile Fixer");
                ArrayList<String> projLore = new ArrayList<>();
                projLore.add("");
                projLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("fixes.projectile_fixer"));
                projLore.add("");
                projLore.add(ChatColor.WHITE + "Fixes a Bug with Projectiles");
                projLore.add(ChatColor.WHITE + "Not always going straight");
                projMeta.setLore(projLore);
                proj.setItemMeta(projMeta);
                //custom health
                ItemStack health = new ItemStack(Material.PAPER, 1);
                ItemMeta healthMeta = health.getItemMeta();
                healthMeta.setDisplayName("§6Custom Player Health");
                ArrayList<String> healthLore = new ArrayList<>();
                healthLore.add("");
                healthLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("custom.player_health.enabled"));
                healthLore.add("");
                healthLore.add(ChatColor.WHITE + "Would you like your Players");
                healthLore.add(ChatColor.WHITE + "To have Customized Max Health?");
                healthLore.add("");
                healthLore.add(ChatColor.GRAY + "More options in the Config.yml");
                healthMeta.setLore(healthLore);
                health.setItemMeta(healthMeta);
                //epearl cooldown
                ItemStack epearl = new ItemStack(Material.PAPER, 1);
                ItemMeta epearlMeta = epearl.getItemMeta();
                epearlMeta.setDisplayName("§6Ender Pearl Cooldown");
                ArrayList<String> epearlLore = new ArrayList<>();
                epearlLore.add("");
                epearlLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("enderpearl_cooldown.enabled"));
                epearlLore.add("");
                epearlLore.add(ChatColor.WHITE + "Would you like a Cooldown");
                epearlLore.add(ChatColor.WHITE + "Between using Ender Pearls?");
                epearlLore.add("");
                epearlLore.add(ChatColor.GRAY + "More options in the Config.yml");
                epearlMeta.setLore(epearlLore);
                epearl.setItemMeta(epearlMeta);

                ItemStack back = new ItemStack(Material.BARRIER, 1);
                ItemMeta backMeta = back.getItemMeta();
                backMeta.setDisplayName("§cBack");
                back.setItemMeta(backMeta);

                ItemStack next = new ItemStack(Material.BOOK, 1);
                ItemMeta nextMeta = back.getItemMeta();
                nextMeta.setDisplayName("§eNext Page");
                next.setItemMeta(nextMeta);

                generalGUI.setItem(10, gapple);
                generalGUI.setItem(12, gappleE);
                generalGUI.setItem(14, disItems);
                generalGUI.setItem(16, health);
                generalGUI.setItem(28, disRotate);
                generalGUI.setItem(30, offh);
                generalGUI.setItem(32, proj);
                generalGUI.setItem(34, epearl);
                generalGUI.setItem(49, back);
                generalGUI.setItem(53, next);
            }
        }.runTaskTimer(plugin, 1, 5);
    }

    public void openGeneralTwoGUI(Player p) {
        Inventory generalTwoGUI = Bukkit.createInventory(new CombatPlusHolder(), 54, Messenger.format(Lang.get().getString("gui.general")));
        p.openInventory(generalTwoGUI);
        final Player pNon = p;
        new BukkitRunnable() {
            @Override
            public void run() {
                InventoryView guiView = pNon.getOpenInventory();
                if (!(guiView.getTopInventory().getHolder() instanceof CombatPlusHolder)) {
                    cancel();
                    return;
                }
                //Back
                ItemStack back = new ItemStack(Material.BARRIER, 1);
                ItemMeta backMeta = back.getItemMeta();
                backMeta.setDisplayName("§cBack");
                back.setItemMeta(backMeta);

                //previous page
                ItemStack pp = new ItemStack(Material.BOOK, 1);
                ItemMeta ppMeta = pp.getItemMeta();
                ppMeta.setDisplayName("§ePrevious Page");
                pp.setItemMeta(ppMeta);

                //invalid criticals
                ItemStack crit = new ItemStack(Material.PAPER, 1);
                ItemMeta critMeta = crit.getItemMeta();
                critMeta.setDisplayName("§6Invalid Criticals");
                ArrayList<String> critLore = new ArrayList<>();
                critLore.add("");
                critLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("fixes.invalid_criticals"));
                critLore.add("");
                critLore.add(ChatColor.WHITE + "Detects and cancels Invalid Critical Hits");
                critLore.add(ChatColor.WHITE + "Caused by Hacking Clients");
                critMeta.setLore(critLore);
                crit.setItemMeta(critMeta);

                //health spoof
                ItemStack hs = new ItemStack(Material.PAPER, 1);
                ItemMeta hsMeta = hs.getItemMeta();
                hsMeta.setDisplayName("§6Health Spoof");
                ArrayList<String> hsLore = new ArrayList<>();
                hsLore.add("");
                hsLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("fixes.health_spoof"));
                hsLore.add("");
                hsLore.add(ChatColor.WHITE + "Prevents Hacking Clients from Exploiting");
                hsLore.add(ChatColor.WHITE + "Their current Health");
                hsMeta.setLore(hsLore);
                hs.setItemMeta(hsMeta);

                //kill aura
                ItemStack aura = new ItemStack(Material.PAPER, 1);
                ItemMeta auraMeta = aura.getItemMeta();
                auraMeta.setDisplayName("§6Kill Aura");
                ArrayList<String> auraLore = new ArrayList<>();
                auraLore.add("");
                auraLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("fixes.kill_aura"));
                auraLore.add("");
                auraLore.add(ChatColor.WHITE + "Reduces the usage of Kill Aura by");
                auraLore.add(ChatColor.WHITE + "Spawning an Entity behind the Attacker");
                auraMeta.setLore(auraLore);
                aura.setItemMeta(auraMeta);

                generalTwoGUI.setItem(14, aura);
                generalTwoGUI.setItem(12, hs);
                generalTwoGUI.setItem(10, crit);
                generalTwoGUI.setItem(45, pp);
                generalTwoGUI.setItem(49, back);
            }
        }.runTaskTimer(plugin, 1, 5);
    }

    private boolean isEnabled(String path) {
        return Config.get().getBoolean(path);
    }
}
