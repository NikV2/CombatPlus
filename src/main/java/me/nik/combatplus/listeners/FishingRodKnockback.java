package me.nik.combatplus.listeners;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.MiscUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

public class FishingRodKnockback implements Listener {

    /*
    Bring back old fishing rod behavior
     */

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRodLand(ProjectileHitEvent e) {
        Entity rodHook = e.getEntity();
        if (e.getEntityType() != EntityType.FISHING_HOOK) return;
        Entity target;
        target = e.getHitEntity();

        if (target == null) return;
        if (!(target instanceof LivingEntity)) return;

        /*
        return on citizen NPCS
         */
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

        livingEntity.setVelocity(MiscUtils.calculateVelocity(livingEntity.getVelocity(), livingEntity.getLocation(), hook.getLocation()));
        livingEntity.damage(Config.Setting.ADV_FISHING_ROD_DAMAGE.getDouble());
        Messenger.debug(holder, "&3Fishing Rod Knockback &f&l>> &6Velocity: &a" + livingEntity.getVelocity().toString());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHook(PlayerFishEvent e) {
        if (!Config.Setting.FISHING_ROD_CANCEL_DRAG.getBoolean()) return;
        if (e.getState() != PlayerFishEvent.State.CAUGHT_ENTITY) return;
        e.getHook().remove();
        e.setCancelled(true);
        Messenger.debug(e.getPlayer(), "&3Fishing Rod Knockback &f&l>> &6Cancelled Dragging: &atrue");
    }

    /*
     Thanks to OldCombatMechanics for this one
     https://github.com/kernitus/BukkitOldCombatMechanics

     Return a new EntityDamageByEntityEvent, Return if it's cancelled by a Protection Plugin
     */
    private EntityDamageEvent customEvent(Player rodder, Entity entity, double damage) {
        return new EntityDamageByEntityEvent(rodder, entity, EntityDamageEvent.DamageCause.PROJECTILE, damage);
    }
}