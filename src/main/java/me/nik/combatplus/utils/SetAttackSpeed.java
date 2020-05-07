package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class SetAttackSpeed {
    private final double attackSpeed = Config.get().getDouble("advanced.settings.old_pvp.attack_speed");

    public void setAttackSpd(Player player) {
        if (WorldUtils.combatDisabledWorlds(player)) {
            new ResetStats().resetAttackSpeed(player);
            return;
        }
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed);
        player.saveData();
        Messenger.debug(player, "&6Set Attack Speed to: &a" + attackSpeed);
    }
}
