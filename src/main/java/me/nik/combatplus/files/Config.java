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
        List<String> combatlist = Config.get().getStringList("combat.settings.disabled_worlds");
        combatlist.add("example_world");
        List<String> gapplelist = Config.get().getStringList("general.settings.golden_apple_cooldown.disabled_worlds");
        gapplelist.add("example_world");
        //actual config
        List<String> itemslist = Config.get().getStringList("general.settings.disabled_items.items");
        itemslist.add("item_frame");
        itemslist.add("armor_stand");
        List<String> offhandworlds = Config.get().getStringList("general.settings.disable_offhand.disabled_worlds");
        offhandworlds.add("example_world");
        List<String> craftingworlds = Config.get().getStringList("disabled_items.disabled_worlds");
        craftingworlds.add("example_world");
        List<String> itemframerotation = Config.get().getStringList("disable_item_frame_rotation.disabled_worlds");
        itemframerotation.add("example_world");
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
        Config.get().addDefault("combat.settings.disabled_worlds", combatlist);
        Config.get().addDefault("golden_apple_cooldown.golden_apple.enabled", true);
        Config.get().addDefault("golden_apple_cooldown.golden_apple.cooldown", 10);
        Config.get().addDefault("golden_apple_cooldown.enchanted_golden_apple.enabled", true);
        Config.get().addDefault("golden_apple_cooldown.enchanted_golden_apple.cooldown", 20);
        Config.get().addDefault("golden_apple_cooldown.disabled_worlds", gapplelist);
        Config.get().addDefault("enderpearl_cooldown.enabled", true);
        Config.get().addDefault("enderpearl_cooldown.cooldown", 10);
        Config.get().addDefault("disabled_items.enabled", false);
        Config.get().addDefault("disabled_items.items", itemslist);
        Config.get().addDefault("disabled_items.disabled_worlds", craftingworlds);
        Config.get().addDefault("disable_item_frame_rotation.enabled", true);
        Config.get().addDefault("disable_item_frame_rotation.disabled_worlds", itemframerotation);
        Config.get().addDefault("disable_offhand.enabled", true);
        Config.get().addDefault("disable_offhand.disabled_worlds", offhandworlds);
        Config.get().addDefault("fixes.projectile_fixer", true);
        Config.get().addDefault("fixes.invalid_criticals", true);
        Config.get().addDefault("fixes.health_spoof", true);
        Config.get().addDefault("custom.player_health.enabled", false);
        Config.get().addDefault("custom.player_health.max_health", 20);
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
    }
}