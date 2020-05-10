package me.nik.combatplus.utils;

import org.bukkit.Bukkit;

public class MiscUtils {

    public static boolean isPlaceholderApiEnabled() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
}
