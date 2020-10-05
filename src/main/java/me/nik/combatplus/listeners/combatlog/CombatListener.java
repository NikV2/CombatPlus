package me.nik.combatplus.listeners.combatlog;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.CombatLog;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class CombatListener extends CombatLog {

    private final boolean tagForMobs = Config.Setting.COMBATLOG_MOBS.getBoolean();
    private final boolean tagForProjectiles = Config.Setting.COMBATLOG_PROJECTILES.getBoolean();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCombat(EntityDamageByEntityEvent e) {

        final Entity target = e.getEntity();

        if (!(e.getEntity() instanceof LivingEntity)) return;

        if (e.getDamager() instanceof Projectile) {

            if (!tagForProjectiles) return;

            final ProjectileSource shooter = ((Projectile) e.getDamager()).getShooter();

            if (!(shooter instanceof Player)) return;

            final Player p = (Player) shooter;

            if (!tagForMobs && target instanceof Mob) return;

            tagPlayer(p);

            if (!(target instanceof Player)) return;
            tagPlayer((Player) target);
        } else if (e.getDamager() instanceof Player) {

            if (!tagForMobs && target instanceof Mob) return;

            final Player p = (Player) e.getDamager();

            tagPlayer(p);

            if (!(target instanceof Player)) return;
            tagPlayer((Player) target);
        } else if (e.getDamager() instanceof Mob) {
            if (!tagForMobs) return;
            if (!(target instanceof Player)) return;
            final Player p = (Player) e.getEntity();

            tagPlayer(p);
        }
    }
}