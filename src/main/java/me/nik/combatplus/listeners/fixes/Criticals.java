package me.nik.combatplus.listeners.fixes;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
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

    // Patches the Criticals Cheat if a player crits while he's on the ground

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public static void onCritical(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        Player p = (Player) e.getDamager();
        if (isCritical(p)) {
            if ((p.getLocation().getY() % 1.0 == 0 || p.getLocation().getY() % 0.5 == 0) && p.getLocation().clone().subtract(0, 1.0, 0).getBlock().getType().isSolid()) {
                e.setCancelled(true);
                if (debug(p)) {
                    p.sendMessage(Messenger.prefix(ChatColor.AQUA + "Criticals: " + "Blocked: true" + ChatColor.GREEN + " Velocity: " + p.getVelocity().getY()));
                }
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

    private static boolean debug(Player player) {
        if (Config.get().getBoolean("settings.developer_mode")) {
            return player.hasPermission("cp.debug");
        }
        return false;
    }
}
