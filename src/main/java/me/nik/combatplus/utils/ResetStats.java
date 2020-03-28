package me.nik.combatplus.utils;

import me.nik.combatplus.api.Manager;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ResetStats extends Manager {
    private double defaultAttSpd = configDouble("advanced.settings.new_pvp.attack_speed");

    public void Reset(Player player) {
        AttributeInstance baseAttSpd = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (!isAsync()) {
            baseAttSpd.setBaseValue(defaultAttSpd);
            player.saveData();
            if (debug(player)) {
                player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "Reset Stats" + ChatColor.GREEN + " Status" + " Done" + ChatColor.YELLOW + " Async:" + " False"));
            }
        } else {
            final Player pAnonymous = player;
            final AttributeInstance pBaseAttSpd = pAnonymous.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
            new BukkitRunnable() {
                @Override
                public void run() {
                    pBaseAttSpd.setBaseValue(defaultAttSpd);
                    pAnonymous.saveData();
                    if (debug(pAnonymous)) {
                        pAnonymous.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "Reset Stats" + ChatColor.GREEN + " TaskID: " + getTaskId() + ChatColor.YELLOW + " Async:" + " True"));
                    } else cancel();
                }
            }.runTaskAsynchronously(plugin);
        }
    }
}