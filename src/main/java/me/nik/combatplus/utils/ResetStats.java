package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ResetStats {
    private double defaultAttSpd = Config.get().getDouble("advanced.settings.new_pvp.attack_speed");
    CombatPlus plugin = CombatPlus.getPlugin(CombatPlus.class);

    public void Reset(Player player) {
        if (!Config.get().getBoolean("settings.async")) {
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(defaultAttSpd);
            player.saveData();
            if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
                if (player.hasPermission("cp.admin")) {
                    player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "Reset Stats" + ChatColor.GREEN + " Status" + " Done" + ChatColor.YELLOW + " Async:" + " False"));
                    return;
                }
            } else return;
        } else {
            final Player pAnonymous = player;
            new BukkitRunnable() {
                @Override
                public void run() {
                    pAnonymous.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(defaultAttSpd);
                    pAnonymous.saveData();
                    if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
                        if (pAnonymous.hasPermission("cp.admin")) {
                            pAnonymous.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "Reset Stats" + ChatColor.GREEN + " TaskID: " + getTaskId() + ChatColor.YELLOW + " Async:" + " True"));
                            cancel();
                            return;
                        }
                    } else {
                        cancel();
                        return;
                    }
                }
            }.runTaskAsynchronously(plugin);
        }
    }
}