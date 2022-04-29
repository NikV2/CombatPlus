package me.nik.combatplus.utils;

import me.nik.combatplus.utils.custom.CombatPlusException;

public final class MathUtils {

    private MathUtils() {
        throw new CombatPlusException("This is a static class dummy!");
    }

    public static double clampDouble(double value, double max) {
        final double realMin = Math.min(0.0, max);
        final double realMax = Math.max(0.0, max);

        if (value < realMin) {
            value = realMin;
        }
        if (value > realMax) {
            value = realMax;
        }
        return value;
    }
}