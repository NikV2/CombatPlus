package me.nik.combatplus;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.listeners.AttackSpeed;
import me.nik.combatplus.listeners.DamageModifiers;
import me.nik.combatplus.utils.ResetStats;
import org.bukkit.Bukkit;
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

        //Load Listeners
        Bukkit.getServer().getPluginManager().registerEvents(new AttackSpeed(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new DamageModifiers(), this);
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
