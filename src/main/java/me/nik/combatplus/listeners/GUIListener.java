package me.nik.combatplus.listeners;

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
                new GUIManager().openPluginGUI(p);
                break;
            case "§eCombat Settings":
                p.closeInventory();
                new GUIManager().openCombatGUI(p);
                break;
            case "§aGeneral Settings":
                p.closeInventory();
                new GUIManager().openGeneralGUI(p);
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
                booleanSet("settings.check_for_updates", !configBoolean("settings.check_for_updates"));
                saveAndReload();
                break;
            case "§6Run Asynchronously":
                booleanSet("settings.async", !configBoolean("settings.async"));
                saveAndReload();
                break;
            case "§6Developer Mode":
                booleanSet("settings.developer_mode", !configBoolean("settings.developer_mode"));
                saveAndReload();
                break;
            case "§cBack":
                p.closeInventory();
                new GUIManager().openMainGUI(p);
                break;
            // Combat Settings GUI
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
            // General GUI Settings
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
        }
        e.setCancelled(true);
        if (e.getClick() == ClickType.SHIFT_RIGHT) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
    }
}