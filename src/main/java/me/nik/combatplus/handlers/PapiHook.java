package me.nik.combatplus.handlers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.listeners.EnchantedGoldenApple;
import me.nik.combatplus.listeners.Enderpearl;
import me.nik.combatplus.listeners.GoldenApple;
import org.bukkit.entity.Player;

public class PapiHook extends PlaceholderExpansion {

    private final CombatPlus plugin;

    public PapiHook(CombatPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "combatplus";
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (player == null) {
            return "";
        }

        //Golden Apple Cooldown
        if (identifier.equalsIgnoreCase("goldenapple")) {
            return GoldenApple.papiCooldown;
        }

        //Enchanted Golden Apple Cooldown
        if (identifier.equalsIgnoreCase("enchantedgoldenapple")) {
            return EnchantedGoldenApple.papiCooldown;
        }

        //Enderpearl Cooldown
        if (identifier.equalsIgnoreCase("enderpearl")) {
            return Enderpearl.papiCooldown;
        }
        return null;
    }
}
