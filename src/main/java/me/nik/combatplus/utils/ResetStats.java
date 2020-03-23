package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class ResetStats {
    private double defaultAttSpd = Config.get().getDouble("advanced.settings.new_pvp.attack_speed");
    public void Reset(Player player){
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(defaultAttSpd);
        player.saveData();
        player.loadData();
    }
}
