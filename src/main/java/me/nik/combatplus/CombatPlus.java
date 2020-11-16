package me.nik.combatplus;

import me.nik.combatplus.api.events.CombatPlusLoadEvent;
import me.nik.combatplus.commands.CommandManager;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.files.commentedfiles.CommentedFileConfiguration;
import me.nik.combatplus.handlers.PapiHook;
import me.nik.combatplus.handlers.UpdateChecker;
import me.nik.combatplus.listeners.GuiListener;
import me.nik.combatplus.managers.CustomRecipes;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.metrics.MetricsLite;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.modules.impl.Blocking;
import me.nik.combatplus.modules.impl.BowBoost;
import me.nik.combatplus.modules.impl.CombatLog;
import me.nik.combatplus.modules.impl.CustomHealth;
import me.nik.combatplus.modules.impl.DamageModifiers;
import me.nik.combatplus.modules.impl.DisabledItems;
import me.nik.combatplus.modules.impl.EnchantedGoldenApple;
import me.nik.combatplus.modules.impl.Enderpearl;
import me.nik.combatplus.modules.impl.FishingRodKnockback;
import me.nik.combatplus.modules.impl.GoldenApple;
import me.nik.combatplus.modules.impl.HealthBar;
import me.nik.combatplus.modules.impl.Offhand;
import me.nik.combatplus.modules.impl.OldPvP;
import me.nik.combatplus.modules.impl.PlayerRegen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
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
        setDefaultStats();

        this.modules.forEach(Module::disInit);

        config.reset();
        lang.reload();
        lang.save();

        instance = null;
    }

    /**
     * This needs a re-code, i might do it once im not so lazy
     */

    @Override
    public void onEnable() {
        instance = this;
        this.lang = new Lang();

        this.config = new Config(this);

        //Load Files
        loadFiles();

        this.getServer().getConsoleSender().sendMessage(this.STARTUP_MESSAGE);

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
            consoleMessage(MsgType.CONSOLE_UPDATE_DISABLED.getMessage());
        }
    }

    /**
     * Default Stats to avoid Server Damage
     */
    private void setDefaultStats() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final double defaultHealth = Config.Setting.ADV_BASE_HEALTH.getDouble();
            final AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            playerMaxHealth.setBaseValue(defaultHealth);
            final double defaultAttSpd = Config.Setting.ADV_NEW_ATTACK_SPEED.getDouble();
            final AttributeInstance playerAttSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
            playerAttSpeed.setBaseValue(defaultAttSpd);
            player.saveData();
        });
    }

    private void initModules() {
        this.modules.clear();

        //add modules
        this.modules.add(new PlayerRegen());
        this.modules.add(new OldPvP());
        this.modules.add(new Offhand());
        this.modules.add(new HealthBar());
        this.modules.add(new GoldenApple());
        this.modules.add(new FishingRodKnockback());
        this.modules.add(new Enderpearl());
        this.modules.add(new EnchantedGoldenApple());
        this.modules.add(new DisabledItems());
        this.modules.add(new DamageModifiers());
        this.modules.add(new CustomHealth());
        this.modules.add(new CombatLog());
        this.modules.add(new BowBoost());
        this.modules.add(new Blocking());

        this.modules.forEach(Module::init);

        if (Config.Setting.ENCHANTED_APPLE_CRAFTING.getBoolean()) {
            try {
                this.getServer().addRecipe(new CustomRecipes(this).enchantedGoldenAppleRecipe());
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Initialize enabled Listeners
     */
    private void initListeners() {

        final PluginManager pm = this.getServer().getPluginManager();

        //GUI Listener (Do not remove this, idiot nik)
        pm.registerEvents(new GuiListener(), this);
    }

    /**
     * @param message The console message to send to the Server (ChatColor Friendly)
     */
    public void consoleMessage(String message) {
        this.getServer().getConsoleSender().sendMessage(message);
    }
}