package me.nik.combatplus.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Lang {
    private static File file;
    private static FileConfiguration lang;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("CombatPlus").getDataFolder(), "lang.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                //Does not exist
            }
        }
        lang = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return lang;
    }

    public static void save() {
        try {
            lang.save(file);
        } catch (IOException e) {
            //Cannot save file
        }
    }

    public static void reload() {
        lang = YamlConfiguration.loadConfiguration(file);
    }

    public static void addDefaults() {
        //lang.yml
        Lang.get().options().header("+----------------------------------------------------------------------------------------------+" + "\n" + "|                                                                                              |" + "\n" + "|                                         Combat Plus                                          |" + "\n" + "|                                                                                              |" + "\n" + "|                               Discord: https://discord.gg/m7j2Y9H                            |" + "\n" + "|                                                                                              |" + "\n" + "|                                           Author: Nik                                        |" + "\n" + "|                                                                                              |" + "\n" + "+----------------------------------------------------------------------------------------------+" + "\n");
        Lang.get().addDefault("prefix", "[combatplus]: ");
    }
}