package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SetAttackSpeed {
    private double attackSpeed = Config.get().getDouble("advanced.settings.old_pvp.attack_speed");
    CombatPlus plugin = CombatPlus.getPlugin(CombatPlus.class);

    public void setAttackSpd(Player player) {
        for (String world : Config.get().getStringList("combat.settings.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world)) {
                new ResetStats().Reset(player);
                return;
            } else {
                if (!Config.get().getBoolean("settings.async")) {
                    player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed);
                    player.saveData();
                    if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
                        if (player.hasPermission("cp.admin")) {
                            player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "SetAttackSpeed" + ChatColor.GREEN + " Status" + " Done" + ChatColor.YELLOW + " Async:" + " False"));
                            return;
                        }
                    } else return;
                } else {
                    final Player pAnonymous = player;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            pAnonymous.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed);
                            pAnonymous.saveData();
                            if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
                                if (pAnonymous.hasPermission("cp.admin")) {
                                    pAnonymous.sendMessage(Messenger.prefix(ChatColor.AQUA + "Task: " + "SetAttackSpeed" + ChatColor.GREEN + " TaskID: " + getTaskId() + ChatColor.YELLOW + " Async:" + " True"));
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
    }
}
