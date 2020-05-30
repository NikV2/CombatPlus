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

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRodLand(ProjectileHitEvent e) {

        Entity rodHook = e.getEntity();
        World world = rodHook.getWorld();
        if (e.getEntityType() != EntityType.FISHING_HOOK) return;

        Entity target;

        try {
            target = e.getHitEntity();
        } catch (NoSuchMethodError error) {
            target = world.getNearbyEntities(rodHook.getLocation(), 0.25, 0.25, 0.25).stream().filter(entity -> entity instanceof Player).findFirst().orElse(null);
        }

        if (target == null) return;
        if (!(target instanceof LivingEntity)) return;

        if (target.hasMetadata("NPC")) return;

        FishHook hook = (FishHook) rodHook;
        Player rodder = (Player) hook.getShooter();

        if (target instanceof Player) {
            Player player = (Player) target;

            if (player.equals(rodder)) return;

            if (player.getGameMode() == GameMode.CREATIVE) return;
        }
        LivingEntity livingEntity = (LivingEntity) target;

        if (livingEntity.getNoDamageTicks() > livingEntity.getMaximumNoDamageTicks() / 2f) return;
        EntityDamageEvent event = customEvent(rodder, target, fishingRodDamage);
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
        double xDist = hook.getX() - player.getX();
        double zDist = hook.getZ() - player.getZ();

        while (xDist * xDist + zDist * zDist < 0.0001) {
            xDist = (Math.random() - Math.random()) * 0.01D;
            zDist = (Math.random() - Math.random()) * 0.01D;
        }

        double distance = Math.sqrt(xDist * xDist + zDist * zDist);

        double y = currentVelocity.getY() / 2;
        double x = currentVelocity.getX() / 2;
        double z = currentVelocity.getZ() / 2;

        x -= xDist / distance * 0.4;

        y += 0.4;

        z -= zDist / distance * 0.4;

        if (y >= 0.4) {
            y = 0.4;
        }

        return new Vector(x, y, z);
    }

    private EntityDamageEvent customEvent(Player rodder, Entity entity, double damage) {
        return new EntityDamageByEntityEvent(rodder, entity, EntityDamageEvent.DamageCause.PROJECTILE, damage);
    }
}
