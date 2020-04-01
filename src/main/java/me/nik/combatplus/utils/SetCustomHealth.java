package me.nik.combatplus.utils;

import me.nik.combatplus.api.Manager;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SetCustomHealth extends Manager {
    private double maxHealth = configDouble("custom.player_health.max_health");

    public void setHealth(Player player) {
        if (!isAsync()) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
            player.saveData();
            if (debug(player)) {
                player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "SetCustomHealth" + ChatColor.GREEN + " Status" + " Done" + ChatColor.YELLOW + " Async:" + " False"));
            }
        } else {
            final Player pAnonymous = player;
            new BukkitRunnable() {
                @Override
                public void run() {
                    pAnonymous.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
                    pAnonymous.saveData();
                    if (debug(pAnonymous)) {
                        pAnonymous.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "SetCustomHealth" + ChatColor.GREEN + " TaskID: " + getTaskId() + ChatColor.YELLOW + " Async:" + " True"));
                    } else cancel();
                }
            }.runTaskAsynchronously(plugin);
        }
    }
}
