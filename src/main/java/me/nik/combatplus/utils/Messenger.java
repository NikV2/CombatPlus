package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.managers.MsgType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messenger {

    private static Lang lang;
    private static Config config;

    public static void initialize(Lang lang, Config config) {
        Messenger.lang = lang;
        Messenger.config = config;
    }

    /**
     * @param msg The message to format
     * @return The formatted message
     */
    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /**
     * Returns a message from the given type
     *
     * @param type The message type
     * @return The type's Message
     */
    public static String message(MsgType type) {
        switch (type) {
            case PREFIX:
                return format(lang.get().getString("prefix"));
            case NO_PERMISSION:
                return format(lang.get().getString("prefix") + lang.get().getString("no_perm"));
            case RELOADING:
                return format(lang.get().getString("prefix") + lang.get().getString("reloading"));
            case RELOADED:
                return format(lang.get().getString("prefix") + lang.get().getString("reloaded"));
            case UPDATE_REMINDER:
                return format(lang.get().getString("prefix") + lang.get().getString("update_reminder"));
            case GOLDEN_APPLE_COOLDOWN:
                return format(lang.get().getString("prefix") + lang.get().getString("golden_apple_cooldown"));
            case GOLDEN_APPLE_COOLDOWN_ACTIONBAR:
                return format(lang.get().getString("golden_apple_cooldown_actionbar"));
            case ENCHANTED_GOLDEN_APPLE_COOLDOWN:
                return format(lang.get().getString("prefix") + lang.get().getString("enchanted_golden_apple_cooldown"));
            case ENCHANTED_GOLDEN_APPLE_COOLDOWN_ACTIONBAR:
                return format(lang.get().getString("enchanted_golden_apple_cooldown_actionbar"));
            case ENDERPEARL_COOLDOWN:
                return format(lang.get().getString("prefix") + lang.get().getString("enderpearl_cooldown"));
            case ENDERPEARL_COOLDOWN_ACTIONBAR:
                return format(lang.get().getString("enderpearl_cooldown_actionbar"));
            case CANNOT_CRAFT_THIS:
                return format(lang.get().getString("prefix") + lang.get().getString("cannot_craft_this"));
            case GUI_MAIN:
                return format(lang.get().getString("gui_main"));
            case GUI_PLUGIN:
                return format(lang.get().getString("gui_plugin"));
            case GUI_COMBAT:
                return format(lang.get().getString("gui_combat"));
            case GUI_GENERAL:
                return format(lang.get().getString("gui_general"));
            case CONSOLE_UPDATE_NOT_FOUND:
                return format(lang.get().getString("prefix") + lang.get().getString("console_update_not_found"));
            case CONSOLE_UPDATE_DISABLED:
                return format(lang.get().getString("prefix") + lang.get().getString("console_update_disabled"));
            case CONSOLE_COMMANDS:
                return format(lang.get().getString("prefix") + lang.get().getString("console_commands"));
            case CONSOLE_INITIALIZE:
                return format(lang.get().getString("prefix") + lang.get().getString("console_initialize"));
            case CONSOLE_UNSUPPORTED_VERSION:
                return format(lang.get().getString("prefix") + lang.get().getString("console_unsupported_version"));
            case CONSOLE_UNSUPPORTED_SWEEP_ATTACK:
                return format(lang.get().getString("prefix") + lang.get().getString("console_unsupported_sweep_attack"));
            case CONSOLE_DISABLED:
                return format(lang.get().getString("prefix") + lang.get().getString("console_disabled"));
            default:
                return null;
        }
    }

    /**
     * Sends a debug message to a player, Useful for finding or sharing information and data
     *
     * @param player  The player to send the message to
     * @param message The debug message
     */
    public static void debug(Player player, String message) {
        if (config.get().getBoolean("settings.developer_mode") && player.hasPermission("cp.debug")) {
            player.sendMessage(Messenger.message(MsgType.PREFIX) + format(message));
        }
    }
}