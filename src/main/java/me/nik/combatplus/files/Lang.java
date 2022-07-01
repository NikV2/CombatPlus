package me.nik.combatplus.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Lang {
    private File file;
    private FileConfiguration lang;

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

    public FileConfiguration get() {
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
        get().options().header("+----------------------------------------------------------------------------------------------+" + "\n" + "|                                                                                              |" + "\n" + "|                                           Combat Plus                                        |" + "\n" + "|                                                                                              |" + "\n" + "|                               Discord: https://discord.gg/m7j2Y9H                            |" + "\n" + "|                                                                                              |" + "\n" + "|                                           Author: Nik                                        |" + "\n" + "|                                                                                              |" + "\n" + "+----------------------------------------------------------------------------------------------+" + "\n");
        get().addDefault("prefix", "&f&l[&cCombat+&f&l]&f: ");
        get().addDefault("no_perm", "&cYou do not have permission to do that!");
        get().addDefault("reloaded", "&fYou have successfully reloaded the plugin!");
        get().addDefault("update_reminder", "&6There is a new version available, Your version &a%current%&6 new version &a%new%");
        get().addDefault("golden_apple_cooldown", "&fYou cannot eat a Golden Apple yet, You must wait %seconds% seconds.");
        get().addDefault("golden_apple_cooldown_actionbar", "&6You can eat a Golden Apple again in &a%seconds% &6seconds.");
        get().addDefault("enchanted_golden_apple_cooldown", "&fYou cannot eat an Enchanted Golden Apple yet, You must wait %seconds% seconds.");
        get().addDefault("enchanted_golden_apple_cooldown_actionbar", "&6You can eat an Enchanted Golden Apple again in &a%seconds% &6seconds.");
        get().addDefault("enderpearl_cooldown", "&fYou cannot use an Ender Pearl yet, You must wait %seconds% seconds.");
        get().addDefault("enderpearl_cooldown_actionbar", "&6You can use an Ender Pearl again in &a%seconds% &6seconds.");
        get().addDefault("combatlog_tagged", "&fYou are now in combat, Do not log out!");
        get().addDefault("combatlog_actionbar", "&f&l[&cCombat+&f&l]&f: You are tagged for %seconds% seconds");
        get().addDefault("combatlog_broadcast", "&f%player% Died due to logging out while in combat");
        get().addDefault("combatlog_item_drop", "&cYou can not drop items while in combat!");
        get().addDefault("combatlog_teleport", "&cYou can not teleport while in combat!");
        get().addDefault("combatlog_command", "&cYou can not use this command while in combat!");
        get().addDefault("gui_main", "&c&lCombatPlus Menu");
        get().addDefault("gui_plugin", "&c&lPlugin Settings");
        get().addDefault("gui_combat", "&c&lCombat Settings");
        get().addDefault("gui_general", "&c&lGeneral Settings");
        get().addDefault("console_update_not_found", "&6&lNo updates are found, You're running the Latest Version <3");
        get().addDefault("console_commands", "&c&lYou cannot run this command through the console :(");
    }
}