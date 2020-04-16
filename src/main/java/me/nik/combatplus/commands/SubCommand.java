package me.nik.combatplus.commands;

import me.nik.combatplus.CombatPlus;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    protected CombatPlus plugin = CombatPlus.getInstance();

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(Player player, String[] args);

    public abstract List<String> getSubcommandArguments(Player player, String[] args);
}