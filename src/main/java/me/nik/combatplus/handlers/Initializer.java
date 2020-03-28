package me.nik.combatplus.handlers;

import me.nik.combatplus.api.Manager;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.listeners.*;
import me.nik.combatplus.utils.Messenger;

public class Initializer extends Manager {

    public void initialize() {
        if (configBoolean("combat.settings.old_pvp")) {
            registerEvent(new AttackSpeed());
            System.out.println(Messenger.message("console.old_pvp_on"));
        } else {
            System.out.println(Messenger.message("console.old_pvp_off"));
        }
        if (configBoolean("combat.settings.old_weapon_damage") || configBoolean("combat.settings.old_tool_damage") || configBoolean("combat.settings.disable_sweep_attacks")) {
            registerEvent(new DamageModifiers());
            System.out.println(Messenger.message("console.modifiers_on"));
        } else {
            System.out.println(Messenger.message("console.modifiers_off"));
        }
        if (configBoolean("combat.settings.disable_arrow_boost")) {
            registerEvent(new BowBoost());
            System.out.println(Messenger.message("console.arrow_boost_on"));
        } else {
            System.out.println(Messenger.message("console.arrow_boost_off"));
        }
        if (configBoolean("general.settings.golden_apple_cooldown.enabled")) {
            registerEvent(new Gapple());
            System.out.println(Messenger.message("console.golden_apple_cooldown_on"));
        } else {
            System.out.println(Messenger.message("console.golden_apple_cooldown_off"));
        }
        if (configBoolean("combat.settings.old_player_regen")) {
            registerEvent(new PlayerRegen());
            System.out.println(Messenger.message("console.old_regen_on"));
        } else {
            System.out.println(Messenger.message("console.old_regen_off"));
        }
    }

    public void initializeFiles() {
        Config.setup();
        Config.addDefaults();
        Config.get().options().copyDefaults(true);
        Config.save();
        Lang.setup();
        Lang.addDefaults();
        Lang.get().options().copyDefaults(true);
        Lang.save();
    }
}
