package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
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
     * Sends a debug message to a player, Useful for finding or sharing information and data
     *
     * @param player  The player to send the message to
     * @param message The debug message
     */
    public static void debug(Player player, String message) {
        if (Config.Setting.DEVELOPER_MODE.getBoolean() && player.hasPermission("cp.debug")) {
            player.sendMessage(MsgType.PREFIX.getMessage() + format(message));
        }
    }
}