package me.nik.combatplus.utils;

public final class MathUtils {

    private MathUtils() {
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