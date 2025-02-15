package me.nik.combatplus.utils;

import org.bukkit.Bukkit;

public enum ServerVersion {
    LATEST(Integer.MAX_VALUE),
    v1_21_3(28),
    v1_21(27),
    v1_20_6(26),
    v1_20_5(25),
    //Versions below that have no R packaging
    v1_20_R3(24),
    v1_20_R2(23),
    v1_20_R1(22),
    v1_19_R3(21),
    v1_19_R2(20),
    v1_19_R1(19),
    v1_18_R2(18),
    v1_18_R1(17),
    v1_17_R1(16),
    v1_16_R3(15),
    v1_16_R2(14),
    v1_16_R1(13),
    v1_15_R1(12),
    v1_14_R1(11),
    v1_13_R2(10),
    v1_13_R1(9),
    v1_12_R1(8),
    v1_11_R1(7),
    v1_10_R1(6),
    v1_9_R2(5),
    v1_9_R1(4),
    v1_8_R3(3),
    v1_8_R2(2),
    v1_8_R1(1);

    /*
    We're going to cache this the first time we get it.
     */
    private static ServerVersion VERSION;
    public final int value;

    ServerVersion(int value) {
        this.value = value;
    }

    public static ServerVersion getVersion() {

        if (VERSION == null) {

            String serverPackageName = Bukkit.getServer().getClass().getPackage().getName();

            ServerVersion version;

            try {
                //Newer versions have no R versioning
                if (serverPackageName.equals("org.bukkit.craftbukkit")) {
                    version = valueOf("v" + Bukkit.getServer().getBukkitVersion().split("-")[0].replace(".", "_"));
                } else {
                    version = valueOf(serverPackageName.substring(serverPackageName.lastIndexOf(".") + 1).trim());
                }
            } catch (IllegalArgumentException e) {
                version = LATEST;
            }

            VERSION = version;
        }

        return VERSION;
    }

    public boolean isHigherThan(ServerVersion target) {
        return VERSION.value > target.value;
    }

    public boolean isHigherThanOrEquals(ServerVersion target) {
        return VERSION.value >= target.value;
    }

    public boolean isLowerThan(ServerVersion target) {
        return VERSION.value < target.value;
    }

    public boolean isLowerThanOrEquals(ServerVersion target) {
        return VERSION.value <= target.value;
    }

    public boolean equals(ServerVersion target) {
        return VERSION.value == target.value;
    }

    public boolean isBetween(ServerVersion first, ServerVersion last) {
        return VERSION.value >= first.value && VERSION.value <= last.value;
    }

    @Override
    public String toString() {
        return this.name().substring(1).replace("_", ".");
    }
}