package me.nik.combatplus.handlers;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.Manager;
import me.nik.combatplus.listeners.*;
import me.nik.combatplus.listeners.fixes.Projectiles;
import me.nik.combatplus.utils.Messenger;

public class Initializer extends Manager {

    public Initializer(CombatPlus plugin) {
        super(plugin);
    }

    public void initialize() {
        if (configBoolean("combat.settings.old_pvp") || configBoolean("custom.player_health.enabled")) {
            registerEvent(new AttributesSet(plugin));
            System.out.println(Messenger.message("console.attribute_modifiers_on"));
        } else {
            System.out.println(Messenger.message("console.attribute_modifiers_off"));
        }
        if (configBoolean("combat.settings.old_weapon_damage") || configBoolean("combat.settings.old_tool_damage") || configBoolean("combat.settings.disable_sweep_attacks.enabled")) {
            registerEvent(new DamageModifiers(plugin));
            System.out.println(Messenger.message("console.modifiers_on"));
        } else {
            System.out.println(Messenger.message("console.modifiers_off"));
        }
        if (configBoolean("combat.settings.disable_arrow_boost")) {
            registerEvent(new BowBoost(plugin));
            System.out.println(Messenger.message("console.arrow_boost_on"));
        } else {
            System.out.println(Messenger.message("console.arrow_boost_off"));
        }
        if (configBoolean("combat.settings.old_player_regen")) {
            registerEvent(new PlayerRegen(plugin));
            System.out.println(Messenger.message("console.old_regen_on"));
        } else {
            System.out.println(Messenger.message("console.old_regen_off"));
        }
        if (configBoolean("disabled_items.enabled")) {
            registerEvent(new DisabledItems(plugin));
            System.out.println(Messenger.message("console.disabled_items_on"));
        } else {
            System.out.println(Messenger.message("console.disabled_items_off"));
        }
        if (configBoolean("disable_item_frame_rotation.enabled")) {
            registerEvent(new ItemFrameRotate(plugin));
            System.out.println(Messenger.message("console.item_frame_rotation_on"));
        } else {
            System.out.println(Messenger.message("console.item_frame_rotation_off"));
        }
        if (configBoolean("disable_offhand.enabled")) {
            registerEvent(new Offhand(plugin));
            System.out.println(Messenger.message("console.disable_offhand_on"));
        } else {
            System.out.println(Messenger.message("console.disable_offhand_off"));
        }
        if (configBoolean("fixes.projectile_fixer")) {
            registerEvent(new Projectiles(plugin));
            System.out.println(Messenger.message("console.fixes_on"));
        } else {
            System.out.println(Messenger.message("console.fixes_off"));
        }
        if (configBoolean("golden_apple_cooldown.golden_apple.enabled")) {
            registerEvent(new GoldenApple(plugin));
            System.out.println(Messenger.message("console.golden_apple_cooldown_on"));
        } else {
            System.out.println(Messenger.message("console.golden_apple_cooldown_off"));
        }
        if (configBoolean("golden_apple_cooldown.enchanted_golden_apple.enabled")) {
            registerEvent(new EnchantedGoldenApple(plugin));
            System.out.println(Messenger.message("console.enchanted_golden_apple_cooldown_on"));
        } else {
            System.out.println(Messenger.message("console.enchanted_golden_apple_cooldown_off"));
        }
        //GUI Listener (Do not remove this, idiot nik)
        registerEvent(new GUIListener(plugin));
    }
}
