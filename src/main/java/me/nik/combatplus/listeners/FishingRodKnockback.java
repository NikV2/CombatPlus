package me.nik.combatplus.listeners;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

public class FishingRodKnockback implements Listener {

    private final double fishingRodDamage = Config.get().getDouble("advanced.settings.knockback.fishing_rod.damage");
    private final boolean cancelDragging = Config.get().getBoolean("knockback.fishing_rod.cancel_dragging");
    private final boolean useEntityDamageEvent = Config.get().getBoolean("advanced.settings.knockback.fishing_rod.entity_damage_event");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRodLand(ProjectileHitEvent e) {

        Entity hookEntity = e.getEntity();
        World world = hookEntity.getWorld();
        if (e.getEntityType() != EntityType.FISHING_HOOK) return;

        Entity hitEntity;

        try {
            hitEntity = e.getHitEntity();
        } catch (NoSuchMethodError error) {
            hitEntity = world.getNearbyEntities(hookEntity.getLocation(), 0.25, 0.25, 0.25).stream().filter(entity -> entity instanceof Player).findFirst().orElse(null);
        }

        if (hitEntity == null) return;
        if (!(hitEntity instanceof LivingEntity)) return;

        if (hitEntity.hasMetadata("NPC")) return;

        FishHook hook = (FishHook) hookEntity;
        Player rodder = (Player) hook.getShooter();

        if (hitEntity instanceof Player) {
            Player player = (Player) hitEntity;

            if (player.equals(rodder)) return;

            if (player.getGameMode() == GameMode.CREATIVE) return;
        }

        LivingEntity livingEntity = (LivingEntity) hitEntity;

        if (livingEntity.getNoDamageTicks() > livingEntity.getMaximumNoDamageTicks() / 2f) return;

        EntityDamageEvent event = makeEvent(rodder, hitEntity, fishingRodDamage);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return;

        livingEntity.setVelocity(calculateKnockbackVelocity(livingEntity.getVelocity(), livingEntity.getLocation(), hook.getLocation()));
        livingEntity.damage(fishingRodDamage);
        Messenger.debug(rodder, "&3Fishing Rod Knockback &f&l>> &6Velocity: &a" + livingEntity.getVelocity().toString());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHook(PlayerFishEvent e) {
        if (!cancelDragging) return;
        if (e.getState() != PlayerFishEvent.State.CAUGHT_ENTITY) return;
        e.getHook().remove();
        e.setCancelled(true);
        Messenger.debug(e.getPlayer(), "&3Fishing Rod Knockback &f&l>> &6Cancelled Dragging: &atrue");
    }

    private Vector calculateKnockbackVelocity(Vector currentVelocity, Location player, Location hook) {
        double xDistance = hook.getX() - player.getX();
        double zDistance = hook.getZ() - player.getZ();

        while (xDistance * xDistance + zDistance * zDistance < 0.0001) {
            xDistance = (Math.random() - Math.random()) * 0.01D;
            zDistance = (Math.random() - Math.random()) * 0.01D;
        }

        double distance = Math.sqrt(xDistance * xDistance + zDistance * zDistance);

        double y = currentVelocity.getY() / 2;
        double x = currentVelocity.getX() / 2;
        double z = currentVelocity.getZ() / 2;

        x -= xDistance / distance * 0.4;

        y += 0.4;

        z -= zDistance / distance * 0.4;

        if (y >= 0.4) {
            y = 0.4;
        }

        return new Vector(x, y, z);
    }

    private EntityDamageEvent makeEvent(Player rodder, Entity entity, double damage) {
        if (useEntityDamageEvent) {
            return new EntityDamageEvent(entity,
                    EntityDamageEvent.DamageCause.PROJECTILE,
                    damage);
        } else {
            return new EntityDamageByEntityEvent(rodder,
                    entity,
                    EntityDamageEvent.DamageCause.PROJECTILE,
                    damage);
        }
    }
}
