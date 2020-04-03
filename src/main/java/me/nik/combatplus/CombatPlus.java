package me.nik.combatplus;

import me.nik.combatplus.commands.CommandManager;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.handlers.Initializer;
import me.nik.combatplus.handlers.UnsupportedCheck;
import me.nik.combatplus.handlers.UpdateChecker;
import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.SetAttackSpeed;
import me.nik.combatplus.utils.SetCustomHealth;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class CombatPlus extends JavaPlugin {

    @Override
    public void onEnable() {
        //Load Files
        new Initializer().initializeFiles();

        //Startup Message
        System.out.println();
        System.out.println("          " + ChatColor.RED + "CombatPlus " + ChatColor.UNDERLINE + "v" + this.getDescription().getVersion());
        System.out.println();
        System.out.println("             " + ChatColor.BOLD + "Author: " + ChatColor.UNDERLINE + "Nik");
        System.out.println();

        //Unsupported Version Checker
        new UnsupportedCheck().check();

        //Load Commands
        getCommand("combatplus").setExecutor(new CommandManager());

        //Load Listeners
        new Initializer().initialize();

        //Load Player Stats to allow reloading availability
        if (Config.get().getBoolean("combat.settings.old_pvp")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                new SetAttackSpeed().setAttackSpd(player);
            });
        }
        if (Config.get().getBoolean("custom.player_health.enabled")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                new SetCustomHealth().setHealth(player);
            });
        }

        //Check for Updates
        if (Config.get().getBoolean("settings.check_for_updates")) {
            BukkitTask UpdateChecker = new UpdateChecker(this).runTaskAsynchronously(this);
        } else {
            System.out.println(Messenger.message("console.update_disabled"));
        }

        //Load bStats
        int pluginID = 6982;
        MetricsLite metricsLite = new MetricsLite(this, pluginID);
    }
    @Override
    public void onDisable() {
        //Load Default Stats to avoid server damage
        if (Config.get().getBoolean("combat.settings.old_pvp")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                double defaultAttSpd = Config.get().getDouble("advanced.settings.new_pvp.attack_speed");
                final AttributeInstance playerAttSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
                playerAttSpeed.setBaseValue(defaultAttSpd);
                player.saveData();
            });
        }
        if (Config.get().getBoolean("custom.player_health.enabled")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                double defaultHealth = Config.get().getDouble("advanced.settings.base_player_health");
                final AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                playerMaxHealth.setBaseValue(defaultHealth);
                player.saveData();
            });
        }
        //Reload Files
        Config.reload();
        Config.save();
        Lang.reload();
        Lang.save();
    }
}
