package me.nik.combatplus.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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
        Config.get().options().header("+----------------------------------------------------------------------------------------------+" + "\n" + "|                                                                                              |" + "\n" + "|                                         Combat Plus                                          |" + "\n" + "|                                                                                              |" + "\n" + "|                               Discord: https://discord.gg/m7j2Y9H                            |" + "\n" + "|                                                                                              |" + "\n" + "|                                           Author: Nik                                        |" + "\n" + "|                                                                                              |" + "\n" + "+----------------------------------------------------------------------------------------------+" + "\n");
        Config.get().addDefault("settings.check_for_updates", true);
        Config.get().addDefault("settings.developer_mode", false);
        Config.get().addDefault("combat.settings.old_pvp", true);
        Config.get().addDefault("combat.settings.old_weapon_damage", true);
        Config.get().addDefault("combat.settings.old_tool_damage", true);
        Config.get().addDefault("advanced.settings.old_pvp.attack_speed", 23);
        Config.get().addDefault("advanced.settings.new_pvp.attack_speed", 4);
    }
}