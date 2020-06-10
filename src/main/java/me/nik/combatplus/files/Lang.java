package me.nik.combatplus.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Lang {
    private File file;
    private static FileConfiguration lang;

    public void setup(JavaPlugin plugin) {
        file = new File(plugin.getDataFolder(), "lang.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
        lang = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return lang;
    }

    public void save() {
        try {
            lang.save(file);
        } catch (IOException ignored) {
        }
    }

    public void reload() {
        lang = YamlConfiguration.loadConfiguration(file);
    }

    public void addDefaults() {
        //lang.yml
        Lang.get().options().header("+----------------------------------------------------------------------------------------------+" + "\n" + "|                                                                                              |" + "\n" + "|                                           Combat Plus                                        |" + "\n" + "|                                                                                              |" + "\n" + "|                               Discord: https://discord.gg/m7j2Y9H                            |" + "\n" + "|                                                                                              |" + "\n" + "|                                           Author: Nik                                        |" + "\n" + "|                                                                                              |" + "\n" + "+----------------------------------------------------------------------------------------------+" + "\n");
        Lang.get().addDefault("prefix", "&f&l[&cCombat+&f&l]&f: ");
        Lang.get().addDefault("no_perm", "&cYou do not have permission to do that!");
        Lang.get().addDefault("reloading", "&fReloading the plugin...");
        Lang.get().addDefault("reloaded", "&fYou have successfully reloaded the plugin!");
        Lang.get().addDefault("update_reminder", "&6There is a new version available, Your version &a%current%&6 new version &a%new%");
        Lang.get().addDefault("golden_apple_cooldown", "&fYou cannot eat a Golden Apple yet, You must wait %seconds% seconds.");
        Lang.get().addDefault("golden_apple_cooldown_actionbar", "&6You can eat a Golden Apple again in &a%seconds% &6seconds.");
        Lang.get().addDefault("enchanted_golden_apple_cooldown", "&fYou cannot eat an Enchanted Golden Apple yet, You must wait %seconds% seconds.");
        Lang.get().addDefault("enchanted_golden_apple_cooldown_actionbar", "&6You can eat an Enchanted Golden Apple again in &a%seconds% &6seconds.");
        Lang.get().addDefault("enderpearl_cooldown", "&fYou cannot use an Ender Pearl yet, You must wait %seconds% seconds.");
        Lang.get().addDefault("enderpearl_cooldown_actionbar", "&6You can use an Ender Pearl again in &a%seconds% &6seconds.");
        Lang.get().addDefault("cannot_craft_this", "&cYou cannot craft this item!");
        Lang.get().addDefault("gui_main", "&c&lCombatPlus Menu");
        Lang.get().addDefault("gui_plugin", "&c&lPlugin Settings");
        Lang.get().addDefault("gui_combat", "&c&lCombat Settings");
        Lang.get().addDefault("gui_general", "&c&lGeneral Settings");
        Lang.get().addDefault("console_update_not_found", "&6&lNo updates are found, You're running the Latest Version <3");
        Lang.get().addDefault("console_update_disabled", "&f&lUpdate Checker is Disabled, Skipping.");
        Lang.get().addDefault("console_commands", "&c&lYou cannot run this command through the console :(");
        Lang.get().addDefault("console_initialize", "&f&lInitializing Listeners...");
        Lang.get().addDefault("console_unsupported_version", "&c&lWARNING! You Are Using an Unsupported Version, Some Modules Have Been Skipped.");
        Lang.get().addDefault("console_unsupported_sweep_attack", "&c&lSweep Attacks Cannot be Disabled on your Minecraft Version, Skipping.");
        Lang.get().addDefault("console_disabled", "&f&lAll Player Stats Have Been Set Back To Their Default Values! Goodbye.");
    }
}