package me.nik.combatplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class MiscUtils {

    public static boolean isPlaceholderApiEnabled() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public static boolean isCritical(Player p) {
        return p.getFallDistance() > 0.0f
                && !p.isOnGround()
                && !p.isInsideVehicle()
                && !p.hasPotionEffect(PotionEffectType.BLINDNESS)
                && !isInWater(p.getLocation())
                && p.getEyeLocation().getBlock().getType() != Material.LADDER;
    }

    public static boolean isInWater(Location loc, int blocks) {
        for (int i = loc.getBlockY(); i > loc.getBlockY() - blocks; i--) {
            Block block = (new Location(loc.getWorld(), loc.getBlockX(), i, loc.getBlockZ())).getBlock();
            if (block.getType() != Material.AIR) {
                return block.isLiquid();
            }
        }
        return false;
    }

    public static boolean isInWater(Location loc) {
        return isInWater(loc, 25);
    }

    public static Vector calculateVelocity(Vector vel, Location player, Location loc) {
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

    public static double clamp(double value, double max) {
        double realMin = Math.min(0.0, max);
        double realMax = Math.max(0.0, max);
        if (value < realMin) {
            value = realMin;
        }
        if (value > realMax) {
            value = realMax;
        }
        return value;
    }
}
