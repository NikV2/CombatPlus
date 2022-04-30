package me.nik.combatplus.utils;

import org.bukkit.Bukkit;

public final class ServerUtils {

    private static final String VERSION = Bukkit.getVersion();

    private static final boolean LEGACY = areVersions(
            "1.8",
            "1.9",
            "1.10",
            "1.11",
            "1.12");

    private static final boolean NETHER_UPDATE = !areVersions(
            "1.8",
            "1.9",
            "1.10",
            "1.11",
            "1.12",
            "1.13",
            "1.14",
            "1.15");

    public static boolean isVersion(final String version) {
        return VERSION.contains(version);
    }

    public static boolean areVersions(final String... versions) {

        for (String version : versions) {

            if (VERSION.contains(version)) return true;
        }

        return false;
    }

    public static boolean isLegacy() {
        return LEGACY;
    }

    public static boolean isNetherUpdate() {
        return NETHER_UPDATE;
    }
}