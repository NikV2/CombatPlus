package me.nik.combatplus;

import me.nik.combatplus.api.events.CombatPlusLoadEvent;
import me.nik.combatplus.commands.CommandManager;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.files.commentedfiles.CommentedFileConfiguration;
import me.nik.combatplus.listeners.GuiListener;
import me.nik.combatplus.managers.PapiHook;
import me.nik.combatplus.managers.UpdateChecker;
import me.nik.combatplus.metrics.MetricsLite;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.modules.impl.Blocking;
import me.nik.combatplus.modules.impl.CombatLog;
import me.nik.combatplus.modules.impl.CustomAttackSpeed;
import me.nik.combatplus.modules.impl.CustomHealth;
import me.nik.combatplus.modules.impl.CustomPlayerKnockback;
import me.nik.combatplus.modules.impl.CustomPlayerRegeneration;
import me.nik.combatplus.modules.impl.DamageModifiers;
import me.nik.combatplus.modules.impl.DisableBowBoost;
import me.nik.combatplus.modules.impl.DisableOffhand;
import me.nik.combatplus.modules.impl.EnchantedGoldenAppleCooldown;
import me.nik.combatplus.modules.impl.EnderpearlCooldown;
import me.nik.combatplus.modules.impl.FishingRodKnockback;
import me.nik.combatplus.modules.impl.GoldenAppleCooldown;
import me.nik.combatplus.modules.impl.HealthBar;
import me.nik.combatplus.modules.impl.HideToolFlags;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CombatPlus extends JavaPlugin {

    private static CombatPlus instance;

    private final Config config = new Config(this);

    private final Lang lang = new Lang();

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

        //Load Files
        this.config.setup();
        this.lang.setup(this);
        this.lang.addDefaults();
        this.lang.get().options().copyDefaults(true);
        this.lang.save();

        this.getServer().getConsoleSender().sendMessage(STARTUP_MESSAGE);

        //Load Commands
        getCommand("combatplus").setExecutor(new CommandManager(this));

        //Load Modules
        this.modules.clear();

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
                new Blocking(),
                new HideToolFlags(),
                new CustomPlayerKnockback()
        ));

        this.modules.forEach(Module::load);

        //GUI Listener (Do not remove this, idiot nik)
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);

        //Check for Updates
        if (Config.Setting.CHECK_FOR_UPDATES.getBoolean()) {
            new UpdateChecker(this).runTaskAsynchronously(this);
        }

        //Load bStats
        new MetricsLite(this, 6982);

        //Hook PlaceholderAPI
        if (this.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PapiHook(this).register();
        }

        Bukkit.getPluginManager().callEvent(new CombatPlusLoadEvent());
    }

    public Module getModule(Class<? extends Module> clazz) {
        return this.modules.stream().filter(module -> module.getClass().equals(clazz)).findFirst().orElse(null);
    }

    public CommentedFileConfiguration getConfiguration() {
        return config.getConfig();
    }

    public Lang getLang() {
        return lang;
    }
}