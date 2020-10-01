package me.nik.combatplus.files;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.managers.commentedfiles.CommentedFileConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Config {

    private static final String[] HEADER = new String[]{
            "+----------------------------------------------------------------------------------------------+",
            "|                                                                                              |",
            "|                                           Combat Plus                                        |",
            "|                                                                                              |",
            "|                               Discord: https://discord.gg/m7j2Y9H                            |",
            "|                                                                                              |",
            "|                                           Author: Nik                                        |",
            "|                                                                                              |",
            "+----------------------------------------------------------------------------------------------+"
    };

    /**
     * Credits to Nicole for this amazing Commented File Configuration
     * https://github.com/Esophose/PlayerParticles
     */

    private final CombatPlus plugin;
    private CommentedFileConfiguration configuration;

    public Config(CombatPlus plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        File configFile = new File(this.plugin.getDataFolder(), "config.yml");
        boolean setHeaderFooter = !configFile.exists();
        boolean changed = setHeaderFooter;

        this.configuration = CommentedFileConfiguration.loadConfiguration(this.plugin, configFile);

        if (setHeaderFooter) {
            this.configuration.addComments(HEADER);
        }

        for (Setting setting : Setting.values()) {
            setting.reset();
            changed |= setting.setIfNotExists(this.configuration);
        }

        if (changed) {
            this.configuration.save();
        }
    }

    public void reset() {
        for (Setting setting : Setting.values())
            setting.reset();
    }

    /**
     * @return the config.yml as a CommentedFileConfiguration
     */
    public CommentedFileConfiguration getConfig() {
        return this.configuration;
    }

    public enum Setting {
        SETTINGS("settings", "", "General plugin settings"),
        CHECK_FOR_UPDATES("settings.check_for_updates", true, "Would you like to check for updates on startup?"),
        DEVELOPER_MODE("settings.developer_mode", false, "Would you like to receive additional information in-game when an Event gets triggered? (Debug)", "This is very useful if you'd like to Report a bug to me via our Discord"),

        COMBAT("combat", "", "Combat related settings"),
        OLD_PVP("combat.old_pvp", true, "Would you like your server to use the Old PvP?"),
        OLD_WEAPON_DAMAGE("combat.old_weapon_damage", true, "Would you like to use 1.8 Sword Damages?"),
        OLD_TOOL_DAMAGE("combat.old_tool_damage", true, "Would you like to use 1.8 Tool Damages?"),
        OLD_SHARPNESS("combat.old_sharpness", true, "Would you like to use 1.8 Sharpness Damage?"),
        DISABLE_SWEEP("combat.disable_sweep_attacks", "", "Sweep attack properties"),
        DISABLE_SWEEP_ENABLED("combat.disable_sweep_attacks.enabled", true, "Should we enable this?"),
        DISABLE_SWEEP_IGNORE_SWEEPING_EDGE("combat.disable_sweep_attacks.ignore_sweeping_edge", false, "Would you like to ignore cancelling the Sweep Attacks if The Player's Item has the Enchantment: Sweeping Edge?"),
        DISABLE_ARROW_BOOST("combat.disable_arrow_boost", true, "Would you like to prevent players from boosting themselves by using Arrows?"),
        OLD_REGEN("combat.old_player_regen", true, "Would you like to use 1.8 Regeneration?"),
        SWORD_BLOCKING("combat.sword_blocking", "", "Sword blocking properties"),
        SWORD_BLOCKING_ENABLED("combat.sword_blocking.enabled", false, "Would you like players to get a Resistance and a Slowness Effect if they hold Right Click?"),
        SWORD_BLOCKING_IGNORE_SHIELDS("combat.sword_blocking.ignore_shields", true, "Should we ignore it if the player is holding a shield?"),
        SWORD_BLOCKING_CANCEL_SPRINTING("combat.sword_blocking.cancel_sprinting", false, "Should we cancel the player's sprinting?"),
        SWORD_BLOCKING_EFFECT("combat.sword_blocking.effect", "DAMAGE_RESISTANCE", "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html"),
        SWORD_BLOCKING_DURATION_TICKS("combat.sword_blocking.duration_ticks", 8, "The duration in ticks (20 ticks = one second)"),
        SWORD_BLOCKING_AMPLIFIER("combat.sword_blocking.amplifier", 0, "The effect amplifier"),
        SWORD_BLOCKING_SLOW_DURATION_TICKS("combat.sword_blocking.slow_duration_ticks", 8, "The slow duration in ticks (20 ticks = one second)"),
        SWORD_BLOCKING_SLOW_AMPLIFIER("combat.sword_blocking.slow_amplifier", 2, "The slow amplifier"),
        COMBAT_DISABLED_WORLDS("combat.disabled_worlds", Collections.singletonList("example_world"), "Worlds listed below will be ignored from applying the above features"),

        GOLDEN_APPLE_COOLDOWN("golden_apple_cooldown", "", "Golden apple cooldown properties"),
        GOLDEN_APPLE("golden_apple_cooldown.golden_apple", "", "Golden Apple"),
        COOLDOWN_GOLDEN_APPLE_ENABLED("golden_apple_cooldown.golden_apple.enabled", true, "Should we enable this?"),
        COOLDOWN_GOLDEN_APPLE_COOLDOWN("golden_apple_cooldown.golden_apple.cooldown", 20, "Cooldown in seconds"),
        COOLDOWN_GOLDEN_APPLE_ACTIONBAR("golden_apple_cooldown.golden_apple.actionbar", true, "Should the cooldown be sent in an Actionbar Message?"),
        ENCHANTED_GOLDEN_APPLE("golden_apple_cooldown.enchanted_golden_apple", "", "Enchanted Golden Apple"),
        COOLDOWN_ENCHANTED_APPLE_ENABLED("golden_apple_cooldown.enchanted_golden_apple.enabled", true, "Should we enable this?"),
        COOLDOWN_ENCHANTED_APPLE_COOLDOWN("golden_apple_cooldown.enchanted_golden_apple.cooldown", 20, "Cooldown in seconds"),
        COOLDOWN_ENCHANTED_APPLE_ACTIONBAR("golden_apple_cooldown.enchanted_golden_apple.actionbar", true, "Should the cooldown be sent in an Actionbar Message?"),
        COOLDOWN_APPLE_WORLDS("golden_apple_cooldown.disabled_worlds", Collections.singletonList("example_world"), "Worlds listed below will be ignored from applying the above features"),

        ENDERPEARL("enderpearl_cooldown", "", "Enderpearl cooldown properties"),
        ENDERPEARL_ENABLED("enderpearl_cooldown.enabled", true, "Should we enable this?"),
        ENDERPEARL_COOLDOWN("enderpearl_cooldown.cooldown", 10, "Cooldown in seconds"),
        ENDERPEARL_ACTIONBAR("enderpearl_cooldown.actionbar", true, "Should the cooldown be sent in an Actionbar Message?"),
        ENDERPEARL_WORLDS("enderpearl_cooldown.disabled_worlds", Collections.singletonList("example_world"), "Worlds listed below will be ignored from applying the above features"),

        FISHING_ROD("fishing_rod_knockback", "", "Fishing rod knockback properties"),
        FISHING_ROD_ENABLED("fishing_rod_knockback.enabled", true, "Should we enable this?"),
        FISHING_ROD_CANCEL_DRAG("fishing_rod_knockback.cancel_dragging", true, "Should we disable the Fishing Rod dragging?"),

        DISABLED_ITEMS("disabled_items", "", "Disabled items properties"),
        DISABLED_ITEMS_ENABLED("disabled_items.enabled", false, "Should we enable this?"),
        DISABLED_ITEMS_LIST("disabled_items.items", Arrays.asList("item_frame", "armor_stand"), "Items listed below will be disabled from being crafted"),

        DISABLE_OFFHAND("disable_offhand", "", "Offhand properties"),
        DISABLE_OFFHAND_ENABLED("disable_offhand.enabled", true, "Should we enable this?"),
        DISABLE_OFFHAND_WORLDS("disable_offhand.disabled_worlds", Collections.singletonList("example_world"), "Worlds listed below will be ignored from applying the above features"),

        ENCHANTED_APPLE_CRAFTING("enchanted_golden_apple_crafting", false, "Should we make Enchanted Golden Apples craftable again?"),

        HEALTHBAR("healthbar", "", "Healthbar Properties"),
        HEALTHBAR_ENABLED("healthbar.enabled", true, "Would you like Combat Plus to send an Actionbar message to the Attacker indicating the Target's Health and Damage Dealt?"),
        HEALTHBAR_WORLDS("healthbar.disabled_worlds", Collections.singletonList("example_world"), "Worlds listed below will be ignored from applying the above features"),

        COMBATLOG("combatlog", "", "CombatLog Properties"),
        COMBATLOG_ENABLED("combatlog.enabled", true, "Would you like to enable the Combat Log?"),
        COMBATLOG_COOLDOWN("combatlog.cooldown", 15, "How long should players be tagged? (In seconds)"),
        COMBATLOG_MOBS("combatlog.mobs", false, "Should players get tagged when fighting Mobs?"),
        COMBATLOG_PROJECTILES("combatlog.projectiles", true, "Should players get tagged if they shoot players with projectiles?"),
        COMBATLOG_ACTIONBAR("combatlog.actionbar", true, "Should Combat Plus send an Actionbar message to the player?"),
        COMBATLOG_BROADCAST("combatlog.broadcast", true, "Should Combat Plus broadcast when a player dies due to logging out when tagged?"),
        COMBATLOG_DISABLE_FLY("combatlog.disable_fly", true, "Should we disable the tagged player's flight?"),
        COMBATLOG_PREVENT_TELEPORTATIONS("combatlog.prevent_teleportations", true, "Should we prevent tagged players from Teleporting?"),
        COMBATLOG_PREVENT_DROPPING_ITEMS("combatlog.prevent_dropping_items", true, "Should we prevent tagged players from Dropping Items?"),
        COMBATLOG_PREVENT_PICKING_ITEMS("combatlog.prevent_picking_items", true, "Should we prevent tagged players from Picking Up Items?"),
        COMBATLOG_COMMANDS("combatlog.commands", "", "CombatLog Command Properties"),
        COMBATLOG_COMMANDS_ENABLED("combatlog.commands.enabled", true, "Should we prevent tagged players from Using Commands?"),
        COMBATLOG_COMMANDS_EXCLUDED("combatlog.commands.excluded", Arrays.asList("/heal", "/feed"), "Commands listed below will not be blocked from tagged players"),

        FIXES("fixes", "", "Fixes properties"),
        FIX_PROJECTILES("fixes.projectile_fixer", true, "Improves Projectile Velocity"),

        CUSTOM_PLAYER_HEALTH("custom_player_health", "", "Player health properties"),
        CUSTOM_PLAYER_HEALTH_ENABLED("custom_player_health.enabled", false, "Should we enable this?"),
        CUSTOM_PLAYER_HEALTH_HEALTH("custom_player_health.max_health", 20, "The max health that a player should have"),

        ADVANCED_SETTINGS("advanced_settings", "", "Advanced Settings, Do not touch unless you know what you're doing"),

        ADV_OLD_ATTACK_SPEED("advanced_settings.old_attack_speed", 24),
        ADV_NEW_ATTACK_SPEED("advanced_settings.new_attack_speed", 4),

        ADV_NETHERITE_SWORD("advanced_settings.modifiers.netherite_sword", 1),
        ADV_DIAMOND_SWORD("advanced_settings.modifiers.diamond_sword", 1),
        ADV_GOLDEN_SWORD("advanced_settings.modifiers.golden_sword", 1),
        ADV_IRON_SWORD("advanced_settings.modifiers.iron_sword", 1),
        ADV_STONE_SWORD("advanced_settings.modifiers.stone_sword", 1),
        ADV_WOODEN_SWORD("advanced_settings.modifiers.wooden_sword", 1),

        ADV_NETHERITE_SHOVEL("advanced_settings.modifiers.netherite_shovel", 0.5),
        ADV_DIAMOND_SHOVEL("advanced_settings.modifiers.diamond_shovel", 0.5),
        ADV_GOLDEN_SHOVEL("advanced_settings.modifiers.golden_shovel", 0.5),
        ADV_IRON_SHOVEL("advanced_settings.modifiers.iron_shovel", 0.5),
        ADV_STONE_SHOVEL("advanced_settings.modifiers.stone_shovel", 0.5),
        ADV_WOODEN_SHOVEL("advanced_settings.modifiers.wooden_shovel", 0.5),

        ADV_NETHERITE_AXE("advanced_settings.modifiers.netherite_axe", -2),
        ADV_DIAMOND_AXE("advanced_settings.modifiers.diamond_axe", -2),
        ADV_GOLDEN_AXE("advanced_settings.modifiers.golden_axe", -2),
        ADV_IRON_AXE("advanced_settings.modifiers.iron_axe", -2),
        ADV_STONE_AXE("advanced_settings.modifiers.stone_axe", -2),
        ADV_WOODEN_AXE("advanced_settings.modifiers.wooden_axe", -2),

        ADV_NETHERITE_PICKAXE("advanced_settings.modifiers.netherite_pickaxe", 1),
        ADV_DIAMOND_PICKAXE("advanced_settings.modifiers.diamond_pickaxe", 1),
        ADV_GOLDEN_PICKAXE("advanced_settings.modifiers.golden_pickaxe", 1),
        ADV_IRON_PICKAXE("advanced_settings.modifiers.iron_pickaxe", 1),
        ADV_STONE_PICKAXE("advanced_settings.modifiers.stone_pickaxe", 1),
        ADV_WOODEN_PICKAXE("advanced_settings.modifiers.wooden_pickaxe", 1),

        ADV_NETHERITE_HOE("advanced_settings.modifiers.netherite_hoe", 0),
        ADV_DIAMOND_HOE("advanced_settings.modifiers.diamond_hoe", 0),
        ADV_GOLDEN_HOE("advanced_settings.modifiers.golden_hoe", 0),
        ADV_IRON_HOE("advanced_settings.modifiers.iron_hoe", 0),
        ADV_STONE_HOE("advanced_settings.modifiers.stone_hoe", 0),
        ADV_WOODEN_HOE("advanced_settings.modifiers.wooden_hoe", 0),

        ADV_REGEN_FREQUENCY("advanced_settings.old_regen.frequency", 3),
        ADV_REGEN_AMOUNT("advanced_settings.old_regen.amount", 1),
        ADV_REGEN_EXHAUSTION("advanced_settings.old_regen.exhaustion", 3),

        ADV_BASE_HEALTH("advanced_settings.base_player_health", 20),

        ADV_FISHING_ROD_DAMAGE("advanced_settings.fishing_rod_knockback_damage", 0.01);

        private final String key;
        private final Object defaultValue;
        private final String[] comments;
        private Object value = null;

        Setting(String key, Object defaultValue, String... comments) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.comments = comments != null ? comments : new String[0];
        }

        /**
         * Gets the setting as a boolean
         *
         * @return The setting as a boolean
         */
        public boolean getBoolean() {
            this.loadValue();
            return (boolean) this.value;
        }

        public String getKey() {
            return this.key;
        }

        /**
         * @return the setting as an int
         */
        public int getInt() {
            this.loadValue();
            return (int) this.getNumber();
        }

        /**
         * @return the setting as a long
         */
        public long getLong() {
            this.loadValue();
            return (long) this.getNumber();
        }

        /**
         * @return the setting as a double
         */
        public double getDouble() {
            this.loadValue();
            return this.getNumber();
        }

        /**
         * @return the setting as a float
         */
        public float getFloat() {
            this.loadValue();
            return (float) this.getNumber();
        }

        /**
         * @return the setting as a String
         */
        public String getString() {
            this.loadValue();
            return String.valueOf(this.value);
        }

        private double getNumber() {
            if (this.value instanceof Integer) {
                return (int) this.value;
            } else if (this.value instanceof Short) {
                return (short) this.value;
            } else if (this.value instanceof Byte) {
                return (byte) this.value;
            } else if (this.value instanceof Float) {
                return (float) this.value;
            }

            return (double) this.value;
        }

        /**
         * @return the setting as a string list
         */
        @SuppressWarnings("unchecked")
        public List<String> getStringList() {
            this.loadValue();
            return (List<String>) this.value;
        }

        private boolean setIfNotExists(CommentedFileConfiguration fileConfiguration) {
            this.loadValue();

            if (fileConfiguration.get(this.key) == null) {
                List<String> comments = Stream.of(this.comments).collect(Collectors.toList());
                if (this.defaultValue != null) {
                    fileConfiguration.set(this.key, this.defaultValue, comments.toArray(new String[0]));
                } else {
                    fileConfiguration.addComments(comments.toArray(new String[0]));
                }

                return true;
            }

            return false;
        }

        /**
         * Resets the cached value
         */
        public void reset() {
            this.value = null;
        }

        /**
         * @return true if this setting is only a section and doesn't contain an actual value
         */
        public boolean isSection() {
            return this.defaultValue == null;
        }

        /**
         * Loads the value from the config and caches it if it isn't set yet
         */
        private void loadValue() {
            if (this.value != null) return;
            this.value = CombatPlus.getInstance().getConfiguration().get(this.key);
        }
    }
}