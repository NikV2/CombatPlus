package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.entity.Player;

public final class WorldUtils {

    private WorldUtils() {
    }

    public static boolean combatDisabledWorlds(Player player) {
        for (String world : Config.Setting.COMBAT_DISABLED_WORLDS.getStringList()) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public static boolean goldenAppleDisabledWorlds(Player player) {
        for (String world : Config.Setting.GOLDEN_APPLE_DISABLED_WORLDS.getStringList()) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public static boolean enchantedAppleDisabledWorlds(Player player) {
        for (String world : Config.Setting.ENCHANTED_APPLE_DISABLED_WORLDS.getStringList()) {
            if (player.getWorld().getName().equalsIgnoreCase(world)) return true;
        }

        return false;
    }

    public static boolean enderpearlDisabledWorlds(Player player) {
        for (String world : Config.Setting.ENDERPEARL_DISABLED_WORLDS.getStringList()) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public static boolean offhandDisabledWorlds(Player player) {
        for (String world : Config.Setting.DISABLE_OFFHAND_WORLDS.getStringList()) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public static boolean healthBarDisabledWorlds(Player player) {
        for (String world : Config.Setting.HEALTHBAR_WORLDS.getStringList()) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }
}