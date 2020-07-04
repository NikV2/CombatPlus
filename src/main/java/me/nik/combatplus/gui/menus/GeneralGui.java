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
                booleanSet("golden_apple_cooldown.golden_apple.enabled", !configBoolean("golden_apple_cooldown.golden_apple.enabled"));
                saveAndReload();
                break;
            case 12:
                booleanSet("golden_apple_cooldown.enchanted_golden_apple.enabled", !configBoolean("golden_apple_cooldown.enchanted_golden_apple.enabled"));
                saveAndReload();
                break;
            case 14:
                booleanSet("disabled_items.enabled", !configBoolean("disabled_items.enabled"));
                saveAndReload();
                break;
            case 28:
                booleanSet("disable_item_frame_rotation.enabled", !configBoolean("disable_item_frame_rotation.enabled"));
                saveAndReload();
                break;
            case 30:
                booleanSet("disable_offhand.enabled", !configBoolean("disable_offhand.enabled"));
                saveAndReload();
                break;
            case 32:
                booleanSet("fixes.projectile_fixer", !configBoolean("fixes.projectile_fixer"));
                saveAndReload();
                break;
            case 16:
                booleanSet("custom.player_health.enabled", !configBoolean("custom.player_health.enabled"));
                saveAndReload();
                break;
            case 34:
                booleanSet("enderpearl_cooldown.enabled", !configBoolean("enderpearl_cooldown.enabled"));
                saveAndReload();
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
        new BukkitRunnable() {
            public void run() {
                if (!(inventory.getHolder() instanceof Menu)) {
                    cancel();
                    return;
                }
                ArrayList<String> gappleLore = new ArrayList<>();
                gappleLore.add("");
                gappleLore.add("&7Currently set to: &a" + isEnabled("golden_apple_cooldown.golden_apple.enabled"));
                gappleLore.add("");
                gappleLore.add("&fWould you like a Cooldown");
                gappleLore.add("&fBetween eating Golden Apples?");
                gappleLore.add("");
                gappleLore.add("&7More options in the Config.yml");
                ItemStack gapple = makeItem(Material.PAPER, 1, "&6Golden Apple Cooldown", gappleLore);

                ArrayList<String> gappleELore = new ArrayList<>();
                gappleELore.add("");
                gappleELore.add("&7Currently set to: &a" + isEnabled("golden_apple_cooldown.enchanted_golden_apple.enabled"));
                gappleELore.add("");
                gappleELore.add("&fWould you like a Cooldown");
                gappleELore.add("&fBetween eating Enchanted Golden Apples?");
                gappleELore.add("");
                gappleELore.add("&7More options in the Config.yml");
                ItemStack gappleE = makeItem(Material.PAPER, 1, "&6Enchanted Golden Apple Cooldown", gappleELore);

                ArrayList<String> disItemsLore = new ArrayList<>();
                disItemsLore.add("");
                disItemsLore.add("&7Currently set to: &a" + isEnabled("disabled_items.enabled"));
                disItemsLore.add("");
                disItemsLore.add("&fWould you like to Disable specific");
                disItemsLore.add("&fItems from being Crafted?");
                disItemsLore.add("");
                disItemsLore.add("&7More options in the Config.yml");
                ItemStack disItems = makeItem(Material.PAPER, 1, "&6Disabled Items", disItemsLore);

                ArrayList<String> disRotateLore = new ArrayList<>();
                disRotateLore.add("");
                disRotateLore.add("&7Currently set to: &a" + isEnabled("disable_item_frame_rotation.enabled"));
                disRotateLore.add("");
                disRotateLore.add("&fWould you like to Prevent");
                disRotateLore.add("&fItems inside Item Frames from Rotating?");
                ItemStack disRotate = makeItem(Material.PAPER, 1, "&6Disable Item Frame Rotation", disRotateLore);

                ArrayList<String> offhLore = new ArrayList<>();
                offhLore.add("");
                offhLore.add("&7Currently set to: &a" + isEnabled("disable_offhand.enabled"));
                offhLore.add("");
                offhLore.add("&fWould you like to Prevent");
                offhLore.add("&fPlayers from using the Offhand?");
                ItemStack offh = makeItem(Material.PAPER, 1, "&6Disable Offhand", offhLore);

                ArrayList<String> projLore = new ArrayList<>();
                projLore.add("");
                projLore.add("&7Currently set to: &a" + isEnabled("fixes.projectile_fixer"));
                projLore.add("");
                projLore.add("&fFixes a Bug with Projectiles");
                projLore.add("&fNot always going straight");
                ItemStack proj = makeItem(Material.PAPER, 1, "&6Projectile Fixer", projLore);

                ArrayList<String> healthLore = new ArrayList<>();
                healthLore.add("");
                healthLore.add("&7Currently set to: &a" + isEnabled("custom.player_health.enabled"));
                healthLore.add("");
                healthLore.add("&fWould you like your Players");
                healthLore.add("&fTo have Customized Max Health?");
                healthLore.add("");
                healthLore.add("&7More options in the Config.yml");
                ItemStack health = makeItem(Material.PAPER, 1, "&6Custom Player Health", healthLore);

                ArrayList<String> epearlLore = new ArrayList<>();
                epearlLore.add("");
                epearlLore.add("&7Currently set to: &a" + isEnabled("enderpearl_cooldown.enabled"));
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
                inventory.setItem(28, disRotate);
                inventory.setItem(30, offh);
                inventory.setItem(32, proj);
                inventory.setItem(34, epearl);
            }
        }.runTaskTimer(plugin, 1, 5);
    }
}
