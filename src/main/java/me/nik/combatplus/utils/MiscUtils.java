package me.nik.combatplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class MiscUtils {

    public static boolean isPlaceholderApiEnabled() {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
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
