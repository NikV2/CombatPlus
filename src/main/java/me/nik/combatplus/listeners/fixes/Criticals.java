package me.nik.combatplus.listeners.fixes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

public class Criticals implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public static void onCritical(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        Player p = (Player) e.getDamager();
        if (isCritical(p)) {
            if ((p.getLocation().getY() % 1.0 == 0 || p.getLocation().getY() % 0.5 == 0) && p.getLocation().clone().subtract(0, 1.0, 0).getBlock().getType().isSolid()) {
                e.setCancelled(true);
            }
        }
    }

    private static boolean isCritical(Player p) {
        return p.getFallDistance() > 0.0f
                && !p.isOnGround()
                && !p.isInsideVehicle()
                && !p.hasPotionEffect(PotionEffectType.BLINDNESS)
                && !isAtWater(p.getLocation())
                && p.getEyeLocation().getBlock().getType() != Material.LADDER;
    }

    private static boolean isAtWater(Location loc, int blocks) {
        for (int i = loc.getBlockY(); i > loc.getBlockY() - blocks; i--) {
            Block block = (new Location(loc.getWorld(), loc.getBlockX(), i, loc.getBlockZ())).getBlock();
            if (block.getType() != Material.AIR) {
                return block.isLiquid();
            }
        }
        return false;
    }

    private static boolean isAtWater(Location loc) {
        return isAtWater(loc, 25);
    }
}
