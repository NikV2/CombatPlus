package me.nik.combatplus.utils;

import org.bukkit.Bukkit;

public final class ServerUtils {

    private static final boolean legacy = Bukkit.getVersion().contains("1.8")
            || Bukkit.getVersion().contains("1.9")
            || Bukkit.getVersion().contains("1.10")
            || Bukkit.getVersion().contains("1.11")
            || Bukkit.getVersion().contains("1.12");

    private ServerUtils() {
    }

    public static boolean isLegacy() {
        return legacy;
    }
}