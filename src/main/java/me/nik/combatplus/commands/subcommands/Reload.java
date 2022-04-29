package me.nik.combatplus.commands.subcommands;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.commands.SubCommand;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.managers.Permissions;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Reload extends SubCommand {

    private final CombatPlus plugin;

    public Reload(CombatPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload the plugin";
    }

    @Override
    public String getSyntax() {
        return "/cp reload";
    }

    @Override
    public String getPermission() {
        return Permissions.ADMIN.getPermission();
    }

    @Override
    protected int maxArguments() {
        return 1;
    }

    @Override
    public boolean canConsoleExecute() {
        return true;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        plugin.onDisable();
        plugin.onEnable();
        sender.sendMessage(MsgType.RELOADED.getMessage());
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}