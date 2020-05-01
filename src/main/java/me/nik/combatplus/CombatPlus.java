package me.nik.combatplus;

import me.nik.combatplus.commands.CommandManager;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.handlers.UpdateChecker;
import me.nik.combatplus.listeners.AttributesSet;
import me.nik.combatplus.listeners.BowBoost;
import me.nik.combatplus.listeners.DamageModifiers;
import me.nik.combatplus.listeners.DisabledItems;
import me.nik.combatplus.listeners.EnchantedGoldenApple;
import me.nik.combatplus.listeners.Enderpearl;
import me.nik.combatplus.listeners.GUIListener;
import me.nik.combatplus.listeners.GoldenApple;
import me.nik.combatplus.listeners.ItemFrameRotate;
import me.nik.combatplus.listeners.Offhand;
import me.nik.combatplus.listeners.PlayerRegen;
import me.nik.combatplus.listeners.UpdateReminder;
import me.nik.combatplus.listeners.fixes.Criticals;
import me.nik.combatplus.listeners.fixes.HealthSpoof;
import me.nik.combatplus.listeners.fixes.KillAura;
import me.nik.combatplus.listeners.fixes.Projectiles;
import me.nik.combatplus.utils.CustomRecipes;
import me.nik.combatplus.utils.Messenger;
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

    @Override
    public void onEnable() {
        instance = this;
        //Load Files
        loadFiles();

        //Startup Message
        System.out.println();
        System.out.println("          " + ChatColor.RED + "CombatPlus " + ChatColor.UNDERLINE + "v" + this.getDescription().getVersion());
        System.out.println();
        System.out.println("             " + ChatColor.BOLD + "Author: " + ChatColor.UNDERLINE + "Nik");
        System.out.println();

        //Unsupported Version Checker
        checkSupported();

        //Load Commands
        getCommand("combatplus").setExecutor(new CommandManager());

        //Load Listeners
        initialize();

        //Load Player Stats to allow reloading availability
        loadStats();

        //Check for Updates
        checkForUpdates();

        //Load bStats
        int pluginID = 6982;
        MetricsLite metricsLite = new MetricsLite(this, pluginID);
    }
    @Override
    public void onDisable() {
        //Load Default Stats to avoid server damage
        setDefaultStats();

        //Reload Files
        Config.reload();
        Config.save();
        Lang.reload();
        Lang.save();

        //Unload Instances
        instance = null;
        this.getCommand("combatplus").setExecutor(null);

        //Done
        System.out.println(Messenger.message("console.disabled"));
    }

    public static CombatPlus getInstance() {
        return instance;
    }

    private void loadFiles() {
        Config.setup();
        Config.addDefaults();
        Config.get().options().copyDefaults(true);
        Config.save();
        Lang.setup();
        Lang.addDefaults();
        Lang.get().options().copyDefaults(true);
        Lang.save();
    }

    private void checkForUpdates() {
        if (isEnabled("settings.check_for_updates")) {
            BukkitTask updateChecker = new UpdateChecker(this).runTaskAsynchronously(this);
            registerEvent(new UpdateReminder());
        } else {
            System.out.println(Messenger.message("console.update_disabled"));
        }
    }

    private void setDefaultStats() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final double defaultHealth = Config.get().getDouble("advanced.settings.base_player_health");
            final AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            playerMaxHealth.setBaseValue(defaultHealth);
            if (!serverVersion("1.8")) {
                final double defaultAttSpd = Config.get().getDouble("advanced.settings.new_pvp.attack_speed");
                final AttributeInstance playerAttSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
                playerAttSpeed.setBaseValue(defaultAttSpd);
            }
            player.saveData();
        });
    }

    private void loadStats() {
        if (isEnabled("combat.settings.old_pvp")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                new SetAttackSpeed(this).setAttackSpd(player);
            });
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> {
                new ResetStats(this).resetAttackSpeed(player);
            });
        }

        if (isEnabled("custom.player_health.enabled")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                new SetCustomHealth(this).setHealth(player);
            });
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> {
                new ResetStats(this).resetMaxHealth(player);
            });
        }
    }

    private void initialize() {
        System.out.println(Messenger.message("console.initialize"));
        if (isEnabled("combat.settings.old_pvp") || isEnabled("custom.player_health.enabled")) {
            registerEvent(new AttributesSet(this));
        }
        if (isEnabled("combat.settings.old_weapon_damage") || isEnabled("combat.settings.old_tool_damage") || isEnabled("combat.settings.disable_sweep_attacks.enabled")) {
            registerEvent(new DamageModifiers(this));
        }
        if (isEnabled("combat.settings.disable_arrow_boost")) {
            registerEvent(new BowBoost(this));
        }
        if (isEnabled("combat.settings.old_player_regen")) {
            registerEvent(new PlayerRegen(this));
        }
        if (isEnabled("disabled_items.enabled")) {
            registerEvent(new DisabledItems(this));
        }
        if (isEnabled("disable_item_frame_rotation.enabled")) {
            registerEvent(new ItemFrameRotate(this));
        }
        if (isEnabled("disable_offhand.enabled")) {
            registerEvent(new Offhand(this));
        }
        if (isEnabled("fixes.projectile_fixer")) {
            registerEvent(new Projectiles(this));
        }
        if (isEnabled("fixes.invalid_criticals")) {
            registerEvent(new Criticals(this));
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
            Bukkit.addRecipe(new CustomRecipes(this).enchantedGoldenAppleRecipe());
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
            Config.get().set("golden_apple_cooldown.golden_apple.enabled", false);
            Config.get().set("golden_apple_cooldown.enchanted_golden_apple.enabled", false);
            Config.get().set("fixes.kill_aura", false);
            Config.get().set("enderpearl_cooldown.enabled", false);
            Config.get().set("disable_offhand.enabled", false);
            Config.get().set("recipes.enchanted_golden_apple", false);
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_version"));
        } else if (serverVersion("1.9")) {
            Config.get().set("combat.settings.disable_sweep_attacks.enabled", false);
            Config.get().set("golden_apple_cooldown.enchanted_golden_apple.enabled", false);
            Config.get().set("golden_apple_cooldown.golden_apple.enabled", false);
            Config.get().set("fixes.kill_aura", false);
            Config.get().set("recipes.enchanted_golden_apple", false);
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_version"));
            System.out.println(Messenger.message("console.unsupported_sweep_attack"));
        } else if (serverVersion("1.10")) {
            Config.get().set("combat.settings.disable_sweep_attacks.enabled", false);
            Config.get().set("golden_apple_cooldown.enchanted_golden_apple.enabled", false);
            Config.get().set("golden_apple_cooldown.golden_apple.enabled", false);
            Config.get().set("recipes.enchanted_golden_apple", false);
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_version"));
            System.out.println(Messenger.message("console.unsupported_sweep_attack"));
        } else if (serverVersion("1.11")) {
            Config.get().set("golden_apple_cooldown.enchanted_golden_apple.enabled", false);
            Config.get().set("golden_apple_cooldown.golden_apple.enabled", false);
            Config.get().set("recipes.enchanted_golden_apple", false);
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_version"));
        } else if (serverVersion("1.12")) {
            Config.get().set("golden_apple_cooldown.enchanted_golden_apple.enabled", false);
            Config.get().set("golden_apple_cooldown.golden_apple.enabled", false);
            Config.get().set("recipes.enchanted_golden_apple", false);
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_version"));
        }
    }

    private boolean serverVersion(String version) {
        return Bukkit.getVersion().contains(version);
    }

    private void registerEvent(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, this);
    }

    private boolean isEnabled(String path) {
        return Config.get().getBoolean(path);
    }
}
