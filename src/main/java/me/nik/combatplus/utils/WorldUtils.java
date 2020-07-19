package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.entity.Player;

public class WorldUtils {

    /**
     * @param player The player
     * @return true if the player is inside a Disabled World
     */
    public boolean combatDisabledWorlds(Player player) {
        for (String world : Config.Setting.COMBAT_DISABLED_WORLDS.getStringList()) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    /**
     * @param player The player
     * @return true if the player is inside a Disabled World
     */
    public boolean gappleDisabledWorlds(Player player) {
        for (String world : Config.Setting.COOLDOWN_APPLE_WORLDS.getStringList()) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    /**
     * @param player The player
     * @return true if the player is inside a Disabled World
     */
    public boolean enderpearlDisabledWorlds(Player player) {
        for (String world : Config.Setting.ENDERPEARL_WORLDS.getStringList()) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    /**
     * @param player The player
     * @return true if the player is inside a Disabled World
     */
    public boolean offhandDisabledWorlds(Player player) {
        for (String world : Config.Setting.DISABLE_OFFHAND_WORLDS.getStringList()) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }
}