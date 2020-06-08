package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.entity.Player;

import java.util.List;

public class WorldUtils {

    private final List<String> combatWorlds = Config.get().getStringList("combat.settings.disabled_worlds");
    private final List<String> itemFrameWorlds = Config.get().getStringList("disable_item_frame_rotation.disabled_worlds");
    private final List<String> gappleWorlds = Config.get().getStringList("golden_apple_cooldown.disabled_worlds");
    private final List<String> epearlWorlds = Config.get().getStringList("enderpearl_cooldown.disabled_worlds");
    private final List<String> offhandWorlds = Config.get().getStringList("disable_offhand.disabled_worlds");

    /**
     * @param player The player
     * @return true if the player is inside a Disabled World
     */
    public boolean combatDisabledWorlds(Player player) {
        for (String world : combatWorlds) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    /**
     * @param player The player
     * @return true if the player is inside a Disabled World
     */
    public boolean itemFrameRotationDisabledWorlds(Player player) {
        for (String world : itemFrameWorlds) {
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
        for (String world : gappleWorlds) {
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
        for (String world : epearlWorlds) {
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
        for (String world : offhandWorlds) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }
}
