package me.nik.combatplus.files;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.commentedfiles.CommentedFileConfiguration;

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

        DISABLE_BYPASS_PERMISSIONS("settings.disable_bypass_permissions", true, "Should we disable the bypass permissions?"),

        DEVELOPER_MODE("settings.developer_mode", false, "Would you like to receive additional information in-game when an Event gets triggered? (Debug)", "This is very useful if you'd like to Report a bug to me via our Discord"),

        CUSTOM_ATTACK_SPEED("custom_attack_speed", "", "Custom attack speed properties"),
        CUSTOM_ATTACK_SPEED_ENABLED("custom_attack_speed.enabled", true, "Should we modify the player's attack speed?"),
        CUSTOM_ATTACK_SPEED_ATTACK_SPEED("custom_attack_speed.attack_speed", 24, "The new attack speed", "The default value removes the cooldown completely"),
        CUSTOM_ATTACK_SPEED_DEFAULT_ATTACK_SPEED("custom_attack_speed.default_attack_speed", 4, "The base attack speed value, Do not change this", "Unless this changes in a newer version of minecraft"),

        DAMAGE_MODIFIERS("damage_modifiers", "", "Damage modifiers properties"),
        DAMAGE_MODIFIERS_OLD_SHARPNESS("damage_modifiers.old_sharpness", true, "Should we bring back 1.8 sharpness damage?"),
        DAMAGE_MODIFIERS_DISABLE_SWEEP("damage_modifiers.disable_sweep_attacks", true, "Should we disable sweep attacks?"),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES("damage_modifiers.custom_tool_damages", "", "Custom tool damages properties"),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_ENABLED("damage_modifiers.custom_tool_damages.enabled", true, "Should we modify damage dealt by tools?"),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS("damage_modifiers.custom_tool_damages.tools", "", "The tool damage modifiers", "The default values replicate 1.8 weapon and tool damages", "The value is simply going to add up to the damage dealt", "If you'd like to not add or subtract any additional damage simply set the value to zero"),

        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_WOODEN_SWORD("damage_modifiers.custom_tool_damages.tools.WOODEN_SWORD", 1),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_STONE_SWORD("damage_modifiers.custom_tool_damages.tools.STONE_SWORD", 1),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_IRON_SWORD("damage_modifiers.custom_tool_damages.tools.IRON_SWORD", 1),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_GOLDEN_SWORD("damage_modifiers.custom_tool_damages.tools.GOLDEN_SWORD", 1),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_DIAMOND_SWORD("damage_modifiers.custom_tool_damages.tools.DIAMOND_SWORD", 1),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_NETHERITE_SWORD("damage_modifiers.custom_tool_damages.tools.NETHERITE_SWORD", 1),

        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_WOODEN_SHOVEL("damage_modifiers.custom_tool_damages.tools.WOODEN_SHOVEL", 0.5),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_STONE_SHOVEL("damage_modifiers.custom_tool_damages.tools.STONE_SHOVEL", 0.5),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_IRON_SHOVEL("damage_modifiers.custom_tool_damages.tools.IRON_SHOVEL", 0.5),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_GOLDEN_SHOVEL("damage_modifiers.custom_tool_damages.tools.GOLDEN_SHOVEL", 0.5),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_DIAMOND_SHOVEL("damage_modifiers.custom_tool_damages.tools.DIAMOND_SHOVEL", 0.5),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_NETHERITE_SHOVEL("damage_modifiers.custom_tool_damages.tools.NETHERITE_SHOVEL", 0.5),

        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_WOODEN_AXE("damage_modifiers.custom_tool_damages.tools.WOODEN_AXE", -2),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_STONE_AXE("damage_modifiers.custom_tool_damages.tools.STONE_AXE", -2),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_IRON_AXE("damage_modifiers.custom_tool_damages.tools.IRON_AXE", -2),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_GOLDEN_AXE("damage_modifiers.custom_tool_damages.tools.GOLDEN_AXE", -2),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_DIAMOND_AXE("damage_modifiers.custom_tool_damages.tools.DIAMOND_AXE", -2),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_NETHERITE_AXE("damage_modifiers.custom_tool_damages.tools.NETHERITE_AXE", -2),

        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_WOODEN_PICKAXE("damage_modifiers.custom_tool_damages.tools.WOODEN_PICKAXE", 1),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_STONE_PICKAXE("damage_modifiers.custom_tool_damages.tools.STONE_PICKAXE", 1),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_IRON_PICKAXE("damage_modifiers.custom_tool_damages.tools.IRON_PICKAXE", 1),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_GOLDEN_PICKAXE("damage_modifiers.custom_tool_damages.tools.GOLDEN_PICKAXE", 1),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_DIAMOND_PICKAXE("damage_modifiers.custom_tool_damages.tools.DIAMOND_PICKAXE", 1),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_NETHERITE_PICKAXE("damage_modifiers.custom_tool_damages.tools.NETHERITE_PICKAXE", 1),

        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_WOODEN_HOE("damage_modifiers.custom_tool_damages.tools.WOODEN_HOE", 0),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_STONE_HOE("damage_modifiers.custom_tool_damages.tools.STONE_HOE", 0),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_IRON_HOE("damage_modifiers.custom_tool_damages.tools.IRON_HOE", 0),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_GOLDEN_HOE("damage_modifiers.custom_tool_damages.tools.GOLDEN_HOE", 0),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_DIAMOND_HOE("damage_modifiers.custom_tool_damages.tools.DIAMOND_HOE", 0),
        DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_NETHERITE_HOE("damage_modifiers.custom_tool_damages.tools.NETHERITE_HOE", 0),

        DISABLE_ARROW_BOOST("disable_arrow_boost", true, "Would you like to prevent players from boosting themselves by using Arrows?"),

        CUSTOM_PLAYER_REGENERATION("custom_player_regeneration", "", "Custom player regeneration properties"),
        CUSTOM_PLAYER_REGENERATION_ENABLED("custom_player_regeneration.enabled", true, "Should we modify the player's regeneration?"),
        CUSTOM_PLAYER_REGENERATION_FREQUENCY("custom_player_regeneration.frequency", 3, "The regeneration rate", "The default value replicates 1.8 regeneration"),
        CUSTOM_PLAYER_REGENERATION_AMOUNT("custom_player_regeneration.amount", 1, "The regeneration amount", "The default value replicates 1.8 regeneration"),
        CUSTOM_PLAYER_REGENERATION_EXCHAUSTION("custom_player_regeneration.exchaustion", 3, "The exchaustion amount", "The default value replicates 1.8 regeneration"),

        SWORD_BLOCKING("sword_blocking", "", "Sword blocking properties"),
        SWORD_BLOCKING_ENABLED("sword_blocking.enabled", false, "Would you like players to get a Resistance and a Slowness Effect if they hold Right Click?"),
        SWORD_BLOCKING_IGNORE_SHIELDS("sword_blocking.ignore_shields", true, "Should we ignore it if the player is holding a shield on his offhand?"),
        SWORD_BLOCKING_CANCEL_SPRINTING("sword_blocking.cancel_sprinting", false, "Should we cancel the player's sprinting?"),
        SWORD_BLOCKING_EFFECT("sword_blocking.effect", "DAMAGE_RESISTANCE", "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html"),
        SWORD_BLOCKING_DURATION_TICKS("sword_blocking.duration_ticks", 8, "The duration in ticks (20 ticks = 1 second)"),
        SWORD_BLOCKING_AMPLIFIER("sword_blocking.amplifier", 0, "The effect amplifier"),
        SWORD_BLOCKING_SLOW_DURATION_TICKS("sword_blocking.slow_duration_ticks", 8, "The slow duration in ticks (20 ticks = 1 second)"),
        SWORD_BLOCKING_SLOW_AMPLIFIER("sword_blocking.slow_amplifier", 2, "The slow amplifier"),

        COOLDOWNS("cooldowns", "", "Cooldowns properties"),

        GOLDEN_APPLE("cooldowns.golden_apple", "", "Golden Apple Cooldown"),
        GOLDEN_APPLE_ENABLED("cooldowns.golden_apple.enabled", true, "Should we enable this?"),
        GOLDEN_APPLE_COOLDOWN("cooldowns.golden_apple.cooldown", 20, "Cooldown in seconds"),
        GOLDEN_APPLE_ACTIONBAR("cooldowns.golden_apple.actionbar", true, "Should the cooldown get sent in an Actionbar Message?"),
        GOLDEN_APPLE_DISABLED_WORLDS("cooldowns.golden_apple.disabled_worlds", Collections.singletonList("example_world"), "Worlds listed below will be ignored from applying the above features"),

        ENCHANTED_APPLE("cooldowns.enchanted_golden_apple", "", "Enchanted Golden Apple Cooldown"),
        ENCHANTED_APPLE_ENABLED("cooldowns.enchanted_golden_apple.enabled", true, "Should we enable this?"),
        ENCHANTED_APPLE_COOLDOWN("cooldowns.enchanted_golden_apple.cooldown", 20, "Cooldown in seconds"),
        ENCHANTED_APPLE_ACTIONBAR("cooldowns.enchanted_golden_apple.actionbar", true, "Should the cooldown be sent in an Actionbar Message?"),
        ENCHANTED_APPLE_DISABLED_WORLDS("cooldowns.enchanted_golden_apple.disabled_worlds", Collections.singletonList("example_world"), "Worlds listed below will be ignored from applying the above features"),

        ENDERPEARL("cooldowns.enderpearl", "", "Enderpearl Cooldown"),
        ENDERPEARL_ENABLED("cooldowns.enderpearl.enabled", true, "Should we enable this?"),
        ENDERPEARL_COOLDOWN("cooldowns.enderpearl.cooldown", 10, "Cooldown in seconds"),
        ENDERPEARL_ACTIONBAR("cooldowns.enderpearl.actionbar", true, "Should the cooldown be sent in an Actionbar Message?"),
        ENDERPEARL_DISABLED_WORLDS("cooldowns.enderpearl.disabled_worlds", Collections.singletonList("example_world"), "Worlds listed below will be ignored from applying the above features"),

        FISHING_ROD("fishing_rod_knockback", "", "Fishing rod knockback properties"),
        FISHING_ROD_ENABLED("fishing_rod_knockback.enabled", true, "Should we enable this?"),
        FISHING_ROD_DAMAGE("fishing_rod_knockback.damage", 0.01, "The knockback damage to be applied"),
        FISHING_ROD_CANCEL_DRAG("fishing_rod_knockback.cancel_dragging", true, "Should we disable the Fishing Rod dragging?"),

        DISABLE_OFFHAND("disable_offhand", true, "Would you like to completely disable the use of the player's offhand?"),

        HEALTHBAR("healthbar", "", "Healthbar Properties"),
        HEALTHBAR_ENABLED("healthbar.enabled", true, "Would you like Combat Plus to send an Actionbar message to the Attacker indicating the Target's Health and Damage Dealt?"),
        HEALTHBAR_PLAYERS_ONLY("healthbar.players_only", true, "Should we only show the health bar of players?"),
        HEALTHBAR_DISABLED_WORLDS("healthbar.disabled_worlds", Collections.singletonList("example_world"), "Worlds listed below will be ignored from applying the above features"),

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
        COMBATLOG_DISABLED_WORLDS("combatlog.disabled_worlds", Collections.singletonList("example_world"), "Worlds listed below will be ignored from applying the above features"),
        COMBATLOG_COMMANDS("combatlog.commands", "", "CombatLog Command Properties"),
        COMBATLOG_COMMANDS_ENABLED("combatlog.commands.enabled", true, "Should we prevent tagged players from Using Commands?"),
        COMBATLOG_COMMANDS_EXCLUDED("combatlog.commands.excluded", Arrays.asList("/heal", "/feed"), "Commands listed below will not be blocked from tagged players"),

        CUSTOM_PLAYER_HEALTH("custom_player_health", "", "Player health properties"),
        CUSTOM_PLAYER_HEALTH_ENABLED("custom_player_health.enabled", false, "Should we enable this?"),
        CUSTOM_PLAYER_HEALTH_HEALTH("custom_player_health.max_health", 40, "The max health that a player should have"),
        CUSTOM_PLAYER_HEALTH_DEFAULT_MAX_HEALTH("custom_player_health.default_max_health", 20, "The base max health value, Do not change this", "Unless this changes in a newer version of minecraft");

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