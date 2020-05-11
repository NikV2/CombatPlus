package me.nik.combatplus.utils;

import me.rerere.matrix.api.HackType;
import me.rerere.matrix.api.MatrixAPI;
import me.rerere.matrix.api.MatrixAPIProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ACManager {

    private final PluginManager pm = Bukkit.getPluginManager();

    public void hookAntiCheat() {
        if (pm.getPlugin("Matrix") != null) {
            MatrixAPI ac = MatrixAPIProvider.getAPI();
            ac.setEnable(HackType.FASTHEAL, false);
        }
    }
}
