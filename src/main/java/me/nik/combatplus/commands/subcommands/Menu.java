package me.nik.combatplus.commands.subcommands;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.commands.SubCommand;
import me.nik.combatplus.gui.PlayerMenuUtility;
import me.nik.combatplus.gui.menus.MainGui;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.entity.Player;

import java.util.List;

public class Menu extends SubCommand {

    private final CombatPlus plugin;

    public Menu(CombatPlus plugin) {
        this.plugin = plugin;
    }

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
                new MainGui(new PlayerMenuUtility(player), plugin).open();
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
