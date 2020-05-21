package me.nik.combatplus.utils;

import me.rerere.matrix.api.HackType;
import me.rerere.matrix.api.MatrixAPI;
import me.rerere.matrix.api.MatrixAPIProvider;
import org.bukkit.Bukkit;

public class ACManager {

    public void hookAntiCheat() {
        if (Bukkit.getPluginManager().getPlugin("Matrix") != null) {
            MatrixAPI ac = MatrixAPIProvider.getAPI();
            ac.setEnable(HackType.FASTHEAL, false);
        }
    }
}
