package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class ResetStats {

    private final double defaultAttSpd = Config.get().getDouble("advanced.settings.new_pvp.attack_speed");
    private final double defaultMaxHealth = Config.get().getDouble("advanced.settings.base_player_health");

    /**
     * Reset a player's Attack Speed
     *
     * @param player The player
     */
    public void resetAttackSpeed(Player player) {
        AttributeInstance baseAttSpd = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        baseAttSpd.setBaseValue(defaultAttSpd);
        player.saveData();
        Messenger.debug(player, "&6Set Attack Speed to: &a" + defaultAttSpd);
    }

    /**
     * Reset a player's Max Health
     *
     * @param player The player
     */
    public void resetMaxHealth(Player player) {
        AttributeInstance baseMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        baseMaxHealth.setBaseValue(defaultMaxHealth);
        player.saveData();
        Messenger.debug(player, "&6Set Maximum Health to: &a" + defaultMaxHealth);
    }
}