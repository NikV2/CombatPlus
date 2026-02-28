package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import org.bukkit.Bukkit;

public final class TaskUtils {

    private TaskUtils() {
    }

    public static void taskTimer(Runnable runnable, long delay, long interval) {
        Bukkit.getScheduler().runTaskTimer(CombatPlus.getInstance(), runnable, delay, interval);
    }

    public static void taskTimerAsync(Runnable runnable, long delay, long interval) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(CombatPlus.getInstance(), runnable, delay, interval);
    }

    public static void task(Runnable runnable) {
        Bukkit.getScheduler().runTask(CombatPlus.getInstance(), runnable);
    }

    public static void taskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(CombatPlus.getInstance(), runnable);

    }

    public static void taskLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(CombatPlus.getInstance(), runnable, delay);
    }

    public static void taskLaterAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(CombatPlus.getInstance(), runnable, delay);
    }
}