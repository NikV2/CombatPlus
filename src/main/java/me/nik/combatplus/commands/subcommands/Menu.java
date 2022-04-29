package me.nik.combatplus.commands.subcommands;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.commands.SubCommand;
import me.nik.combatplus.gui.PlayerMenu;
import me.nik.combatplus.gui.menus.MainGui;
import me.nik.combatplus.managers.Permissions;
import org.bukkit.command.CommandSender;
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
    public String getPermission() {
        return Permissions.ADMIN.getPermission();
    }

    @Override
    protected int maxArguments() {
        return 1;
    }

    @Override
    public boolean canConsoleExecute() {
        return false;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        new MainGui(new PlayerMenu((Player) sender), plugin).open();
    }

    @Override
    public List<String> getSubcommandArguments(CommandSender sender, String[] args) {
        return null;
    }
}