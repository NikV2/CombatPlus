package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.entity.Player;

import java.util.List;

public class WorldUtils {

    private static final List<String> COMBATWORLDS = Config.get().getStringList("combat.settings.disabled_worlds");
    private static final List<String> ITEMFRAMEWORLDS = Config.get().getStringList("disable_item_frame_rotation.disabled_worlds");
    private static final List<String> GAPPLEWORLDS = Config.get().getStringList("golden_apple_cooldown.disabled_worlds");
    private static final List<String> EPEARLWORLDS = Config.get().getStringList("enderpearl_cooldown.disabled_worlds");
    private static final List<String> OFFHANDWORLDS = Config.get().getStringList("disable_offhand.disabled_worlds");

    public static boolean combatDisabledWorlds(Player player) {
        for (String world : COMBATWORLDS) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public static boolean itemFrameRotationDisabledWorlds(Player player) {
        for (String world : ITEMFRAMEWORLDS) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public static boolean gappleDisabledWorlds(Player player) {
        for (String world : GAPPLEWORLDS) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public static boolean enderpearlDisabledWorlds(Player player) {
        for (String world : EPEARLWORLDS) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public static boolean offhandDisabledWorlds(Player player) {
        for (String world : OFFHANDWORLDS) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }
}
