package me.nik.combatplus.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Config {
    private File file;
    private FileConfiguration config;

    public void setup(JavaPlugin plugin) {
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration get() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException ignored) {
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void addDefaults() {
        //config.yml
        //string lists
        List<String> combatList = get().getStringList("combat.settings.disabled_worlds");
        combatList.add("example_world");
        List<String> gappleList = get().getStringList("general.settings.golden_apple_cooldown.disabled_worlds");
        gappleList.add("example_world");
        List<String> itemsList = get().getStringList("general.settings.disabled_items.items");
        itemsList.add("item_frame");
        itemsList.add("armor_stand");
        List<String> itemFrameRotation = get().getStringList("disable_item_frame_rotation.disabled_worlds");
        itemFrameRotation.add("example_world");
        List<String> epearlList = get().getStringList("enderpearl_cooldown.disabled_worlds");
        epearlList.add("example_world");
        List<String> offhandWorlds = get().getStringList("disable_offhand.disabled_worlds");
        offhandWorlds.add("example_world");
        get().options().header("+----------------------------------------------------------------------------------------------+" + "\n" + "|                                                                                              |" + "\n" + "|                                           Combat Plus                                        |" + "\n" + "|                                                                                              |" + "\n" + "|                               Discord: https://discord.gg/m7j2Y9H                            |" + "\n" + "|                                                                                              |" + "\n" + "|                                           Author: Nik                                        |" + "\n" + "|                                                                                              |" + "\n" + "+----------------------------------------------------------------------------------------------+" + "\n");
        get().addDefault("settings.check_for_updates", true);
        get().addDefault("settings.developer_mode", false);
        get().addDefault("combat.settings.old_pvp", true);
        get().addDefault("combat.settings.old_weapon_damage", true);
        get().addDefault("combat.settings.old_tool_damage", true);
        get().addDefault("combat.settings.old_sharpness", true);
        get().addDefault("combat.settings.disable_sweep_attacks.enabled", true);
        get().addDefault("combat.settings.disable_sweep_attacks.ignore_sweeping_edge", false);
        get().addDefault("combat.settings.disable_arrow_boost", true);
        get().addDefault("combat.settings.old_player_regen", true);
        get().addDefault("combat.settings.sword_blocking.enabled", true);
        get().addDefault("combat.settings.sword_blocking.ignore_shields", true);
        get().addDefault("combat.settings.sword_blocking.cancel_sprinting", false);
        get().addDefault("combat.settings.sword_blocking.effect", "DAMAGE_RESISTANCE");
        get().addDefault("combat.settings.sword_blocking.duration_ticks", 8);
        get().addDefault("combat.settings.sword_blocking.amplifier", 0);
        get().addDefault("combat.settings.sword_blocking.slow_duration_ticks", 8);
        get().addDefault("combat.settings.sword_blocking.slow_amplifier", 2);
        get().addDefault("combat.settings.disabled_worlds", combatList);
        get().addDefault("golden_apple_cooldown.golden_apple.enabled", true);
        get().addDefault("golden_apple_cooldown.golden_apple.cooldown", 10);
        get().addDefault("golden_apple_cooldown.golden_apple.actionbar", true);
        get().addDefault("golden_apple_cooldown.enchanted_golden_apple.enabled", true);
        get().addDefault("golden_apple_cooldown.enchanted_golden_apple.cooldown", 20);
        get().addDefault("golden_apple_cooldown.enchanted_golden_apple.actionbar", true);
        get().addDefault("golden_apple_cooldown.disabled_worlds", gappleList);
        get().addDefault("enderpearl_cooldown.enabled", true);
        get().addDefault("enderpearl_cooldown.cooldown", 10);
        get().addDefault("enderpearl_cooldown.actionbar", true);
        get().addDefault("enderpearl_cooldown.disabled_worlds", epearlList);
        get().addDefault("knockback.fishing_rod.enabled", true);
        get().addDefault("knockback.fishing_rod.cancel_dragging", true);
        get().addDefault("disabled_items.enabled", false);
        get().addDefault("disabled_items.items", itemsList);
        get().addDefault("disable_item_frame_rotation.enabled", true);
        get().addDefault("disable_item_frame_rotation.disabled_worlds", itemFrameRotation);
        get().addDefault("disable_offhand.enabled", true);
        get().addDefault("disable_offhand.disabled_worlds", offhandWorlds);
        get().addDefault("recipes.enchanted_golden_apple", false);
        get().addDefault("fixes.projectile_fixer", true);
        get().addDefault("custom.player_health.enabled", false);
        get().addDefault("custom.player_health.max_health", 20);
        get().addDefault("advanced.settings.old_pvp.attack_speed", 24);
        get().addDefault("advanced.settings.new_pvp.attack_speed", 4);
        get().addDefault("advanced.settings.modifiers.netherite_sword", 1);
        get().addDefault("advanced.settings.modifiers.diamond_sword", 1);
        get().addDefault("advanced.settings.modifiers.golden_sword", 1);
        get().addDefault("advanced.settings.modifiers.iron_sword", 1);
        get().addDefault("advanced.settings.modifiers.stone_sword", 1);
        get().addDefault("advanced.settings.modifiers.wooden_sword", 1);
        get().addDefault("advanced.settings.modifiers.netherite_shovel", 0.5);
        get().addDefault("advanced.settings.modifiers.diamond_shovel", 0.5);
        get().addDefault("advanced.settings.modifiers.golden_shovel", 0.5);
        get().addDefault("advanced.settings.modifiers.iron_shovel", 0.5);
        get().addDefault("advanced.settings.modifiers.stone_shovel", 0.5);
        get().addDefault("advanced.settings.modifiers.wooden_shovel", 0.5);
        get().addDefault("advanced.settings.modifiers.netherite_axe", -2);
        get().addDefault("advanced.settings.modifiers.diamond_axe", -2);
        get().addDefault("advanced.settings.modifiers.golden_axe", -2);
        get().addDefault("advanced.settings.modifiers.iron_axe", -2);
        get().addDefault("advanced.settings.modifiers.stone_axe", -2);
        get().addDefault("advanced.settings.modifiers.wooden_axe", -2);
        get().addDefault("advanced.settings.modifiers.netherite_pickaxe", 1);
        get().addDefault("advanced.settings.modifiers.diamond_pickaxe", 1);
        get().addDefault("advanced.settings.modifiers.golden_pickaxe", 1);
        get().addDefault("advanced.settings.modifiers.iron_pickaxe", 1);
        get().addDefault("advanced.settings.modifiers.stone_pickaxe", 1);
        get().addDefault("advanced.settings.modifiers.wooden_pickaxe", 1);
        get().addDefault("advanced.settings.modifiers.netherite_hoe", 0);
        get().addDefault("advanced.settings.modifiers.diamond_hoe", 0);
        get().addDefault("advanced.settings.modifiers.golden_hoe", 0);
        get().addDefault("advanced.settings.modifiers.iron_hoe", 0);
        get().addDefault("advanced.settings.modifiers.stone_hoe", 0);
        get().addDefault("advanced.settings.modifiers.wooden_hoe", 0);
        get().addDefault("advanced.settings.old_regen.frequency", 3);
        get().addDefault("advanced.settings.old_regen.amount", 1);
        get().addDefault("advanced.settings.old_regen.exhaustion", 3);
        get().addDefault("advanced.settings.base_player_health", 20);
        get().addDefault("advanced.settings.knockback.fishing_rod.damage", 0.01);
    }
}