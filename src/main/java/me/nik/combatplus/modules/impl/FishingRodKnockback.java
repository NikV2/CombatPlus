package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
        super("Fishing Rod Knockback", Config.Setting.FISHING_ROD_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRodLand(final ProjectileHitEvent e) {
        if (e.getEntityType() != EntityType.FISHING_HOOK) return;

        Entity rodHook = e.getEntity();

        Entity target;

        target = e.getHitEntity();

        if (target == null) return;

        if (!(target instanceof LivingEntity)) return;

        //Return on citizens NPCs
        if (target.hasMetadata("NPC")) return;

        FishHook hook = (FishHook) rodHook;

        Player holder = (Player) hook.getShooter();

        if (target instanceof Player) {

            Player player = (Player) target;

            if (player.equals(holder)) return;

            if (player.getGameMode() == GameMode.CREATIVE) return;

        }

        LivingEntity livingEntity = (LivingEntity) target;

        if (livingEntity.getNoDamageTicks() > livingEntity.getMaximumNoDamageTicks() / 2f) return;

        EntityDamageEvent event = customEvent(holder, target, Config.Setting.ADV_FISHING_ROD_DAMAGE.getDouble());

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) return;

        livingEntity.setVelocity(calculateVelocity(livingEntity.getVelocity(), livingEntity.getLocation(), hook.getLocation()));

        livingEntity.damage(Config.Setting.ADV_FISHING_ROD_DAMAGE.getDouble());

        debug(holder, "&6Velocity: &a" + livingEntity.getVelocity());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHook(final PlayerFishEvent e) {

        if (!Config.Setting.FISHING_ROD_CANCEL_DRAG.getBoolean()) return;

        if (e.getState() != PlayerFishEvent.State.CAUGHT_ENTITY) return;

        e.getHook().remove();

        e.setCancelled(true);

        debug(e.getPlayer(), "&6Cancelled Dragging: &a" + e.isCancelled());
    }

    /*
     Thanks to OldCombatMechanics for this one
     https://github.com/kernitus/BukkitOldCombatMechanics

     Return a new EntityDamageByEntityEvent, Return if it's cancelled by a Protection Plugin
     */
    private EntityDamageEvent customEvent(Player rodder, Entity entity, double damage) {
        return new EntityDamageByEntityEvent(rodder, entity, EntityDamageEvent.DamageCause.PROJECTILE, damage);
    }

    private Vector calculateVelocity(Vector vel, Location player, Location loc) {
        double xDist = loc.getX() - player.getX();
        double zDist = loc.getZ() - player.getZ();

        while (xDist * xDist + zDist * zDist < 0.0001) {
            xDist = (Math.random() - Math.random()) * 0.01D;
            zDist = (Math.random() - Math.random()) * 0.01D;
        }

        double distance = Math.sqrt(xDist * xDist + zDist * zDist);

        double y = vel.getY() / 2;
        double x = vel.getX() / 2;
        double z = vel.getZ() / 2;

        x -= xDist / distance * 0.4;

        y += 0.4;

        z -= zDist / distance * 0.4;

        if (y >= 0.4) {
            y = 0.4;
        }

        return new Vector(x, y, z);
    }
}