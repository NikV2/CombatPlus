package me.nik.combatplus.commands.subcommands;

import me.nik.combatplus.commands.SubCommand;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.entity.Player;

import java.util.List;

public class Reload extends SubCommand {

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
    public void perform(Player player, String[] args) {
        if (args.length == 1) {
            if (!player.hasPermission("cp.admin")) {
                player.sendMessage(Messenger.message("no_perm"));
            } else {
                player.sendMessage(Messenger.message("reloading"));
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                plugin.getServer().getPluginManager().enablePlugin(plugin);
                player.sendMessage(Messenger.message("reloaded"));
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
