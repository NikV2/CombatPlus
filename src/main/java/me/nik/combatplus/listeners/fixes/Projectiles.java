package me.nik.combatplus.listeners.fixes;

import me.nik.combatplus.api.Manager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Projectiles extends Manager {

    // This Module Makes Projectiles Go Straight (To where they're supposed to land)
    // Removes Randomness

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onProjectileShoot(ProjectileLaunchEvent e) {
        final Projectile projectile = e.getEntity();
        final ProjectileSource holder = projectile.getShooter();
        if (holder instanceof Player) {
            final Player p = (Player) holder;
            if (!isAsync()) {
                final Vector pDirection = p.getLocation().getDirection().normalize();
                final Vector eVelocity = pDirection.multiply(projectile.getVelocity().length());
                projectile.setVelocity(eVelocity);
                debug(p, "&3Projectile Fixer &f&l>> &6Fixed Velocity: &atrue &6Direction: &a" + pDirection);
            } else {
                final Vector pDirection = p.getLocation().getDirection().normalize();
                final Vector eVelocity = pDirection.multiply(projectile.getVelocity().length());
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        projectile.setVelocity(eVelocity);
                        debug(p, "&3Projectile Fixer &f&l>> &6Fixed Velocity: &atrue &6Direction: &a" + pDirection + " &bAsync: true");
                    }
                }.runTaskAsynchronously(plugin);
            }
        }
    }
}