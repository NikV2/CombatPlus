package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class ResetStats {

    private final double defaultAttSpd;
    private final double defaultMaxHealth;

    public ResetStats(CombatPlus plugin) {
        this.defaultAttSpd = plugin.getConfig().getDouble("advanced.settings.new_pvp.attack_speed");
        this.defaultMaxHealth = plugin.getConfig().getDouble("advanced.settings.base_player_health");
    }

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