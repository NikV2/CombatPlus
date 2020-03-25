package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.Bukkit;

public class UnsupportedListeners {
    public void check() {
        if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10")) {
            Config.get().set("combat.settings.disable_sweep_attacks", false);
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_sweep_attack"));
        }
    }
}
