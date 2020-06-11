package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class SetAttackSpeed {
    private final double attackSpeed;
    private final WorldUtils worldUtils;
    private final ResetStats resetStats;

    public SetAttackSpeed(CombatPlus plugin) {
        this.attackSpeed = plugin.getConfig().getDouble("advanced.settings.old_pvp.attack_speed");
        this.worldUtils = new WorldUtils(plugin);
        this.resetStats = new ResetStats(plugin);
    }

    /**
     * Set the Player's Attack Speed
     *
     * @param player The player
     */
    public void setAttackSpd(Player player) {
        if (worldUtils.combatDisabledWorlds(player)) {
            resetStats.resetAttackSpeed(player);
            return;
        }
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed);
        player.saveData();
        Messenger.debug(player, "&6Set Attack Speed to: &a" + attackSpeed);
    }
}
