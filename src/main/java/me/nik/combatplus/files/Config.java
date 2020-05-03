package me.nik.combatplus.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Config {
    private static File file;
    private static FileConfiguration config;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("CombatPlus").getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void save() {
        try {
            config.save(file);
        } catch (IOException ignored) {
        }
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void addDefaults() {
        //config.yml
        //string lists
        List<String> combatList = Config.get().getStringList("combat.settings.disabled_worlds");
        combatList.add("example_world");
        List<String> gappleList = Config.get().getStringList("general.settings.golden_apple_cooldown.disabled_worlds");
        gappleList.add("example_world");
        List<String> itemsList = Config.get().getStringList("general.settings.disabled_items.items");
        itemsList.add("item_frame");
        itemsList.add("armor_stand");
        List<String> offhandWorlds = Config.get().getStringList("general.settings.disable_offhand.disabled_worlds");
        offhandWorlds.add("example_world");
        List<String> craftingWorlds = Config.get().getStringList("disabled_items.disabled_worlds");
        craftingWorlds.add("example_world");
        List<String> itemFrameRotation = Config.get().getStringList("disable_item_frame_rotation.disabled_worlds");
        itemFrameRotation.add("example_world");
        List<String> epearlList = Config.get().getStringList("enderpearl_cooldown.disabled_worlds");
        epearlList.add("example_world");
        List<String> customStats = Config.get().getStringList("custom.disabled_worlds");
        customStats.add("example_world");
        Config.get().options().header("+----------------------------------------------------------------------------------------------+" + "\n" + "|                                                                                              |" + "\n" + "|                                           Combat Plus                                        |" + "\n" + "|                                                                                              |" + "\n" + "|                               Discord: https://discord.gg/m7j2Y9H                            |" + "\n" + "|                                                                                              |" + "\n" + "|                                           Author: Nik                                        |" + "\n" + "|                                                                                              |" + "\n" + "+----------------------------------------------------------------------------------------------+" + "\n");
        Config.get().addDefault("settings.check_for_updates", true);
        Config.get().addDefault("settings.async", false);
        Config.get().addDefault("settings.developer_mode", false);
        Config.get().addDefault("combat.settings.old_pvp", true);
        Config.get().addDefault("combat.settings.old_weapon_damage", true);
        Config.get().addDefault("combat.settings.old_tool_damage", true);
        Config.get().addDefault("combat.settings.old_sharpness", true);
        Config.get().addDefault("combat.settings.disable_sweep_attacks.enabled", true);
        Config.get().addDefault("combat.settings.disable_sweep_attacks.ignore_sweeping_edge", false);
        Config.get().addDefault("combat.settings.disable_arrow_boost", true);
        Config.get().addDefault("combat.settings.old_player_regen", true);
        Config.get().addDefault("combat.settings.disabled_worlds", combatList);
        Config.get().addDefault("golden_apple_cooldown.golden_apple.enabled", true);
        Config.get().addDefault("golden_apple_cooldown.golden_apple.cooldown", 10);
        Config.get().addDefault("golden_apple_cooldown.golden_apple.actionbar", true);
        Config.get().addDefault("golden_apple_cooldown.enchanted_golden_apple.enabled", true);
        Config.get().addDefault("golden_apple_cooldown.enchanted_golden_apple.cooldown", 20);
        Config.get().addDefault("golden_apple_cooldown.enchanted_golden_apple.actionbar", true);
        Config.get().addDefault("golden_apple_cooldown.disabled_worlds", gappleList);
        Config.get().addDefault("enderpearl_cooldown.enabled", true);
        Config.get().addDefault("enderpearl_cooldown.cooldown", 10);
        Config.get().addDefault("enderpearl_cooldown.actionbar", true);
        Config.get().addDefault("enderpearl_cooldown.disabled_worlds", epearlList);
        Config.get().addDefault("disabled_items.enabled", false);
        Config.get().addDefault("disabled_items.items", itemsList);
        Config.get().addDefault("disabled_items.disabled_worlds", craftingWorlds);
        Config.get().addDefault("disable_item_frame_rotation.enabled", true);
        Config.get().addDefault("disable_item_frame_rotation.disabled_worlds", itemFrameRotation);
        Config.get().addDefault("disable_offhand.enabled", true);
        Config.get().addDefault("disable_offhand.disabled_worlds", offhandWorlds);
        Config.get().addDefault("recipes.enchanted_golden_apple", false);
        Config.get().addDefault("fixes.kill_aura", false);
        Config.get().addDefault("fixes.projectile_fixer", true);
        Config.get().addDefault("fixes.invalid_criticals", true);
        Config.get().addDefault("fixes.health_spoof", true);
        Config.get().addDefault("custom.player_health.enabled", false);
        Config.get().addDefault("custom.player_health.max_health", 20);
        Config.get().addDefault("custom.disabled_worlds", customStats);
        Config.get().addDefault("advanced.settings.old_pvp.attack_speed", 24);
        Config.get().addDefault("advanced.settings.new_pvp.attack_speed", 4);
        Config.get().addDefault("advanced.settings.modifiers.old_swords_damage", 1);
        Config.get().addDefault("advanced.settings.modifiers.old_shovels_damage", -0.5);
        Config.get().addDefault("advanced.settings.modifiers.old_axes_damage", -2);
        Config.get().addDefault("advanced.settings.modifiers.old_pickaxes_damage", 1);
        Config.get().addDefault("advanced.settings.old_regen.frequency", 3);
        Config.get().addDefault("advanced.settings.old_regen.amount", 1);
        Config.get().addDefault("advanced.settings.old_regen.exhaustion", 3);
        Config.get().addDefault("advanced.settings.base_player_health", 20);
        Config.get().addDefault("advanced.settings.kill_aura.entity", "BAT");
        Config.get().addDefault("advanced.settings.kill_aura.teleport_ticks", 1);
        Config.get().addDefault("advanced.settings.kill_aura.ticks_duration", 300);
        Config.get().addDefault("advanced.settings.kill_aura.ignore_creative", true);
        Config.get().addDefault("advanced.settings.kill_aura.range", -2.5);
    }
}