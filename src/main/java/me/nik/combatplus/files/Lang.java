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
        Lang.get().addDefault("golden_apple_cooldown", "&fYou cannot eat a Golden Apple yet, You must wait ");
        Lang.get().addDefault("console.old_pvp_on", "&f&lInitialized Old PVP");
        Lang.get().addDefault("console.old_pvp_off", "&f&lOld PVP Is Turned Off, Skipping.");
        Lang.get().addDefault("console.modifiers_on", "&f&lInitialized Modifiers");
        Lang.get().addDefault("console.modifiers_off", "&f&lModifiers Are Turned Off, Skipping.");
        Lang.get().addDefault("console.arrow_boost_on", "&f&lInitialized Arrow Boost Listener");
        Lang.get().addDefault("console.arrow_boost_off", "&f&lArrow Boost Listener Is Turned Off, Skipping.");
        Lang.get().addDefault("console.golden_apple_cooldown_on", "&f&lInitialized Golden Apple Cooldown Module");
        Lang.get().addDefault("console.golden_apple_cooldown_off", "&f&lGolden Apple Cooldown Module Is Turned Off, Skipping.");
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