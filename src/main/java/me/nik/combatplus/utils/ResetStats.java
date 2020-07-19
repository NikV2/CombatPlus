package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class ResetStats {

    /**
     * Reset a player's Attack Speed
     *
     * @param player The player
     */
    public void resetAttackSpeed(Player player) {
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
    public void resetMaxHealth(Player player) {
        AttributeInstance baseMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        baseMaxHealth.setBaseValue(Config.Setting.ADV_BASE_HEALTH.getDouble());
        player.saveData();
        Messenger.debug(player, "&6Set Maximum Health to: &a" + Config.Setting.ADV_BASE_HEALTH.getDouble());
    }
}