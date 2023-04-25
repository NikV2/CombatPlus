package me.nik.combatplus.utils;

import me.nik.combatplus.utils.custom.CombatPlusException;

public final class MathUtils {

    private MathUtils() {
        throw new CombatPlusException("This is a static class dummy!");
    }

    /**
     * FastMath Sin Table
     */
    private static final float[] SIN_TABLE = new float[4096];

    public static float sin(float value) {
        return SIN_TABLE[(int) (value * 651.8986F) & 4095];
    }

    public static float cos(float value) {
        return SIN_TABLE[(int) ((value + ((float) Math.PI / 2F)) * 651.8986F) & 4095];
    }

    /**
     * Compute the square root of x number.
     *
     * @param x number on which evaluation is done
     * @return square root of x. If the argument is NaN or less than zero, the result is NaN.
     */
    public static double sqrt(double x) {
        if (Double.isNaN(x) || x < 0.0D) {
            return Double.NaN;
        }

        double t;

        double squareRoot = x / 2;

        if (x != 0.0D) {

            do {

                t = squareRoot;

                squareRoot = (t + (x / t)) / 2;

            } while ((t - squareRoot) != 0D);
        }

        return squareRoot;
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