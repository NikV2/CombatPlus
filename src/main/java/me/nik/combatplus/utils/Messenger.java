package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messenger {

    /**
     * @param msg The message to format
     * @return The formatted message
     */
    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /**
     * @param msg Path to the message from Lang.yml
     * @return The Prefix and a Message formatted
     */
    public static String prefix(String msg) {
        return format(Lang.get().getString("prefix")) + format(msg);
    }

    /**
     * @param msg Path to the message from Lang.yml
     * @return The Prefix and Message from Lang.yml formatted
     */
    public static String message(String msg) {
        return format(Lang.get().getString("prefix") + format(Lang.get().getString(msg)));
    }

    /**
     * Sends a debug message to a player, Useful for finding or sharing information and data
     *
     * @param player  The player to send the message to
     * @param message The debug message
     */
    public static void debug(Player player, String message) {
        if (Config.get().getBoolean("settings.developer_mode") && player.hasPermission("cp.debug")) {
            player.sendMessage(Messenger.prefix(message));
        }
    }
}
