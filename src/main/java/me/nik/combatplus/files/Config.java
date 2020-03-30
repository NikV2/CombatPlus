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
            } catch (IOException e) {
                //Does not exist
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
        } catch (IOException e) {
            //Cannot save file
        }
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void addDefaults() {
        //config.yml
        //string lists
        List<String> combatlist = Config.get().getStringList("combat.settings.disabled_worlds");
        combatlist.add("world_nether");
        combatlist.add("world_the_end");
        List<String> gapplelist = Config.get().getStringList("general.settings.golden_apple_cooldown.disabled_worlds");
        gapplelist.add("world_nether");
        gapplelist.add("world_the_end");
        //actual config
        List<String> itemslist = Config.get().getStringList("general.settings.disabled_items.items");
        itemslist.add("item_frame");
        itemslist.add("armor_stand");
        List<String> disableditemsworlds = Config.get().getStringList("general.settings.disabled_items.disabled_worlds");
        disableditemsworlds.add("world_nether");
        disableditemsworlds.add("world_the_end");
        List<String> offhandworlds = Config.get().getStringList("general.settings.disable_offhand.disabled_worlds");
        offhandworlds.add("world_nether");
        offhandworlds.add("world_the_end");
        Config.get().options().header("+----------------------------------------------------------------------------------------------+" + "\n" + "|                                                                                              |" + "\n" + "|                                           Combat Plus                                        |" + "\n" + "|                                                                                              |" + "\n" + "|                               Discord: https://discord.gg/m7j2Y9H                            |" + "\n" + "|                                                                                              |" + "\n" + "|                                           Author: Nik                                        |" + "\n" + "|                                                                                              |" + "\n" + "+----------------------------------------------------------------------------------------------+" + "\n");
        Config.get().addDefault("settings.check_for_updates", true);
        Config.get().addDefault("settings.async", true);
        Config.get().addDefault("settings.developer_mode", false);
        Config.get().addDefault("combat.settings.old_pvp", true);
        Config.get().addDefault("combat.settings.old_weapon_damage", true);
        Config.get().addDefault("combat.settings.old_tool_damage", true);
        Config.get().addDefault("combat.settings.old_sharpness", true);
        Config.get().addDefault("combat.settings.disable_sweep_attacks", true);
        Config.get().addDefault("combat.settings.disable_arrow_boost", true);
        Config.get().addDefault("combat.settings.old_player_regen", true);
        Config.get().addDefault("combat.settings.disabled_worlds", combatlist);
        Config.get().addDefault("general.settings.golden_apple_cooldown.enabled", true);
        Config.get().addDefault("general.settings.golden_apple_cooldown.cooldown", 10);
        Config.get().addDefault("general.settings.golden_apple_cooldown.disabled_worlds", gapplelist);
        Config.get().addDefault("general.settings.disabled_items.enabled", false);
        Config.get().addDefault("general.settings.disabled_items.items", itemslist);
        Config.get().addDefault("general.settings.disabled_items.disabled_worlds", disableditemsworlds);
        Config.get().addDefault("general.settings.disable_item_frame_rotation.enabled", true);
        Config.get().addDefault("general.settings.disable_offhand.enabled", true);
        Config.get().addDefault("general.settings.disable_offhand.disabled_worlds", offhandworlds);
        Config.get().addDefault("general.settings.fixes.projectile_fixer", true);
        Config.get().addDefault("advanced.settings.old_pvp.attack_speed", 24);
        Config.get().addDefault("advanced.settings.new_pvp.attack_speed", 4);
        Config.get().addDefault("advanced.settings.modifiers.old_swords_damage", 1);
        Config.get().addDefault("advanced.settings.modifiers.old_shovels_damage", -0.5);
        Config.get().addDefault("advanced.settings.modifiers.old_axes_damage", -2);
        Config.get().addDefault("advanced.settings.modifiers.old_pickaxes_damage", 1);
        Config.get().addDefault("advanced.settings.old_regen.frequency", 3);
        Config.get().addDefault("advanced.settings.old_regen.amount", 1);
        Config.get().addDefault("advanced.settings.old_regen.exhaustion", 3);
    }
}