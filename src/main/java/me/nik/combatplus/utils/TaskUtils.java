package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import org.bukkit.Bukkit;

public final class TaskUtils {

    private TaskUtils() {
    }

    public static void taskTimer(Runnable runnable, long delay, long interval) {
        if (CombatPlus.getInstance().isFolia()) {

            taskTimerAsync(runnable, delay, interval);

            return;
        }

        Bukkit.getScheduler().runTaskTimer(CombatPlus.getInstance(), runnable, delay, interval);
    }

    public static void taskTimerAsync(Runnable runnable, long delay, long interval) {
        if (CombatPlus.getInstance().isFolia()) {
            Bukkit.getGlobalRegionScheduler().runAtFixedRate(CombatPlus.getInstance(), task -> runnable.run(), delay, interval);
        } else {
            Bukkit.getScheduler().runTaskTimerAsynchronously(CombatPlus.getInstance(), runnable, delay, interval);
        }
    }

    public static void task(Runnable runnable) {
        if (CombatPlus.getInstance().isFolia()) {

            taskAsync(runnable);

            return;
        }

        Bukkit.getScheduler().runTask(CombatPlus.getInstance(), runnable);
    }

    public static void taskAsync(Runnable runnable) {
        if (CombatPlus.getInstance().isFolia()) {
            Bukkit.getGlobalRegionScheduler().run(CombatPlus.getInstance(), task -> runnable.run());
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(CombatPlus.getInstance(), runnable);
        }
    }

    public static void taskLater(Runnable runnable, long delay) {
        if (CombatPlus.getInstance().isFolia()) {

            taskLaterAsync(runnable, delay);

            return;
        }

        Bukkit.getScheduler().runTaskLater(CombatPlus.getInstance(), runnable, delay);
    }

    public static void taskLaterAsync(Runnable runnable, long delay) {
        if (CombatPlus.getInstance().isFolia()) {
            Bukkit.getGlobalRegionScheduler().runDelayed(CombatPlus.getInstance(), task -> runnable.run(), delay);
        } else {
            Bukkit.getScheduler().runTaskLaterAsynchronously(CombatPlus.getInstance(), runnable, delay);
        }
    }
}