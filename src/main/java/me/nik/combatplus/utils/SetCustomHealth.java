package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class SetCustomHealth {

    /**
     * Set the Player's Max Health
     *
     * @param player The player
     */
    public void setHealth(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Config.Setting.CUSTOM_PLAYER_HEALTH_HEALTH.getDouble());
        player.saveData();
        Messenger.debug(player, "&6Set Maximum Health to: &a" + Config.Setting.CUSTOM_PLAYER_HEALTH_HEALTH.getDouble());
    }
}