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

public class GeneralGui extends Menu {
    public GeneralGui(PlayerMenuUtility playerMenuUtility, CombatPlus plugin) {
        super(playerMenuUtility, plugin);
    }

    @Override
    protected String getMenuName() {
        return Messenger.format(Lang.get().getString("gui.general"));
    }

    @Override
    protected int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
            case "§cBack":
                p.closeInventory();
                new MainGui(playerMenuUtility, plugin).open();
                break;
            case "§6Golden Apple Cooldown":
                booleanSet("golden_apple_cooldown.golden_apple.enabled", !configBoolean("golden_apple_cooldown.golden_apple.enabled"));
                saveAndReload();
                break;
            case "§6Enchanted Golden Apple Cooldown":
                booleanSet("golden_apple_cooldown.enchanted_golden_apple.enabled", !configBoolean("golden_apple_cooldown.enchanted_golden_apple.enabled"));
                saveAndReload();
                break;
            case "§6Disabled Items":
                booleanSet("disabled_items.enabled", !configBoolean("disabled_items.enabled"));
                saveAndReload();
                break;
            case "§6Disable Item Frame Rotation":
                booleanSet("disable_item_frame_rotation.enabled", !configBoolean("disable_item_frame_rotation.enabled"));
                saveAndReload();
                break;
            case "§6Disable Offhand":
                booleanSet("disable_offhand.enabled", !configBoolean("disable_offhand.enabled"));
                saveAndReload();
                break;
            case "§6Projectile Fixer":
                booleanSet("fixes.projectile_fixer", !configBoolean("fixes.projectile_fixer"));
                saveAndReload();
                break;
            case "§6Custom Player Health":
                booleanSet("custom.player_health.enabled", !configBoolean("custom.player_health.enabled"));
                saveAndReload();
                break;
            case "§6Ender Pearl Cooldown":
                booleanSet("enderpearl_cooldown.enabled", !configBoolean("enderpearl_cooldown.enabled"));
                saveAndReload();
                break;
            case "§eNext Page 1/2":
                p.closeInventory();
                new GeneralTwoGui(playerMenuUtility, plugin).open();
                break;
        }
    }

    @Override
    protected void setMenuItems() {
        ItemStack back = makeItem(Material.BARRIER, 1, "§cBack", null);
        ItemStack next = makeItem(Material.BOOK, 1, "§eNext Page 1/2", null);

        inventory.setItem(49, back);
        inventory.setItem(53, next);
        new BukkitRunnable() {
            public void run() {
                if (!(inventory.getHolder() instanceof Menu)) {
                    cancel();
                    return;
                }
                ArrayList<String> gappleLore = new ArrayList<>();
                gappleLore.add("");
                gappleLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("golden_apple_cooldown.golden_apple.enabled"));
                gappleLore.add("");
                gappleLore.add(ChatColor.WHITE + "Would you like a Cooldown");
                gappleLore.add(ChatColor.WHITE + "Between eating Golden Apples?");
                gappleLore.add("");
                gappleLore.add(ChatColor.GRAY + "More options in the Config.yml");
                ItemStack gapple = makeItem(Material.PAPER, 1, "§6Golden Apple Cooldown", gappleLore);

                ArrayList<String> gappleELore = new ArrayList<>();
                gappleELore.add("");
                gappleELore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("golden_apple_cooldown.enchanted_golden_apple.enabled"));
                gappleELore.add("");
                gappleELore.add(ChatColor.WHITE + "Would you like a Cooldown");
                gappleELore.add(ChatColor.WHITE + "Between eating Enchanted Golden Apples?");
                gappleELore.add("");
                gappleELore.add(ChatColor.GRAY + "More options in the Config.yml");
                ItemStack gappleE = makeItem(Material.PAPER, 1, "§6Enchanted Golden Apple Cooldown", gappleELore);

                ArrayList<String> disItemsLore = new ArrayList<>();
                disItemsLore.add("");
                disItemsLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("disabled_items.enabled"));
                disItemsLore.add("");
                disItemsLore.add(ChatColor.WHITE + "Would you like to Disable specific");
                disItemsLore.add(ChatColor.WHITE + "Items from being Crafted?");
                disItemsLore.add("");
                disItemsLore.add(ChatColor.GRAY + "More options in the Config.yml");
                ItemStack disItems = makeItem(Material.PAPER, 1, "§6Disabled Items", disItemsLore);

                ArrayList<String> disRotateLore = new ArrayList<>();
                disRotateLore.add("");
                disRotateLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("disable_item_frame_rotation.enabled"));
                disRotateLore.add("");
                disRotateLore.add(ChatColor.WHITE + "Would you like to Prevent");
                disRotateLore.add(ChatColor.WHITE + "Items inside Item Frames from Rotating?");
                ItemStack disRotate = makeItem(Material.PAPER, 1, "§6Disable Item Frame Rotation", disRotateLore);

                ArrayList<String> offhLore = new ArrayList<>();
                offhLore.add("");
                offhLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("disable_offhand.enabled"));
                offhLore.add("");
                offhLore.add(ChatColor.WHITE + "Would you like to Prevent");
                offhLore.add(ChatColor.WHITE + "Players from using the Offhand?");
                ItemStack offh = makeItem(Material.PAPER, 1, "§6Disable Offhand", offhLore);

                ArrayList<String> projLore = new ArrayList<>();
                projLore.add("");
                projLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("fixes.projectile_fixer"));
                projLore.add("");
                projLore.add(ChatColor.WHITE + "Fixes a Bug with Projectiles");
                projLore.add(ChatColor.WHITE + "Not always going straight");
                ItemStack proj = makeItem(Material.PAPER, 1, "§6Projectile Fixer", projLore);

                ArrayList<String> healthLore = new ArrayList<>();
                healthLore.add("");
                healthLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("custom.player_health.enabled"));
                healthLore.add("");
                healthLore.add(ChatColor.WHITE + "Would you like your Players");
                healthLore.add(ChatColor.WHITE + "To have Customized Max Health?");
                healthLore.add("");
                healthLore.add(ChatColor.GRAY + "More options in the Config.yml");
                ItemStack health = makeItem(Material.PAPER, 1, "§6Custom Player Health", healthLore);

                ArrayList<String> epearlLore = new ArrayList<>();
                epearlLore.add("");
                epearlLore.add(ChatColor.GRAY + "Currently set to: " + ChatColor.GREEN + isEnabled("enderpearl_cooldown.enabled"));
                epearlLore.add("");
                epearlLore.add(ChatColor.WHITE + "Would you like a Cooldown");
                epearlLore.add(ChatColor.WHITE + "Between using Ender Pearls?");
                epearlLore.add("");
                epearlLore.add(ChatColor.GRAY + "More options in the Config.yml");
                ItemStack epearl = makeItem(Material.PAPER, 1, "§6Ender Pearl Cooldown", epearlLore);

                inventory.setItem(10, gapple);
                inventory.setItem(12, gappleE);
                inventory.setItem(14, disItems);
                inventory.setItem(16, health);
                inventory.setItem(28, disRotate);
                inventory.setItem(30, offh);
                inventory.setItem(32, proj);
                inventory.setItem(34, epearl);
            }
        }.runTaskTimer(plugin, 1, 5);
    }
}
