package me.nik.combatplus.handlers;

import me.nik.combatplus.api.Manager;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.Bukkit;

public class UnsupportedCheck extends Manager {
    public void check() {
        if (Bukkit.getVersion().contains("1.8")) {
            if (configBoolean("combat.settings.old_pvp")) {
                booleanSet("combat.settings.old_pvp", false);
            }
            if (configBoolean("combat.settings.old_weapon_damage")) {
                booleanSet("combat.settings.old_weapon_damage", false);
            }
            if (configBoolean("combat.settings.old_tool_damage")) {
                booleanSet("combat.settings.old_tool_damage", false);
            }
            if (configBoolean("combat.settings.old_sharpness")) {
                booleanSet("combat.settings.old_sharpness", false);
            }
            if (configBoolean("combat.settings.disable_sweep_attacks")) {
                booleanSet("combat.settings.disable_sweep_attacks", false);
            }
            if (configBoolean("combat.settings.old_player_regen")) {
                booleanSet("combat.settings.old_player_regen", false);
            }
            if (configBoolean("golden_apple_cooldown.golden_apple.enabled")) {
                booleanSet("golden_apple_cooldown.golden_apple.enabled", false);
            }
            if (configBoolean("golden_apple_cooldown.enchanted_golden_apple.enabled")) {
                booleanSet("golden_apple_cooldown.enchanted_golden_apple.enabled", false);
            }
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_version"));
        } else if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10")) {
            booleanSet("combat.settings.disable_sweep_attacks", false);
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_sweep_attack"));
        } else if (Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
            booleanSet("golden_apple_cooldown.enchanted_golden_apple.enabled", false);
            booleanSet("golden_apple_cooldown.golden_apple.enabled", false);
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_version"));
        }
    }
}
