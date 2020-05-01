package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.Manager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SetAttackSpeed extends Manager {
    private final double attackSpeed = configDouble("advanced.settings.old_pvp.attack_speed");

    public SetAttackSpeed(CombatPlus plugin) {
        super(plugin);
    }

    public void setAttackSpd(Player player) {
        if (combatDisabledWorlds(player)) {
            new ResetStats(plugin).resetAttackSpeed(player);
        } else {
            if (!isAsync()) {
                player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed);
                player.saveData();
                debug(player, "&6Set Attack Speed to: &a" + attackSpeed);
            } else {
                final Player pAnonymous = player;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        pAnonymous.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed);
                        pAnonymous.saveData();
                        debug(pAnonymous, "&6Set Attack Speed to: &a" + attackSpeed + " &bAsync: true");
                    }
                    }.runTaskAsynchronously(plugin);
                }
            }
        }
    }
