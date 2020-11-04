package me.nik.combatplus.utils;

import org.bukkit.ChatColor;

public final class Messenger {

    private Messenger() {
    }

    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}