package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class SetAttackSpeed {
    private final WorldUtils worldUtils;
    private final ResetStats resetStats;

    public SetAttackSpeed() {
        this.worldUtils = new WorldUtils();
        this.resetStats = new ResetStats();
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
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Config.Setting.ADV_OLD_ATTACK_SPEED.getDouble());
        player.saveData();
        Messenger.debug(player, "&6Set Attack Speed to: &a" + Config.Setting.ADV_OLD_ATTACK_SPEED.getDouble());
    }
}