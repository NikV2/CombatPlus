package me.nik.combatplus;

import me.nik.combatplus.commands.CommandManager;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
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
import me.nik.combatplus.listeners.fixes.Projectiles;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.managers.commentedfiles.CommentedFileConfiguration;
import me.nik.combatplus.utils.ACManager;
import me.nik.combatplus.utils.CustomRecipes;
import me.nik.combatplus.utils.MiscUtils;
import me.nik.combatplus.utils.ResetStats;
import me.nik.combatplus.utils.SetAttackSpeed;
import me.nik.combatplus.utils.SetCustomHealth;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

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
        if (MiscUtils.isPlaceholderApiEnabled()) {
            new PapiHook(this).register();
        }

        //Hook AntiCheats
        new ACManager().hookMatrixAC();
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
            BukkitTask updateChecker = new UpdateChecker(this).runTaskAsynchronously(this);
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

        if (Config.Setting.OLD_PVP.getBoolean() || Config.Setting.CUSTOM_PLAYER_HEALTH_ENABLED.getBoolean()) {
            registerEvent(new AttributesSet());
        }
        if (Config.Setting.OLD_WEAPON_DAMAGE.getBoolean() || Config.Setting.OLD_TOOL_DAMAGE.getBoolean() || Config.Setting.DISABLE_SWEEP_ENABLED.getBoolean()) {
            registerEvent(new DamageModifiers());
        }
        if (Config.Setting.DISABLE_ARROW_BOOST.getBoolean()) {
            registerEvent(new BowBoost());
        }
        if (Config.Setting.OLD_REGEN.getBoolean()) {
            registerEvent(new PlayerRegen(this));
        }
        if (Config.Setting.DISABLED_ITEMS_ENABLED.getBoolean()) {
            registerEvent(new DisabledItems());
        }
        if (Config.Setting.DISABLE_OFFHAND_ENABLED.getBoolean()) {
            registerEvent(new Offhand());
        }
        if (Config.Setting.FIX_PROJECTILES.getBoolean()) {
            registerEvent(new Projectiles());
        }
        if (Config.Setting.COOLDOWN_GOLDEN_APPLE_ENABLED.getBoolean()) {
            registerEvent(new GoldenApple(this));
        }
        if (Config.Setting.COOLDOWN_ENCHANTED_APPLE_ENABLED.getBoolean()) {
            registerEvent(new EnchantedGoldenApple(this));
        }
        if (Config.Setting.ENDERPEARL_ENABLED.getBoolean()) {
            registerEvent(new Enderpearl(this));
        }
        if (Config.Setting.ENCHANTED_APPLE_CRAFTING.getBoolean()) {
            try {
                this.getServer().addRecipe(new CustomRecipes(this).enchantedGoldenAppleRecipe());
            } catch (Exception ignored) {
            }
        }
        if (Config.Setting.FISHING_ROD_ENABLED.getBoolean()) {
            registerEvent(new FishingRodKnockback());
        }
        if (Config.Setting.SWORD_BLOCKING_ENABLED.getBoolean()) {
            registerEvent(new Blocking());
        }
        if (Config.Setting.HEALTHBAR_ENABLED.getBoolean()) {
            registerEvent(new HealthBar());
        }
        //GUI Listener (Do not remove this, idiot nik)
        registerEvent(new GuiListener());
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
            getConfiguration().save();
            getConfiguration().reloadConfig();
            consoleMessage(MsgType.CONSOLE_UNSUPPORTED_VERSION.getMessage());
        } else if (serverVersion("1.9") || serverVersion("1.10")) {
            setFalse(Config.Setting.DISABLE_SWEEP_ENABLED.getKey());
            setFalse(Config.Setting.ENCHANTED_APPLE_CRAFTING.getKey());
            setFalse(Config.Setting.FISHING_ROD_ENABLED.getKey());
            getConfiguration().save();
            getConfiguration().reloadConfig();
            consoleMessage(MsgType.CONSOLE_UNSUPPORTED_VERSION.getMessage());
            consoleMessage(MsgType.CONSOLE_UNSUPPORTED_SWEEP_ATTACK.getMessage());
        } else if (serverVersion("1.11") || serverVersion("1.12")) {
            setFalse(Config.Setting.ENCHANTED_APPLE_CRAFTING.getKey());
            setFalse(Config.Setting.FISHING_ROD_ENABLED.getKey());
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
     * Registers the specified Listener
     *
     * @param listener The listener to register
     */
    public void registerEvent(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, this);
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