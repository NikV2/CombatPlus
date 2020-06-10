package me.nik.combatplus.commands.subcommands;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.commands.SubCommand;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.utils.Messenger;
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
        return "cp.admin";
    }

    @Override
    public boolean canConsoleExecute() {
        return true;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(Messenger.message(MsgType.RELOADING));
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            plugin.getServer().getPluginManager().enablePlugin(plugin);
            sender.sendMessage(Messenger.message(MsgType.RELOADED));
        }
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}
