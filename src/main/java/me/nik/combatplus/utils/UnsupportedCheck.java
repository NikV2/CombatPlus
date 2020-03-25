package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import org.bukkit.Bukkit;

public class UnsupportedCheck {
    public void check() {
        if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.7")) {
            if (Config.get().getBoolean("combat.settings.old_pvp")) {
                Config.get().set("combat.settings.old_pvp", false);
            }
            if (Config.get().getBoolean("combat.settings.old_weapon_damage")) {
                Config.get().set("combat.settings.old_weapon_damage", false);
            }
            if (Config.get().getBoolean("combat.settings.old_tool_damage")) {
                Config.get().set("combat.settings.old_tool_damage", false);
            }
            if (Config.get().getBoolean("combat.settings.old_sharpness")) {
                Config.get().set("combat.settings.old_sharpness", false);
            }
            if (Config.get().getBoolean("combat.settings.disable_sweep_attacks")) {
                Config.get().set("combat.settings.disable_sweep_attacks", false);
            }
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_version"));
        }
    }
}
