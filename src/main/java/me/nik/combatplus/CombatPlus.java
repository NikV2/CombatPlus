package me.nik.combatplus;

import me.nik.combatplus.commands.CommandManager;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.handlers.PapiHook;
import me.nik.combatplus.handlers.UpdateChecker;
import me.nik.combatplus.listeners.AttributesSet;
import me.nik.combatplus.listeners.BowBoost;
import me.nik.combatplus.listeners.DamageModifiers;
import me.nik.combatplus.listeners.DisabledItems;
import me.nik.combatplus.listeners.EnchantedGoldenApple;
import me.nik.combatplus.listeners.Enderpearl;
import me.nik.combatplus.listeners.FishingRodKnockback;
import me.nik.combatplus.listeners.GUIListener;
import me.nik.combatplus.listeners.GoldenApple;
import me.nik.combatplus.listeners.ItemFrameRotate;
import me.nik.combatplus.listeners.Offhand;
import me.nik.combatplus.listeners.PlayerRegen;
import me.nik.combatplus.listeners.fixes.BadPackets;
import me.nik.combatplus.listeners.fixes.Criticals;
import me.nik.combatplus.listeners.fixes.HealthSpoof;
import me.nik.combatplus.listeners.fixes.KillAura;
import me.nik.combatplus.listeners.fixes.NoFall;
import me.nik.combatplus.listeners.fixes.Projectiles;
import me.nik.combatplus.listeners.fixes.Speed;
import me.nik.combatplus.utils.ACManager;
import me.nik.combatplus.utils.CustomRecipes;
import me.nik.combatplus.utils.Messenger;
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

    public Config config;
    public Lang lang;

    @Override
    public void onEnable() {
        this.lang = new Lang();
        this.config = new Config();

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
        MetricsLite metricsLite = new MetricsLite(this, 6982);

        //Hook PlaceholderAPI
        if (MiscUtils.isPlaceholderApiEnabled()) {
            new PapiHook(this).register();
        }

        //Hook AntiCheat
        new ACManager().hookMatrixAC();
    }
    @Override
    public void onDisable() {
        //Load Default Stats to avoid server damage
        setDefaultStats();

        //Reload Files
        config.reload();
        config.save();
        lang.reload();
        lang.save();

        //Done
        consoleMessage(Messenger.message("console.disabled"));
    }

    private void loadFiles() {
        config.setup();
        config.addDefaults();
        Config.get().options().copyDefaults(true);
        config.save();
        lang.setup();
        lang.addDefaults();
        Lang.get().options().copyDefaults(true);
        lang.save();
    }

    private void checkForUpdates() {
        if (isEnabled("settings.check_for_updates")) {
            BukkitTask updateChecker = new UpdateChecker(this).runTaskAsynchronously(this);
        } else {
            consoleMessage(Messenger.message("console.update_disabled"));
        }
    }

    private void setDefaultStats() {
        if (serverVersion("1.8")) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            final double defaultHealth = Config.get().getDouble("advanced.settings.base_player_health");
            final AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            playerMaxHealth.setBaseValue(defaultHealth);
            final double defaultAttSpd = Config.get().getDouble("advanced.settings.new_pvp.attack_speed");
            final AttributeInstance playerAttSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
            playerAttSpeed.setBaseValue(defaultAttSpd);
            player.saveData();
        });
    }

    private void loadStats() {
        if (serverVersion("1.8")) return;
        SetAttackSpeed setAttackSpeed = new SetAttackSpeed();
        ResetStats resetStats = new ResetStats();
        SetCustomHealth setCustomHealth = new SetCustomHealth();

        if (isEnabled("combat.settings.old_pvp")) {
            Bukkit.getOnlinePlayers().forEach(setAttackSpeed::setAttackSpd);
        } else {
            Bukkit.getOnlinePlayers().forEach(resetStats::resetAttackSpeed);
        }

        if (isEnabled("custom.player_health.enabled")) {
            Bukkit.getOnlinePlayers().forEach(setCustomHealth::setHealth);
        } else {
            Bukkit.getOnlinePlayers().forEach(resetStats::resetMaxHealth);
        }
    }

    private void initialize() {
        consoleMessage(Messenger.message("console.initialize"));

        if (isEnabled("combat.settings.old_pvp") || isEnabled("custom.player_health.enabled")) {
            registerEvent(new AttributesSet());
        }
        if (isEnabled("combat.settings.old_weapon_damage") || isEnabled("combat.settings.old_tool_damage") || isEnabled("combat.settings.disable_sweep_attacks.enabled")) {
            registerEvent(new DamageModifiers());
        }
        if (isEnabled("combat.settings.disable_arrow_boost")) {
            registerEvent(new BowBoost());
        }
        if (isEnabled("combat.settings.old_player_regen")) {
            registerEvent(new PlayerRegen(this));
        }
        if (isEnabled("disabled_items.enabled")) {
            registerEvent(new DisabledItems());
        }
        if (isEnabled("disable_item_frame_rotation.enabled")) {
            registerEvent(new ItemFrameRotate());
        }
        if (isEnabled("disable_offhand.enabled")) {
            registerEvent(new Offhand());
        }
        if (isEnabled("fixes.projectile_fixer")) {
            registerEvent(new Projectiles());
        }
        if (isEnabled("fixes.invalid_criticals")) {
            registerEvent(new Criticals());
        }
        if (isEnabled("fixes.health_spoof")) {
            registerEvent(new HealthSpoof(this));
        }
        if (isEnabled("fixes.kill_aura")) {
            registerEvent(new KillAura(this));
        }
        if (isEnabled("golden_apple_cooldown.golden_apple.enabled")) {
            registerEvent(new GoldenApple(this));
        }
        if (isEnabled("golden_apple_cooldown.enchanted_golden_apple.enabled")) {
            registerEvent(new EnchantedGoldenApple(this));
        }
        if (isEnabled("enderpearl_cooldown.enabled")) {
            registerEvent(new Enderpearl(this));
        }
        if (isEnabled("recipes.enchanted_golden_apple")) {
            try {
                this.getServer().addRecipe(new CustomRecipes(this).enchantedGoldenAppleRecipe());
            } catch (Exception ignored) {
            }
        }
        if (isEnabled("fixes.no_fall")) {
            registerEvent(new NoFall());
        }
        if (isEnabled("fixes.speed")) {
            registerEvent(new Speed());
        }
        if (isEnabled("fixes.bad_packets")) {
            registerEvent(new BadPackets());
        }
        if (isEnabled("knockback.fishing_rod.enabled")) {
            registerEvent(new FishingRodKnockback());
        }
        //GUI Listener (Do not remove this, idiot nik)
        registerEvent(new GUIListener(this));
    }

    private void checkSupported() {
        if (serverVersion("1.8")) {
            Config.get().set("combat.settings.old_pvp", false);
            Config.get().set("combat.settings.old_weapon_damage", false);
            Config.get().set("combat.settings.old_tool_damage", false);
            Config.get().set("combat.settings.old_sharpness", false);
            Config.get().set("combat.settings.disable_sweep_attacks.enabled", false);
            Config.get().set("combat.settings.old_player_regen", false);
            Config.get().set("custom.player_health.enabled", false);
            Config.get().set("fixes.kill_aura", false);
            Config.get().set("disable_offhand.enabled", false);
            Config.get().set("recipes.enchanted_golden_apple", false);
            Config.get().set("knockback.fishing_rod.enabled", false);
            Config.get().set("golden_apple_cooldown.golden_apple.actionbar", false);
            Config.get().set("golden_apple_cooldown.enchanted_golden_apple.actionbar", false);
            Config.get().set("enderpearl_cooldown.actionbar", false);
            config.save();
            config.reload();
            consoleMessage(Messenger.message("console.unsupported_version"));
        } else if (serverVersion("1.9")) {
            Config.get().set("combat.settings.disable_sweep_attacks.enabled", false);
            Config.get().set("recipes.enchanted_golden_apple", false);
            Config.get().set("knockback.fishing_rod.enabled", false);
            config.save();
            config.reload();
            consoleMessage(Messenger.message("console.unsupported_version"));
            consoleMessage(Messenger.message("console.unsupported_sweep_attack"));
        } else if (serverVersion("1.10")) {
            Config.get().set("combat.settings.disable_sweep_attacks.enabled", false);
            Config.get().set("recipes.enchanted_golden_apple", false);
            Config.get().set("knockback.fishing_rod.enabled", false);
            config.save();
            config.reload();
            consoleMessage(Messenger.message("console.unsupported_version"));
            consoleMessage(Messenger.message("console.unsupported_sweep_attack"));
        } else if (serverVersion("1.11")) {
            Config.get().set("recipes.enchanted_golden_apple", false);
            Config.get().set("knockback.fishing_rod.enabled", false);
            config.save();
            config.reload();
            consoleMessage(Messenger.message("console.unsupported_version"));
        } else if (serverVersion("1.12")) {
            Config.get().set("recipes.enchanted_golden_apple", false);
            Config.get().set("knockback.fishing_rod.enabled", false);
            config.save();
            config.reload();
            consoleMessage(Messenger.message("console.unsupported_version"));
        }
    }

    public boolean serverVersion(String version) {
        return Bukkit.getVersion().contains(version);
    }

    public void registerEvent(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, this);
    }

    private boolean isEnabled(String path) {
        return Config.get().getBoolean(path);
    }

    public void consoleMessage(String message) {
        this.getServer().getConsoleSender().sendMessage(message);
    }
}
