package me.nik.combatplus;

import me.nik.combatplus.commands.CommandManager;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.files.commentedfiles.CommentedFileConfiguration;
import me.nik.combatplus.handlers.PapiHook;
import me.nik.combatplus.handlers.UpdateChecker;
import me.nik.combatplus.listeners.AttributesSet;
import me.nik.combatplus.listeners.Blocking;
import me.nik.combatplus.listeners.BowBoost;
import me.nik.combatplus.listeners.DamageModifiers;
import me.nik.combatplus.listeners.DisabledItems;
import me.nik.combatplus.listeners.EnchantedGoldenApple;
import me.nik.combatplus.listeners.Enderpearl;
import me.nik.combatplus.listeners.FishingRodKnockback;
import me.nik.combatplus.listeners.GoldenApple;
import me.nik.combatplus.listeners.GuiListener;
import me.nik.combatplus.listeners.HealthBar;
import me.nik.combatplus.listeners.Offhand;
import me.nik.combatplus.listeners.PlayerRegen;
import me.nik.combatplus.listeners.combatlog.CombatListener;
import me.nik.combatplus.listeners.combatlog.CommandListener;
import me.nik.combatplus.listeners.combatlog.DisconnectListener;
import me.nik.combatplus.listeners.combatlog.ItemDropListener;
import me.nik.combatplus.listeners.combatlog.ItemPickListener;
import me.nik.combatplus.listeners.combatlog.TeleportListener;
import me.nik.combatplus.listeners.fixes.Projectiles;
import me.nik.combatplus.managers.CombatLog;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.metrics.MetricsLite;
import me.nik.combatplus.utils.CustomRecipes;
import me.nik.combatplus.utils.ResetStats;
import me.nik.combatplus.utils.SetAttackSpeed;
import me.nik.combatplus.utils.SetCustomHealth;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CombatPlus extends JavaPlugin {

    private static CombatPlus instance;

    private Config config;

    private Lang lang;

    public static CombatPlus getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        //Load Default Stats to avoid server damage
        setDefaultStats();

        //Reload Files
        config.reset();
        lang.reload();
        lang.save();

        //Done
        consoleMessage(MsgType.CONSOLE_DISABLED.getMessage());
    }

    @Override
    public void onEnable() {
        instance = this;
        this.lang = new Lang();

        this.config = new Config(this);

        //Load Files
        loadFiles();

        //Startup Message
        consoleMessage("");
        consoleMessage("          " + ChatColor.RED + "Combat Plus v" + this.getDescription().getVersion());
        consoleMessage("");
        consoleMessage("             " + ChatColor.WHITE + "Author: Nik");
        consoleMessage("");

        //Unsupported Version Checker
        checkSupported();

        //Load Commands
        getCommand("combatplus").setExecutor(new CommandManager(this));

        //Load Listeners
        initialize();

        //Load Player Stats to allow reloading availability
        loadStats();

        //Check for Updates
        checkForUpdates();

        //Load bStats
        new MetricsLite(this, 6982);

        //Hook PlaceholderAPI

        if (this.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PapiHook(this).register();
        }
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
        if (serverVersion("1.8")) return;
        this.getServer().getOnlinePlayers().forEach(player -> {
            final double defaultHealth = Config.Setting.ADV_BASE_HEALTH.getDouble();
            final AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            playerMaxHealth.setBaseValue(defaultHealth);
            final double defaultAttSpd = Config.Setting.ADV_NEW_ATTACK_SPEED.getDouble();
            final AttributeInstance playerAttSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
            playerAttSpeed.setBaseValue(defaultAttSpd);
            player.saveData();
        });
    }

    /**
     * Load 1.8 Attack Speed and Custom Stats on startup
     * Potentially adding Reloading Compatibility
     */
    private void loadStats() {
        if (serverVersion("1.8")) return;
        SetAttackSpeed setAttackSpeed = new SetAttackSpeed();
        ResetStats resetStats = new ResetStats();
        SetCustomHealth setCustomHealth = new SetCustomHealth();

        if (Config.Setting.OLD_PVP.getBoolean()) {
            this.getServer().getOnlinePlayers().forEach(setAttackSpeed::setAttackSpd);
        } else {
            this.getServer().getOnlinePlayers().forEach(resetStats::resetAttackSpeed);
        }

        if (Config.Setting.CUSTOM_PLAYER_HEALTH_ENABLED.getBoolean()) {
            this.getServer().getOnlinePlayers().forEach(setCustomHealth::setHealth);
        } else {
            this.getServer().getOnlinePlayers().forEach(resetStats::resetMaxHealth);
        }
    }

    /**
     * Initialize enabled Listeners
     */
    private void initialize() {
        consoleMessage(MsgType.CONSOLE_INITIALIZE.getMessage());

        final PluginManager pm = this.getServer().getPluginManager();

        if (Config.Setting.OLD_PVP.getBoolean() || Config.Setting.CUSTOM_PLAYER_HEALTH_ENABLED.getBoolean()) {
            pm.registerEvents(new AttributesSet(), this);
        }
        if (Config.Setting.OLD_WEAPON_DAMAGE.getBoolean() || Config.Setting.OLD_TOOL_DAMAGE.getBoolean() || Config.Setting.DISABLE_SWEEP_ENABLED.getBoolean()) {
            pm.registerEvents(new DamageModifiers(), this);
        }
        if (Config.Setting.DISABLE_ARROW_BOOST.getBoolean()) {
            pm.registerEvents(new BowBoost(), this);
        }
        if (Config.Setting.OLD_REGEN.getBoolean()) {
            pm.registerEvents(new PlayerRegen(this), this);
        }
        if (Config.Setting.DISABLED_ITEMS_ENABLED.getBoolean()) {
            pm.registerEvents(new DisabledItems(), this);
        }
        if (Config.Setting.DISABLE_OFFHAND_ENABLED.getBoolean()) {
            pm.registerEvents(new Offhand(), this);
        }
        if (Config.Setting.FIX_PROJECTILES.getBoolean()) {
            pm.registerEvents(new Projectiles(), this);
        }
        if (Config.Setting.COOLDOWN_GOLDEN_APPLE_ENABLED.getBoolean()) {
            pm.registerEvents(new GoldenApple(this), this);
        }
        if (Config.Setting.COOLDOWN_ENCHANTED_APPLE_ENABLED.getBoolean()) {
            pm.registerEvents(new EnchantedGoldenApple(this), this);
        }
        if (Config.Setting.ENDERPEARL_ENABLED.getBoolean()) {
            pm.registerEvents(new Enderpearl(this), this);
        }
        if (Config.Setting.ENCHANTED_APPLE_CRAFTING.getBoolean()) {
            try {
                this.getServer().addRecipe(new CustomRecipes(this).enchantedGoldenAppleRecipe());
            } catch (Exception ignored) {
            }
        }
        if (Config.Setting.FISHING_ROD_ENABLED.getBoolean()) {
            pm.registerEvents(new FishingRodKnockback(), this);
        }
        if (Config.Setting.SWORD_BLOCKING_ENABLED.getBoolean()) {
            pm.registerEvents(new Blocking(), this);
        }
        if (Config.Setting.HEALTHBAR_ENABLED.getBoolean()) {
            pm.registerEvents(new HealthBar(), this);
        }

        if (Config.Setting.COMBATLOG_ENABLED.getBoolean()) {

            new CombatLog().runTaskTimerAsynchronously(this, 20, 20);

            pm.registerEvents(new CombatListener(), this);
            pm.registerEvents(new DisconnectListener(), this);

            if (Config.Setting.COMBATLOG_COMMANDS_ENABLED.getBoolean()) {
                pm.registerEvents(new CommandListener(), this);
            }
            if (Config.Setting.COMBATLOG_PREVENT_DROPPING_ITEMS.getBoolean()) {
                pm.registerEvents(new ItemDropListener(), this);
            }
            if (Config.Setting.COMBATLOG_PREVENT_PICKING_ITEMS.getBoolean()) {
                pm.registerEvents(new ItemPickListener(), this);
            }
            if (Config.Setting.COMBATLOG_PREVENT_TELEPORTATIONS.getBoolean()) {
                pm.registerEvents(new TeleportListener(), this);
            }
        }
        //GUI Listener (Do not remove this, idiot nik)
        pm.registerEvents(new GuiListener(), this);
    }

    /**
     * Disable unsupported features
     */
    private void checkSupported() {
        if (serverVersion("1.8")) {
            setFalse(Config.Setting.OLD_PVP.getKey());
            setFalse(Config.Setting.OLD_WEAPON_DAMAGE.getKey());
            setFalse(Config.Setting.OLD_TOOL_DAMAGE.getKey());
            setFalse(Config.Setting.OLD_SHARPNESS.getKey());
            setFalse(Config.Setting.DISABLE_SWEEP_ENABLED.getKey());
            setFalse(Config.Setting.OLD_REGEN.getKey());
            setFalse(Config.Setting.CUSTOM_PLAYER_HEALTH_ENABLED.getKey());
            setFalse(Config.Setting.DISABLE_OFFHAND_ENABLED.getKey());
            setFalse(Config.Setting.ENCHANTED_APPLE_CRAFTING.getKey());
            setFalse(Config.Setting.FISHING_ROD_ENABLED.getKey());
            setFalse(Config.Setting.COOLDOWN_GOLDEN_APPLE_ACTIONBAR.getKey());
            setFalse(Config.Setting.COOLDOWN_ENCHANTED_APPLE_ACTIONBAR.getKey());
            setFalse(Config.Setting.ENDERPEARL_ACTIONBAR.getKey());
            setFalse(Config.Setting.SWORD_BLOCKING_ENABLED.getKey());
            setFalse(Config.Setting.HEALTHBAR_ENABLED.getKey());
            setFalse(Config.Setting.COMBATLOG_ENABLED.getKey());
            getConfiguration().save();
            getConfiguration().reloadConfig();
            consoleMessage(MsgType.CONSOLE_UNSUPPORTED_VERSION.getMessage());
        } else if (serverVersion("1.9") || serverVersion("1.10")) {
            setFalse(Config.Setting.DISABLE_SWEEP_ENABLED.getKey());
            setFalse(Config.Setting.ENCHANTED_APPLE_CRAFTING.getKey());
            setFalse(Config.Setting.FISHING_ROD_ENABLED.getKey());
            setFalse(Config.Setting.DISABLE_SWEEP_ENABLED.getKey());
            setFalse(Config.Setting.COMBATLOG_ENABLED.getKey());
            getConfiguration().save();
            getConfiguration().reloadConfig();
            consoleMessage(MsgType.CONSOLE_UNSUPPORTED_VERSION.getMessage());
            consoleMessage(MsgType.CONSOLE_UNSUPPORTED_SWEEP_ATTACK.getMessage());
        } else if (serverVersion("1.11") || serverVersion("1.12")) {
            setFalse(Config.Setting.ENCHANTED_APPLE_CRAFTING.getKey());
            setFalse(Config.Setting.FISHING_ROD_ENABLED.getKey());
            setFalse(Config.Setting.COMBATLOG_ENABLED.getKey());
            getConfiguration().save();
            getConfiguration().reloadConfig();
            consoleMessage(MsgType.CONSOLE_UNSUPPORTED_VERSION.getMessage());
        }
    }

    /**
     * @param version The version to look for
     * @return Whether or not the Server's running on the specified version
     */
    public boolean serverVersion(String version) {
        return Bukkit.getVersion().contains(version);
    }

    /**
     * @param message The console message to send to the Server (ChatColor Friendly)
     */
    public void consoleMessage(String message) {
        this.getServer().getConsoleSender().sendMessage(message);
    }

    private void setFalse(String path) {
        getConfiguration().set(path, false);
    }
}