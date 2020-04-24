package me.nik.combatplus;

import me.nik.combatplus.commands.CommandManager;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.handlers.UpdateChecker;
import me.nik.combatplus.listeners.*;
import me.nik.combatplus.listeners.fixes.Criticals;
import me.nik.combatplus.listeners.fixes.Projectiles;
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
        if (isEnabled("settings.check_for_updates")) {
            BukkitTask updateChecker = new UpdateChecker(this).runTaskAsynchronously(this);
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
        Bukkit.getOnlinePlayers().forEach(player -> {
            final double defaultAttSpd = Config.get().getDouble("advanced.settings.new_pvp.attack_speed");
            final double defaultHealth = Config.get().getDouble("advanced.settings.base_player_health");
            final AttributeInstance playerAttSpeed = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
            final AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            playerAttSpeed.setBaseValue(defaultAttSpd);
            playerMaxHealth.setBaseValue(defaultHealth);
            player.saveData();
        });

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

    private void loadStats() {
        if (isEnabled("combat.settings.old_pvp")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                new SetAttackSpeed().setAttackSpd(player);
            });
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> {
                new ResetStats().resetAttackSpeed(player);
            });
        }

        if (isEnabled("custom.player_health.enabled")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                new SetCustomHealth().setHealth(player);
            });
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> {
                new ResetStats().resetMaxHealth(player);
            });
        }
    }

    private void initialize() {
        if (isEnabled("combat.settings.old_pvp") || isEnabled("custom.player_health.enabled")) {
            registerEvent(new AttributesSet());
            System.out.println(Messenger.message("console.attribute_modifiers_on"));
        } else {
            System.out.println(Messenger.message("console.attribute_modifiers_off"));
        }
        if (isEnabled("combat.settings.old_weapon_damage") || isEnabled("combat.settings.old_tool_damage") || isEnabled("combat.settings.disable_sweep_attacks.enabled")) {
            registerEvent(new DamageModifiers());
            System.out.println(Messenger.message("console.modifiers_on"));
        } else {
            System.out.println(Messenger.message("console.modifiers_off"));
        }
        if (isEnabled("combat.settings.disable_arrow_boost")) {
            registerEvent(new BowBoost());
            System.out.println(Messenger.message("console.arrow_boost_on"));
        } else {
            System.out.println(Messenger.message("console.arrow_boost_off"));
        }
        if (isEnabled("combat.settings.old_player_regen")) {
            registerEvent(new PlayerRegen());
            System.out.println(Messenger.message("console.old_regen_on"));
        } else {
            System.out.println(Messenger.message("console.old_regen_off"));
        }
        if (isEnabled("disabled_items.enabled")) {
            registerEvent(new DisabledItems());
            System.out.println(Messenger.message("console.disabled_items_on"));
        } else {
            System.out.println(Messenger.message("console.disabled_items_off"));
        }
        if (isEnabled("disable_item_frame_rotation.enabled")) {
            registerEvent(new ItemFrameRotate());
            System.out.println(Messenger.message("console.item_frame_rotation_on"));
        } else {
            System.out.println(Messenger.message("console.item_frame_rotation_off"));
        }
        if (isEnabled("disable_offhand.enabled")) {
            registerEvent(new Offhand());
            System.out.println(Messenger.message("console.disable_offhand_on"));
        } else {
            System.out.println(Messenger.message("console.disable_offhand_off"));
        }
        if (isEnabled("fixes.projectile_fixer") || isEnabled("fixes.invalid_criticals")) {
            System.out.println(Messenger.message("console.fixes_on"));
            if (isEnabled("fixes.projectile_fixer")) {
                registerEvent(new Projectiles());
            } else if (isEnabled("fixes.invalid_criticals")) {
                registerEvent(new Criticals());
            }
        } else {
            System.out.println(Messenger.message("console.fixes_off"));
        }
        if (isEnabled("golden_apple_cooldown.golden_apple.enabled")) {
            registerEvent(new GoldenApple());
            System.out.println(Messenger.message("console.golden_apple_cooldown_on"));
        } else {
            System.out.println(Messenger.message("console.golden_apple_cooldown_off"));
        }
        if (isEnabled("golden_apple_cooldown.enchanted_golden_apple.enabled")) {
            registerEvent(new EnchantedGoldenApple());
            System.out.println(Messenger.message("console.enchanted_golden_apple_cooldown_on"));
        } else {
            System.out.println(Messenger.message("console.enchanted_golden_apple_cooldown_off"));
        }
        if (isEnabled("enderpearl_cooldown.enabled")) {
            registerEvent(new Enderpearl());
            System.out.println(Messenger.message("console.enderpearl_cooldown_on"));
        } else {
            System.out.println(Messenger.message("console.enderpearl_cooldown_off"));
        }
        //GUI Listener (Do not remove this, idiot nik)
        registerEvent(new GUIListener());
    }

    private void checkSupported() {
        if (serverVersion("1.8")) {
            if (isEnabled("combat.settings.old_pvp")) {
                Config.get().set("combat.settings.old_pvp", false);
            }
            if (isEnabled("combat.settings.old_weapon_damage")) {
                Config.get().set("combat.settings.old_weapon_damage", false);
            }
            if (isEnabled("combat.settings.old_tool_damage")) {
                Config.get().set("combat.settings.old_tool_damage", false);
            }
            if (isEnabled("combat.settings.old_sharpness")) {
                Config.get().set("combat.settings.old_sharpness", false);
            }
            if (isEnabled("combat.settings.disable_sweep_attacks.enabled")) {
                Config.get().set("combat.settings.disable_sweep_attacks.enabled", false);
            }
            if (isEnabled("combat.settings.old_player_regen")) {
                Config.get().set("combat.settings.old_player_regen", false);
            }
            if (isEnabled("golden_apple_cooldown.golden_apple.enabled")) {
                Config.get().set("golden_apple_cooldown.golden_apple.enabled", false);
            }
            if (isEnabled("golden_apple_cooldown.enchanted_golden_apple.enabled")) {
                Config.get().set("golden_apple_cooldown.enchanted_golden_apple.enabled", false);
            }
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_version"));
        } else if (serverVersion("1.9") || serverVersion("1.10")) {
            Config.get().set("combat.settings.disable_sweep_attacks.enabled", false);
            Config.get().set("golden_apple_cooldown.enchanted_golden_apple.enabled", false);
            Config.get().set("golden_apple_cooldown.golden_apple.enabled", false);
            Config.save();
            Config.reload();
            System.out.println(Messenger.message("console.unsupported_version"));
            System.out.println(Messenger.message("console.unsupported_sweep_attack"));
        } else if (serverVersion("1.11") || serverVersion("1.12")) {
            Config.get().set("golden_apple_cooldown.enchanted_golden_apple.enabled", false);
            Config.get().set("golden_apple_cooldown.golden_apple.enabled", false);
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
