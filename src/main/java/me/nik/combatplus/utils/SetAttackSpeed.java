package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class SetAttackSpeed {
    private double attackSpeed = Config.get().getDouble("advanced.settings.old_pvp.attack_speed");
    public void setAttackSpd(Player player){
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed);
        player.saveData();
        player.loadData();
    }
}
