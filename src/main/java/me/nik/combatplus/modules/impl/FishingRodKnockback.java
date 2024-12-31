package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.MathUtils;
import me.nik.combatplus.utils.MiscUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

public class FishingRodKnockback extends Module {


    public FishingRodKnockback() {
        super(Config.Setting.FISHING_ROD_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRodLand(ProjectileHitEvent e) {
        if (e.getEntityType() != MiscUtils.FISHING_HOOK || !(e.getHitEntity() instanceof LivingEntity)) return;

        Entity target = e.getHitEntity();

        //Return on citizens NPCs
        if (target.hasMetadata("NPC")) return;

        FishHook hook = (FishHook) e.getEntity();

        Player holder = (Player) hook.getShooter();

        if (target instanceof Player && (target.equals(holder) || target.isInvulnerable())) return;

        LivingEntity livingEntity = (LivingEntity) target;

        if (livingEntity.getNoDamageTicks() > livingEntity.getMaximumNoDamageTicks() / 2F) return;

        EntityDamageEvent event = customEvent(holder, target, Config.Setting.FISHING_ROD_DAMAGE.getDouble());

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) return;

        livingEntity.setVelocity(calculateVelocity(livingEntity.getVelocity(), livingEntity.getLocation(), hook.getLocation()));

        livingEntity.damage(Config.Setting.FISHING_ROD_DAMAGE.getDouble());

        debug(holder, "&6Velocity: &a" + livingEntity.getVelocity());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHook(PlayerFishEvent e) {
        if (!Config.Setting.FISHING_ROD_CANCEL_DRAG.getBoolean() || e.getState() != PlayerFishEvent.State.CAUGHT_ENTITY)
            return;

        e.getHook().remove();

        e.setCancelled(true);

        debug(e.getPlayer(), "&6Cancelled Dragging: &a" + e.isCancelled());
    }

    /*
     Thanks to OldCombatMechanics for this one
     https://github.com/kernitus/BukkitOldCombatMechanics

     Return a new EntityDamageByEntityEvent, Return if it's cancelled by a Protection Plugin
     */
    @SuppressWarnings("removal")
    private EntityDamageEvent customEvent(Player rodder, Entity entity, double damage) {
        return new EntityDamageByEntityEvent(rodder, entity, EntityDamageEvent.DamageCause.PROJECTILE, damage);
    }

    private Vector calculateVelocity(Vector vel, Location player, Location loc) {

        double directionX = loc.getX() - player.getX();
        double directionZ = loc.getZ() - player.getZ();

        double random;

        //Modify the directions just like the server does
        while (directionX * directionX + directionZ * directionZ < 0.0001) {

            //This is not the proper way to do it, However it saves two Math.random() calls.
            random = (Math.random() - Math.random()) * .01D;

            directionX = random;
            directionZ = random;
        }

        double magnitude = MathUtils.sqrt(directionX * directionX + directionZ * directionZ);

        double y = vel.getY() / 2;
        double x = vel.getX() / 2;
        double z = vel.getZ() / 2;

        x -= directionX / magnitude * 0.4;

        y += 0.4;

        z -= directionZ / magnitude * 0.4;

        if (y >= 0.4) {
            y = 0.4;
        }

        return new Vector(x, y, z);
    }
}