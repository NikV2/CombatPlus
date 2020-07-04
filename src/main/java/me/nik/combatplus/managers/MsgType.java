package me.nik.combatplus.managers;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.utils.Messenger;

public enum MsgType {
    PREFIX(Messenger.format(CombatPlus.getInstance().getLang().get().getString("prefix"))),
    NO_PERMISSION(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("no_perm"))),
    RELOADING(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("reloading"))),
    RELOADED(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("reloaded"))),
    UPDATE_REMINDER(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("update_reminder"))),
    GOLDEN_APPLE_COOLDOWN(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("golden_apple_cooldown"))),
    GOLDEN_APPLE_COOLDOWN_ACTIONBAR(Messenger.format(CombatPlus.getInstance().getLang().get().getString("golden_apple_cooldown_actionbar"))),
    ENCHANTED_GOLDEN_APPLE_COOLDOWN(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("enchanted_golden_apple_cooldown"))),
    ENCHANTED_GOLDEN_APPLE_COOLDOWN_ACTIONBAR(Messenger.format(CombatPlus.getInstance().getLang().get().getString("enchanted_golden_apple_cooldown_actionbar"))),
    ENDERPEARL_COOLDOWN(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("enderpearl_cooldown"))),
    ENDERPEARL_COOLDOWN_ACTIONBAR(Messenger.format(CombatPlus.getInstance().getLang().get().getString("enderpearl_cooldown_actionbar"))),
    CANNOT_CRAFT_THIS(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("cannot_craft_this"))),
    GUI_MAIN(Messenger.format(CombatPlus.getInstance().getLang().get().getString("gui_main"))),
    GUI_PLUGIN(Messenger.format(CombatPlus.getInstance().getLang().get().getString("gui_plugin"))),
    GUI_COMBAT(Messenger.format(CombatPlus.getInstance().getLang().get().getString("gui_combat"))),
    GUI_GENERAL(Messenger.format(CombatPlus.getInstance().getLang().get().getString("gui_general"))),
    CONSOLE_UPDATE_NOT_FOUND(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("console_update_not_found"))),
    CONSOLE_UPDATE_DISABLED(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("console_update_disabled"))),
    CONSOLE_COMMANDS(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("console_commands"))),
    CONSOLE_INITIALIZE(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("console_initialize"))),
    CONSOLE_UNSUPPORTED_VERSION(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("console_unsupported_version"))),
    CONSOLE_UNSUPPORTED_SWEEP_ATTACK(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("console_unsupported_sweep_attack"))),
    CONSOLE_DISABLED(PREFIX.getMessage() + Messenger.format(CombatPlus.getInstance().getLang().get().getString("console_disabled")));

    private final String message;

    MsgType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}