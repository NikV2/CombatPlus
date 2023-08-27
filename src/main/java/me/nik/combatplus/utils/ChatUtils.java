package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.utils.custom.CombatPlusException;
import org.bukkit.ChatColor;

public final class ChatUtils {

    private ChatUtils() {
        throw new CombatPlusException("This is a static class dummy!");
    }

    /**
     * @param msg The message to format
     * @return The formatted message
     */
    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void log(final String... messages) {
        for (String msg : messages) {
            CombatPlus.getInstance().getLogger().info(msg);
        }
    }
}