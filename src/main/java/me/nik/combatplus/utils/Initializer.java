package me.nik.combatplus.utils;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.listeners.AttackSpeed;
import me.nik.combatplus.listeners.BowBoost;
import me.nik.combatplus.listeners.DamageModifiers;
import me.nik.combatplus.listeners.EnchGapple;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Initializer {
    Plugin plugin = CombatPlus.getPlugin(CombatPlus.class);

    public void initialize() {
        if (Config.get().getBoolean("combat.settings.old_pvp")) {
            Bukkit.getServer().getPluginManager().registerEvents(new AttackSpeed(), plugin);
            System.out.println(Messenger.message("console.old_pvp_on"));
        } else {
            System.out.println(Messenger.message("console.old_pvp_off"));
        }
        if (Config.get().getBoolean("combat.settings.old_weapon_damage") || Config.get().getBoolean("combat.settings.old_tool_damage") || Config.get().getBoolean("combat.settings.disable_sweep_attacks")) {
            Bukkit.getServer().getPluginManager().registerEvents(new DamageModifiers(), plugin);
            System.out.println(Messenger.message("console.modifiers_on"));
        } else {
            System.out.println(Messenger.message("console.modifiers_off"));
        }
        if (Config.get().getBoolean("combat.settings.disable_arrow_boost")) {
            Bukkit.getServer().getPluginManager().registerEvents(new BowBoost(), plugin);
            System.out.println(Messenger.message("console.arrow_boost_on"));
        } else {
            System.out.println(Messenger.message("console.arrow_boost_off"));
        }
        if (Config.get().getBoolean("general.settings.golden_apple_cooldown.enabled")) {
            Bukkit.getServer().getPluginManager().registerEvents(new EnchGapple(), plugin);
            System.out.println(Messenger.message("console.golden_apple_cooldown_on"));
        } else {
            System.out.println(Messenger.message("console.golden_apple_cooldown_off"));
        }
    }
}
