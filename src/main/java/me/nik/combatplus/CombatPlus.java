package me.nik.combatplus;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.listeners.AttackSpeed;
import me.nik.combatplus.listeners.BowBoost;
import me.nik.combatplus.listeners.DamageModifiers;
import me.nik.combatplus.utils.ColourUtils;
import me.nik.combatplus.utils.ResetStats;
import me.nik.combatplus.utils.SetAttackSpeed;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CombatPlus extends JavaPlugin {

    @Override
    public void onEnable() {
        //Load Files
        Config.setup();
        Config.addDefaults();
        Config.get().options().copyDefaults(true);
        Config.save();
        Lang.setup();
        Lang.addDefaults();
        Lang.get().options().copyDefaults(true);
        Lang.save();

        //Startup Message
        System.out.println();
        System.out.println("          " + ChatColor.RED + "CombatPlus " + ChatColor.UNDERLINE + "v" + this.getDescription().getVersion());
        System.out.println();
        System.out.println("             " + ChatColor.BOLD + "Author: " + ChatColor.UNDERLINE + "Nik");
        System.out.println();

        //Load Listeners
        if (Config.get().getBoolean("combat.settings.old_pvp")) {
            Bukkit.getServer().getPluginManager().registerEvents(new AttackSpeed(), this);
            System.out.println(ColourUtils.format(Lang.get().getString("prefix")) + ColourUtils.format(Lang.get().getString("console.old_pvp_on")));
        } else {
            System.out.println(ColourUtils.format(Lang.get().getString("prefix")) + ColourUtils.format(Lang.get().getString("console.old_pvp_off")));
        }
        if (Config.get().getBoolean("combat.settings.old_weapon_damage") || Config.get().getBoolean("combat.settings.old_tool_damage") || Config.get().getBoolean("combat.settings.disable_sweep_attacks")) {
            Bukkit.getServer().getPluginManager().registerEvents(new DamageModifiers(), this);
            System.out.println(ColourUtils.format(Lang.get().getString("prefix")) + ColourUtils.format(Lang.get().getString("console.modifiers_on")));
        } else {
            System.out.println(ColourUtils.format(Lang.get().getString("prefix")) + ColourUtils.format(Lang.get().getString("console.modifiers_off")));
        }
        if (Config.get().getBoolean("combat.settings.disable_arrow_boost")) {
            Bukkit.getServer().getPluginManager().registerEvents(new BowBoost(), this);
            System.out.println(ColourUtils.format(Lang.get().getString("prefix")) + ColourUtils.format(Lang.get().getString("console.arrow_boost_on")));
        } else {
            System.out.println(ColourUtils.format(Lang.get().getString("prefix")) + ColourUtils.format(Lang.get().getString("console.arrow_boost_off")));
        }

        //Load Player Stats to allow reloading compability
        if (Config.get().getBoolean("combat.settings.old_pvp")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                new SetAttackSpeed().setAttackSpd(player);
            });
        }
    }
    @Override
    public void onDisable() {
        //Load Default Stats to avoid server damage
        Bukkit.getOnlinePlayers().forEach(player -> {
            new ResetStats().Reset(player);
        });
        //Reload Files
        Config.reload();
        Config.save();
        Lang.reload();
        Lang.save();
    }
}
