package me.nik.combatplus;

import me.nik.combatplus.api.events.CombatPlusLoadEvent;
import me.nik.combatplus.commands.CommandManager;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.files.commentedfiles.CommentedFileConfiguration;
import me.nik.combatplus.listeners.GuiListener;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.managers.PapiHook;
import me.nik.combatplus.managers.UpdateChecker;
import me.nik.combatplus.metrics.MetricsLite;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.modules.impl.Blocking;
import me.nik.combatplus.modules.impl.CombatLog;
import me.nik.combatplus.modules.impl.CustomAttackSpeed;
import me.nik.combatplus.modules.impl.CustomHealth;
import me.nik.combatplus.modules.impl.CustomPlayerRegeneration;
import me.nik.combatplus.modules.impl.DamageModifiers;
import me.nik.combatplus.modules.impl.DisableBowBoost;
import me.nik.combatplus.modules.impl.DisableOffhand;
import me.nik.combatplus.modules.impl.EnchantedGoldenAppleCooldown;
import me.nik.combatplus.modules.impl.EnderpearlCooldown;
import me.nik.combatplus.modules.impl.FishingRodKnockback;
import me.nik.combatplus.modules.impl.GoldenAppleCooldown;
import me.nik.combatplus.modules.impl.HealthBar;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CombatPlus extends JavaPlugin {

    private static CombatPlus instance;

    private Config config;

    private Lang lang;

    private final List<Module> modules = new ArrayList<>();

    public static CombatPlus getInstance() {
        return instance;
    }

    private final String[] STARTUP_MESSAGE = {
            " ",
            ChatColor.RED + "Combat Plus v" + this.getDescription().getVersion(),
            " ",
            ChatColor.WHITE + "  Author: Nik",
            " "
    };

    @Override
    public void onDisable() {

        //Load Default Stats to avoid server damage
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Config.Setting.CUSTOM_PLAYER_HEALTH_DEFAULT_MAX_HEALTH.getDouble());
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Config.Setting.CUSTOM_ATTACK_SPEED_DEFAULT_ATTACK_SPEED.getDouble());
            player.saveData();
        });

        this.modules.forEach(Module::shutdown);

        config.reset();
        lang.reload();
        lang.save();

        HandlerList.unregisterAll(this);
        this.getServer().getScheduler().cancelTasks(this);

        instance = null;
    }

    @Override
    public void onEnable() {

        instance = this;

        this.lang = new Lang();

        this.config = new Config(this);

        //Load Files
        loadFiles();

        this.getServer().getConsoleSender().sendMessage(STARTUP_MESSAGE);

        //Load Commands
        getCommand("combatplus").setExecutor(new CommandManager(this));

        //Load Modules
        initModules();

        //Load Listeners
        initListeners();

        //Check for Updates
        checkForUpdates();

        //Load bStats
        new MetricsLite(this, 6982);

        //Hook PlaceholderAPI
        if (this.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PapiHook(this).register();
        }

        Bukkit.getPluginManager().callEvent(new CombatPlusLoadEvent());
    }

    public Module getModule(String name) {
        for (Module module : this.modules) {
            if (module.getName().equals(name)) {
                return module;
            }
        }

        return null;
    }

    public CommentedFileConfiguration getConfiguration() {
        return config.getConfig();
    }

    public Lang getLang() {
        return lang;
    }

    /**
     * Load all the built-in files
     */
    private void loadFiles() {
        config.setup();
        lang.setup(this);
        lang.addDefaults();
        lang.get().options().copyDefaults(true);
        lang.save();
    }

    private void checkForUpdates() {
        if (Config.Setting.CHECK_FOR_UPDATES.getBoolean()) {
            new UpdateChecker(this).runTaskAsynchronously(this);
        } else {
            Messenger.consoleMessage(MsgType.CONSOLE_UPDATE_DISABLED.getMessage());
        }
    }

    private void initModules() {

        this.modules.clear();

        //Add modules
        this.modules.addAll(Arrays.asList(
                new CustomPlayerRegeneration(),
                new CustomAttackSpeed(),
                new DisableOffhand(),
                new HealthBar(),
                new GoldenAppleCooldown(),
                new EnchantedGoldenAppleCooldown(),
                new FishingRodKnockback(),
                new EnderpearlCooldown(),
                new DamageModifiers(),
                new CustomHealth(),
                new CombatLog(),
                new DisableBowBoost(),
                new Blocking()
        ));

        this.modules.forEach(Module::load);
    }

    /**
     * Initialize enabled Listeners
     */
    private void initListeners() {

        final PluginManager pm = this.getServer().getPluginManager();

        //GUI Listener (Do not remove this, idiot nik)
        pm.registerEvents(new GuiListener(), this);
    }
}