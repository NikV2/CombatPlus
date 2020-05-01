package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.Manager;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SetCustomHealth extends Manager {
    private final double maxHealth = configDouble("custom.player_health.max_health");

    public SetCustomHealth(CombatPlus plugin) {
        super(plugin);
    }

    public void setHealth(Player player) {
        if (customStatsWorlds(player)) {
            new ResetStats(plugin).resetMaxHealth(player);
        }
        if (!isAsync()) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
            player.saveData();
            debug(player, "&6Set Maximum Health to: &a" + maxHealth);
        } else {
            final Player pAnonymous = player;
            new BukkitRunnable() {
                @Override
                public void run() {
                    pAnonymous.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
                    pAnonymous.saveData();
                    debug(pAnonymous, "&6Set Maximum Health to: &a" + maxHealth + " &bAsync: true");
                }
            }.runTaskAsynchronously(plugin);
        }
    }
}
