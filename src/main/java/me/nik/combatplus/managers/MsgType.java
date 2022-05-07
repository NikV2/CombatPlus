package me.nik.combatplus.managers;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.utils.ChatUtils;

public enum MsgType {
    PREFIX(ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("prefix"))),
    NO_PERMISSION(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("no_perm"))),
    RELOADED(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("reloaded"))),
    UPDATE_REMINDER(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("update_reminder"))),
    GOLDEN_APPLE_COOLDOWN(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("golden_apple_cooldown"))),
    GOLDEN_APPLE_COOLDOWN_ACTIONBAR(ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("golden_apple_cooldown_actionbar"))),
    ENCHANTED_GOLDEN_APPLE_COOLDOWN(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("enchanted_golden_apple_cooldown"))),
    ENCHANTED_GOLDEN_APPLE_COOLDOWN_ACTIONBAR(ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("enchanted_golden_apple_cooldown_actionbar"))),
    ENDERPEARL_COOLDOWN(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("enderpearl_cooldown"))),
    ENDERPEARL_COOLDOWN_ACTIONBAR(ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("enderpearl_cooldown_actionbar"))),
    COMBATLOG_TAGGED(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("combatlog_tagged"))),
    COMBATLOG_ACTIONBAR(ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("combatlog_actionbar"))),
    COMBATLOG_BROADCAST(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("combatlog_broadcast"))),
    COMBATLOG_ITEM_DROP(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("combatlog_item_drop"))),
    COMBATLOG_TELEPORT(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("combatlog_teleport"))),
    COMBATLOG_COMMAND(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("combatlog_command"))),
    GUI_MAIN(ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("gui_main"))),
    GUI_PLUGIN(ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("gui_plugin"))),
    GUI_COMBAT(ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("gui_combat"))),
    GUI_GENERAL(ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("gui_general"))),
    CONSOLE_UPDATE_NOT_FOUND(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("console_update_not_found"))),
    CONSOLE_COMMANDS(PREFIX.getMessage() + ChatUtils.format(CombatPlus.getInstance().getLang().get().getString("console_commands")));

    private final String message;

    MsgType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}