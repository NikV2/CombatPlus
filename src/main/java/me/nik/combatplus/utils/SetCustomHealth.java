package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class SetCustomHealth {

    private final double maxHealth;

    public SetCustomHealth(CombatPlus plugin) {
        this.maxHealth = plugin.getConfig().getDouble("custom.player_health.max_health");
    }

    /**
     * Set the Player's Max Health
     *
     * @param player The player
     */
    public void setHealth(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        player.saveData();
        Messenger.debug(player, "&6Set Maximum Health to: &a" + maxHealth);
    }
}
