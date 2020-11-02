package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public final class StatUtils {

    private StatUtils() {
    }

    /**
     * Reset a player's Attack Speed
     *
     * @param player The player
     */
    public static void resetAttackSpeed(Player player) {
        AttributeInstance baseAttSpd = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        baseAttSpd.setBaseValue(Config.Setting.ADV_NEW_ATTACK_SPEED.getDouble());
        player.saveData();
        Messenger.debug(player, "&6Set Attack Speed to: &a" + Config.Setting.ADV_NEW_ATTACK_SPEED.getDouble());
    }

    /**
     * Reset a player's Max Health
     *
     * @param player The player
     */
    public static void resetMaxHealth(Player player) {
        AttributeInstance baseMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        baseMaxHealth.setBaseValue(Config.Setting.ADV_BASE_HEALTH.getDouble());
        player.saveData();
        Messenger.debug(player, "&6Set Maximum Health to: &a" + Config.Setting.ADV_BASE_HEALTH.getDouble());
    }

    /**
     * Set the Player's Max Health
     *
     * @param player The player
     */
    public static void setMaxHealth(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Config.Setting.CUSTOM_PLAYER_HEALTH_HEALTH.getDouble());
        player.saveData();
        Messenger.debug(player, "&6Set Maximum Health to: &a" + Config.Setting.CUSTOM_PLAYER_HEALTH_HEALTH.getDouble());
    }

    /**
     * Set the Player's Attack Speed
     *
     * @param player The player
     */
    public static void setAttackSpeed(Player player) {
        if (WorldUtils.combatDisabledWorlds(player)) {
            resetAttackSpeed(player);
            return;
        }
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Config.Setting.ADV_OLD_ATTACK_SPEED.getDouble());
        player.saveData();
        Messenger.debug(player, "&6Set Attack Speed to: &a" + Config.Setting.ADV_OLD_ATTACK_SPEED.getDouble());
    }
}