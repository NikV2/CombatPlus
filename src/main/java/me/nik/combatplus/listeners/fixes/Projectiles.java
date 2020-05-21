package me.nik.combatplus.listeners.fixes;

import me.nik.combatplus.utils.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class Projectiles implements Listener {

    /*
    This Module Makes Projectiles Go Straight (To where they're supposed to land)
    Removes Randomness
     */

    @EventHandler(ignoreCancelled = true)
    public void onProjectileShoot(ProjectileLaunchEvent e) {
        final Projectile projectile = e.getEntity();
        final ProjectileSource holder = projectile.getShooter();
        if (holder instanceof Player) {
            final Player p = (Player) holder;
            final Vector pDirection = p.getLocation().getDirection().normalize();
            final Vector eVelocity = pDirection.multiply(projectile.getVelocity().length());
            projectile.setVelocity(eVelocity);
            Messenger.debug(p, "&3Projectile Fixer &f&l>> &6Fixed Velocity: &atrue &6Direction: &a" + pDirection);
        }
    }
}