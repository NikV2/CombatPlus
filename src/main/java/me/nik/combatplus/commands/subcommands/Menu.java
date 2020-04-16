package me.nik.combatplus.commands.subcommands;

import me.nik.combatplus.api.GUIManager;
import me.nik.combatplus.commands.SubCommand;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.entity.Player;

import java.util.List;

public class Menu extends SubCommand {

    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public String getDescription() {
        return "Open up the CombatPlus Menu";
    }

    @Override
    public String getSyntax() {
        return "/cp menu";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1) {
            if (!player.hasPermission("cp.admin")) {
                player.sendMessage(Messenger.message("no_perm"));
            } else {
                new GUIManager().openMainGUI(player);
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
