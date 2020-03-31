package me.nik.combatplus.commands;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.commands.subcommands.Menu;
import me.nik.combatplus.commands.subcommands.Reload;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {
    public Plugin plugin = CombatPlus.getPlugin(CombatPlus.class);

    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new Menu());
        subCommands.add(new Reload());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                helpMessage(sender);
            } else if (args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(Messenger.message("reloading"));
                sender.getServer().getPluginManager().disablePlugin(plugin);
                sender.getServer().getPluginManager().enablePlugin(plugin);
                sender.sendMessage(Messenger.message("reloaded"));
                return true;
            } else if (args[0].equalsIgnoreCase("help")) {
                helpMessage(sender);
                return true;
            } else {
                sender.sendMessage(Messenger.message("console_message"));
                return true;
            }
        } else {
            Player p = (Player) sender;
            if (args.length > 0) {
                for (int i = 0; i < getSubcommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {
                        getSubcommands().get(i).perform(p, args);
                        return true;
                    } else if (args[0].equalsIgnoreCase("help")) {
                        helpMessage(p);
                        return true;
                    }
                }
            } else {
                pluginInfo(p);
                return true;
            }
        }
        return true;
    }

    public ArrayList<SubCommand> getSubcommands() {
        return subCommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            ArrayList<String> subcommandsArgs = new ArrayList<>();
            for (int i = 0; i < getSubcommands().size(); i++) {
                subcommandsArgs.add(getSubcommands().get(i).getName());
            }
            return subcommandsArgs;
        }
        return null;
    }

    private void helpMessage(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(Messenger.prefix(ChatColor.GRAY + "Available Commands"));
        sender.sendMessage("");
        for (int i = 0; i < getSubcommands().size(); i++) {
            sender.sendMessage(ChatColor.RED + getSubcommands().get(i).getSyntax() + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + getSubcommands().get(i).getDescription());
        }
        sender.sendMessage("");
    }

    private void pluginInfo(CommandSender sender) {
        sender.sendMessage(Messenger.prefix(ChatColor.GRAY + "You're running " + ChatColor.WHITE + plugin.getDescription().getName() + ChatColor.GRAY + " version " + ChatColor.RED + "v" + plugin.getDescription().getVersion() + ChatColor.GRAY + " by" + ChatColor.WHITE + " Nik"));
    }
}
