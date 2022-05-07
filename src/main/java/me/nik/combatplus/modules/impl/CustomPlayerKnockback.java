package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.ServerUtils;
import me.nik.combatplus.utils.custom.ExpiringMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.UUID;

public class CustomPlayerKnockback extends Module {
    private final ExpiringMap<UUID, Vector> knockbackQueue = new ExpiringMap<>(1000L);

    public CustomPlayerKnockback() {
        super(Config.Setting.CUSTOM_PLAYER_KNOCKBACK_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof LivingEntity)
                || !(e.getEntity() instanceof Player)
                || e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;

        Player player = (Player) e.getEntity();

        //Don't bother with blocking
        if (player.isBlocking()) return;

        Vector oldVelocity = player.getVelocity();

        LivingEntity damager = (LivingEntity) e.getDamager();

        Location playerLocation = player.getLocation();
        Location damagerLocation = damager.getLocation();

        double directionX = damagerLocation.getX() - playerLocation.getX();
        double directionZ = damagerLocation.getZ() - playerLocation.getZ();

        double random;

        //Modify the directions just like the server does
        while ((directionX * directionX + directionZ * directionZ) < 1E-4D) {

            //This is not the proper way to do it, However it saves two Math.random() calls.
            random = (Math.random() - Math.random()) * .01D;

            directionX = random;
            directionZ = random;
        }

        Vector newVelocity = player.getVelocity();

        double magnitude = Math.sqrt(directionX * directionX + directionZ * directionZ);

        //Calculate the velocity values just like the server, and also apply the custom friction.
        double velocityX = (newVelocity.getX() / Config.Setting.CUSTOM_PLAYER_KNOCKBACK_FRICTION.getDouble())
                - (directionX / magnitude * Config.Setting.CUSTOM_PLAYER_KNOCKBACK_HORIZONTAL.getDouble());

        double velocityY = Math.min((newVelocity.getY() / Config.Setting.CUSTOM_PLAYER_KNOCKBACK_FRICTION.getDouble())
                + Config.Setting.CUSTOM_PLAYER_KNOCKBACK_VERTICAL.getDouble(), Config.Setting.CUSTOM_PLAYER_KNOCKBACK_VERTICAL_LIMIT.getDouble());

        double velocityZ = (newVelocity.getZ() / Config.Setting.CUSTOM_PLAYER_KNOCKBACK_FRICTION.getDouble())
                - (directionZ / magnitude * Config.Setting.CUSTOM_PLAYER_KNOCKBACK_HORIZONTAL.getDouble());

        newVelocity.setX(velocityX);
        newVelocity.setY(velocityY);
        newVelocity.setZ(velocityZ);

        EntityEquipment damagerEquipment = damager.getEquipment();

        if (damagerEquipment != null) {

            ItemStack damagerHandItem = damagerEquipment.getItemInMainHand();

            int knockbackMultiplier = damagerHandItem != null && damagerHandItem.getType() != Material.AIR
                    ? damagerHandItem.getEnchantmentLevel(Enchantment.KNOCKBACK)
                    : 0;

            if (damager instanceof Player && ((Player) damager).isSprinting()) knockbackMultiplier++;

            if (knockbackMultiplier > 0) {

                float damagerYaw = damagerLocation.getYaw();

                //Calculate additional velocity just like the server.
                double additionalX = (-Math.sin(damagerYaw * 3.1415927F / 180.0F)
                        * (float) knockbackMultiplier * Config.Setting.CUSTOM_PLAYER_KNOCKBACK_EXTRA_HORIZONTAL.getDouble());

                double additionalZ = Math.cos(damagerYaw * 3.1415927F / 180.0F) *
                        (float) knockbackMultiplier * Config.Setting.CUSTOM_PLAYER_KNOCKBACK_EXTRA_HORIZONTAL.getDouble();

                newVelocity.setX(newVelocity.getX() + additionalX);
                newVelocity.setY(newVelocity.getY() + Config.Setting.CUSTOM_PLAYER_KNOCKBACK_EXTRA_VERTICAL.getDouble());
                newVelocity.setZ(newVelocity.getZ() + additionalZ);
            }
        }

        //Handle knockback resistance introduced in 1.16+
        if (ServerUtils.isNetherUpdate()) {

            double knockbackResistance = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getValue();

            if (knockbackResistance > 1D) {
                newVelocity.setX(newVelocity.getX() * knockbackResistance);
                newVelocity.setZ(newVelocity.getZ() * knockbackResistance);
            }
        }

        this.knockbackQueue.put(player.getUniqueId(), newVelocity);

        debug(player, "&6Old velocity: &a" + oldVelocity + " &6New velocity: &a" + newVelocity);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onVelocity(PlayerVelocityEvent e) {

        Player player = e.getPlayer();

        Vector velocity = this.knockbackQueue.get(player.getUniqueId());

        if (velocity == null) return;

        e.setVelocity(velocity);

        debug(player, "&6Sent");
    }
}