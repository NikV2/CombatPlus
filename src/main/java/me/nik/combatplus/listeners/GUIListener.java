package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.GUIManager;
import me.nik.combatplus.api.Manager;
import me.nik.combatplus.handlers.CombatPlusHolder;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener extends Manager {

    public GUIListener(CombatPlus plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        if (!(e.getInventory().getHolder() instanceof CombatPlusHolder)) return;
        if (null == clickedItem) return;
        if (clickedItem.getType().equals(Material.AIR)) return;
        switch (clickedItem.getItemMeta().getDisplayName()) {
            // Main GUI
            case "§6Plugin Settings":
                p.closeInventory();
                new GUIManager(plugin).openPluginGUI(p);
                break;
            case "§eCombat Settings":
                p.closeInventory();
                new GUIManager(plugin).openCombatGUI(p);
                break;
            case "§aGeneral Settings":
                p.closeInventory();
                new GUIManager(plugin).openGeneralGUI(p);
                break;
            case "§bLooking for Support?":
                p.closeInventory();
                p.sendMessage(ChatColor.BLUE + ">> " + ChatColor.WHITE + "https://discordapp.com/invite/m7j2Y9H" + ChatColor.BLUE + " <<");
                break;
            case "§cReload and Exit":
                p.closeInventory();
                p.sendMessage(Messenger.message("reloading"));
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                plugin.getServer().getPluginManager().enablePlugin(plugin);
                p.sendMessage(Messenger.message("reloaded"));
                break;
            // Plugin Settings GUI
            case "§6Check for Updates":
                if (configBoolean("settings.check_for_updates")) {
                    booleanSet("settings.check_for_updates", false);
                } else {
                    booleanSet("settings.check_for_updates", true);
                }
                saveAndReload();
                break;
            case "§6Run Asynchronously":
                if (configBoolean("settings.async")) {
                    booleanSet("settings.async", false);
                } else {
                    booleanSet("settings.async", true);
                }
                saveAndReload();
                break;
            case "§6Developer Mode":
                if (configBoolean("settings.developer_mode")) {
                    booleanSet("settings.developer_mode", false);
                } else {
                    booleanSet("settings.developer_mode", true);
                }
                saveAndReload();
                break;
            case "§cBack":
                p.closeInventory();
                new GUIManager(plugin).openMainGUI(p);
                break;
            // Combat Settings GUI
            case "§6Old PvP":
                if (configBoolean("combat.settings.old_pvp")) {
                    booleanSet("combat.settings.old_pvp", false);
                } else {
                    booleanSet("combat.settings.old_pvp", true);
                }
                saveAndReload();
                break;
            case "§6Old Weapon Damage":
                if (configBoolean("combat.settings.old_weapon_damage")) {
                    booleanSet("combat.settings.old_weapon_damage", false);
                } else {
                    booleanSet("combat.settings.old_weapon_damage", true);
                }
                saveAndReload();
                break;
            case "§6Old Tool Damage":
                if (configBoolean("combat.settings.old_tool_damage")) {
                    booleanSet("combat.settings.old_tool_damage", false);
                } else {
                    booleanSet("combat.settings.old_tool_damage", true);
                }
                saveAndReload();
                break;
            case "§6Old Sharpness":
                if (configBoolean("combat.settings.old_sharpness")) {
                    booleanSet("combat.settings.old_sharpness", false);
                } else {
                    booleanSet("combat.settings.old_sharpness", true);
                }
                saveAndReload();
                break;
            case "§6Disable Sweep Attacks":
                if (configBoolean("combat.settings.disable_sweep_attacks")) {
                    booleanSet("combat.settings.disable_sweep_attacks", false);
                } else {
                    booleanSet("combat.settings.disable_sweep_attacks", true);
                }
                saveAndReload();
                break;
            case "§6Disable Arrow Boost":
                if (configBoolean("combat.settings.disable_arrow_boost")) {
                    booleanSet("combat.settings.disable_arrow_boost", false);
                } else {
                    booleanSet("combat.settings.disable_arrow_boost", true);
                }
                saveAndReload();
                break;
            case "§6Old Player Regen":
                if (configBoolean("combat.settings.old_player_regen")) {
                    booleanSet("combat.settings.old_player_regen", false);
                } else {
                    booleanSet("combat.settings.old_player_regen", true);
                }
                saveAndReload();
                break;
            // General GUI Settings
            case "§6Golden Apple Cooldown":
                if (configBoolean("golden_apple_cooldown.golden_apple.enabled")) {
                    booleanSet("golden_apple_cooldown.golden_apple.enabled", false);
                } else {
                    booleanSet("golden_apple_cooldown.golden_apple.enabled", true);
                }
                saveAndReload();
                break;
            case "§6Enchanted Golden Apple Cooldown":
                if (configBoolean("golden_apple_cooldown.enchanted_golden_apple.enabled")) {
                    booleanSet("golden_apple_cooldown.enchanted_golden_apple.enabled", false);
                } else {
                    booleanSet("golden_apple_cooldown.enchanted_golden_apple.enabled", true);
                }
                saveAndReload();
                break;
            case "§6Disabled Items":
                if (configBoolean("disabled_items.enabled")) {
                    booleanSet("disabled_items.enabled", false);
                } else {
                    booleanSet("disabled_items.enabled", true);
                }
                saveAndReload();
                break;
            case "§6Disable Item Frame Rotation":
                if (configBoolean("disable_item_frame_rotation.enabled")) {
                    booleanSet("disable_item_frame_rotation.enabled", false);
                } else {
                    booleanSet("disable_item_frame_rotation.enabled", true);
                }
                saveAndReload();
                break;
            case "§6Disable Offhand":
                if (configBoolean("disable_offhand.enabled")) {
                    booleanSet("disable_offhand.enabled", false);
                } else {
                    booleanSet("disable_offhand.enabled", true);
                }
                saveAndReload();
                break;
            case "§6Projectile Fixer":
                if (configBoolean("fixes.projectile_fixer")) {
                    booleanSet("fixes.projectile_fixer", false);
                } else {
                    booleanSet("fixes.projectile_fixer", true);
                }
                saveAndReload();
                break;
            case "§6Custom Player Health":
                if (configBoolean("custom.player_health.enabled")) {
                    booleanSet("custom.player_health.enabled", false);
                } else {
                    booleanSet("custom.player_health.enabled", true);
                }
                saveAndReload();
                break;
        }
        e.setCancelled(true);
        if (e.getClick() == ClickType.SHIFT_RIGHT) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
    }
}