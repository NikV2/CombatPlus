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
        Lang.get().options().header("+----------------------------------------------------------------------------------------------+" + "\n" + "|                                                                                              |" + "\n" + "|                                           Combat Plus                                        |" + "\n" + "|                                                                                              |" + "\n" + "|                               Discord: https://discord.gg/m7j2Y9H                            |" + "\n" + "|                                                                                              |" + "\n" + "|                                           Author: Nik                                        |" + "\n" + "|                                                                                              |" + "\n" + "+----------------------------------------------------------------------------------------------+" + "\n");
        Lang.get().addDefault("prefix", "&f&l[&cCombat+&f&l]&f: ");
        Lang.get().addDefault("no_perm", "&cYou do not have permission to do that!");
        Lang.get().addDefault("reloading", "&fReloading the plugin...");
        Lang.get().addDefault("reloaded", "&fYou have successfully reloaded the plugin!");
        Lang.get().addDefault("golden_apple_cooldown", "&fYou cannot eat a Golden Apple yet, You must wait ");
        Lang.get().addDefault("enchanted_golden_apple_cooldown", "&fYou cannot eat an Enchanted Golden Apple yet, You must wait ");
        Lang.get().addDefault("cannot_craft_this", "&cYou cannot craft this item!");
        Lang.get().addDefault("gui.main", "&c&lCombatPlus Menu");
        Lang.get().addDefault("gui.plugin", "&c&lPlugin Settings");
        Lang.get().addDefault("gui.combat", "&c&lCombat Settings");
        Lang.get().addDefault("gui.general", "&c&lGeneral Settings");
        Lang.get().addDefault("console.update_found", "&a&lThere is an Update available on Spigot!");
        Lang.get().addDefault("console.update_not_found", "&6&lNo updates are found, You're running the Latest Version <3");
        Lang.get().addDefault("console.update_disabled", "&f&lUpdate Checker is Disabled, Skipping.");
        Lang.get().addDefault("console.commands", "&c&lYou cannot run this command through the console :(");
        Lang.get().addDefault("console.old_pvp_on", "&f&lInitialized Old PVP");
        Lang.get().addDefault("console.old_pvp_off", "&f&lOld PVP Is Turned Off, Skipping.");
        Lang.get().addDefault("console.modifiers_on", "&f&lInitialized Modifiers");
        Lang.get().addDefault("console.modifiers_off", "&f&lModifiers Are Turned Off, Skipping.");
        Lang.get().addDefault("console.arrow_boost_on", "&f&lInitialized Arrow Boost Listener");
        Lang.get().addDefault("console.arrow_boost_off", "&f&lArrow Boost Listener Is Turned Off, Skipping.");
        Lang.get().addDefault("console.golden_apple_cooldown_on", "&f&lInitialized Golden Apple Cooldown Module");
        Lang.get().addDefault("console.golden_apple_cooldown_off", "&f&lGolden Apple Cooldown Module Is Turned Off, Skipping.");
        Lang.get().addDefault("console.enchanted_golden_apple_cooldown_on", "&f&lInitialized Enchanted Golden Apple Cooldown Module");
        Lang.get().addDefault("console.enchanted_golden_apple_cooldown_off", "&f&lEnchanted Golden Apple Cooldown Module Is Turned Off, Skipping.");
        Lang.get().addDefault("console.old_regen_on", "&f&lInitialized Old Player Regeneration Listener");
        Lang.get().addDefault("console.old_regen_off", "&f&lOld Player Regeneration Listener Is Turned Off, Skipping.");
        Lang.get().addDefault("console.disabled_items_on", "&f&lInitialized Disabled Items Manager");
        Lang.get().addDefault("console.disabled_items_off", "&f&lDisabled Items Is Turned Off, Skipping.");
        Lang.get().addDefault("console.item_frame_rotation_on", "&f&lInitialized Item Frame Rotation Blocker");
        Lang.get().addDefault("console.item_frame_rotation_off", "&f&lItem Frame Rotation Blocker Is Turned Off, Skipping.");
        Lang.get().addDefault("console.disable_offhand_on", "&f&lInitialized Offhand Listener");
        Lang.get().addDefault("console.disable_offhand_off", "&f&lOffhand Listener Is Turned Off, Skipping.");
        Lang.get().addDefault("console.fixes_on", "&f&lInitialized Fixing Modules");
        Lang.get().addDefault("console.fixes_off", "&f&lFixes Are Turned Off, Skipping.");
        Lang.get().addDefault("console.unsupported_version", "&c&lWARNING! You Are Using an Unsupported Version, Some Modules Have Been Skipped.");
        Lang.get().addDefault("console.unsupported_sweep_attack", "&c&lSweep Attacks Cannot be Disabled on your Minecraft Version, Skipping.");
    }
}