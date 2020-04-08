package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.Manager;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ResetStats extends Manager {
    private final double defaultAttSpd = configDouble("advanced.settings.new_pvp.attack_speed");
    private final double defaultMaxHealth = configDouble("advanced.settings.base_player_health");

    public ResetStats(CombatPlus plugin) {
        super(plugin);
    }

    public void resetAttackSpeed(Player player) {
        AttributeInstance baseAttSpd = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (!isAsync()) {
            baseAttSpd.setBaseValue(defaultAttSpd);
            player.saveData();
            if (debug(player)) {
                player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "Reset Attack Speed" + ChatColor.GREEN + " Status" + " Done" + ChatColor.YELLOW + " Async:" + " False"));
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
                        pAnonymous.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "Reset Attack Speed" + ChatColor.GREEN + " TaskID: " + getTaskId() + ChatColor.YELLOW + " Async:" + " True"));
                    } else cancel();
                }
            }.runTaskAsynchronously(plugin);
        }
    }

    public void resetMaxHealth(Player player) {
        AttributeInstance baseMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (!isAsync()) {
            baseMaxHealth.setBaseValue(defaultMaxHealth);
            player.saveData();
            if (debug(player)) {
                player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "Reset Max Health" + ChatColor.GREEN + " Status" + " Done" + ChatColor.YELLOW + " Async:" + " False"));
            }
        } else {
            final Player pAnonymous = player;
            final AttributeInstance pBaseMaxHealth = pAnonymous.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            new BukkitRunnable() {
                @Override
                public void run() {
                    pBaseMaxHealth.setBaseValue(defaultMaxHealth);
                    pAnonymous.saveData();
                    if (debug(pAnonymous)) {
                        pAnonymous.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "Reset Max Health" + ChatColor.GREEN + " TaskID: " + getTaskId() + ChatColor.YELLOW + " Async:" + " True"));
                    } else cancel();
                }
            }.runTaskAsynchronously(plugin);
        }
    }
}