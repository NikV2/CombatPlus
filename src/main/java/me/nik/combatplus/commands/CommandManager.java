package me.nik.combatplus.commands;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.commands.subcommands.Menu;
import me.nik.combatplus.commands.subcommands.Reload;
import me.nik.combatplus.managers.MsgType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {

    private final CombatPlus plugin;

    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(CombatPlus plugin) {
        this.plugin = plugin;
        subCommands.add(new Menu(plugin));
        subCommands.add(new Reload(plugin));
    }

    private static final String INFO_MESSAGE = MsgType.PREFIX.getMessage()
            + ChatColor.GRAY + "You're running "
            + ChatColor.WHITE + CombatPlus.getInstance().getDescription().getName()
            + ChatColor.GRAY + " version "
            + ChatColor.RED + "v" + CombatPlus.getInstance().getDescription().getVersion()
            + ChatColor.GRAY + " by"
            + ChatColor.WHITE + " Nik";

    public ArrayList<SubCommand> getSubcommands() {
        return subCommands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {

            for (int i = 0; i < getSubcommands().size(); i++) {

                final SubCommand subCommand = getSubcommands().get(i);

                if (args[0].equalsIgnoreCase(subCommand.getName())) {

                    if (!subCommand.canConsoleExecute() && sender instanceof ConsoleCommandSender) {

                        sender.sendMessage(MsgType.CONSOLE_COMMANDS.getMessage());

                        return true;
                    }

                    if (!sender.hasPermission(subCommand.getPermission())) {

                        sender.sendMessage(MsgType.NO_PERMISSION.getMessage());

                        return true;
                    }

                    subCommand.perform(sender, args);

                    return true;
                }

                if (args[0].equalsIgnoreCase("help")) {

                    helpMessage(sender);

                    return true;
                }
            }

        } else {

            sender.sendMessage(INFO_MESSAGE);

            return true;
        }

        helpMessage(sender);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {

            List<String> subcommandsArgs = new ArrayList<>();

            for (int i = 0; i < getSubcommands().size(); i++) {
                subcommandsArgs.add(getSubcommands().get(i).getName());
            }

            return subcommandsArgs;
        }

        return null;
    }

    private void helpMessage(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(MsgType.PREFIX.getMessage() + ChatColor.GRAY + "Available Commands");
        sender.sendMessage("");
        this.subCommands.stream().filter(subCommand -> sender.hasPermission(subCommand.getPermission()))
                .forEach(subCommand -> sender.sendMessage(ChatColor.RED
                        + subCommand.getSyntax() + ChatColor.DARK_GRAY
                        + " - " + ChatColor.GRAY
                        + subCommand.getDescription()));
        sender.sendMessage("");
    }
}