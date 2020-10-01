package me.nik.combatplus.handlers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.listeners.EnchantedGoldenApple;
import me.nik.combatplus.listeners.Enderpearl;
import me.nik.combatplus.listeners.GoldenApple;
import me.nik.combatplus.managers.CombatLog;
import org.bukkit.OfflinePlayer;

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
    public String onRequest(OfflinePlayer player, String identifier) {

        switch (identifier) {
            case "goldenapple":
                return GoldenApple.getCooldown(player.getUniqueId());
            case "enchantedgoldenapple":
                return EnchantedGoldenApple.getCooldown(player.getUniqueId());
            case "enderpearl":
                return Enderpearl.getCooldown(player.getUniqueId());
            case "combatlog":
                return CombatLog.getCooldown(player.getUniqueId());
        }
        return null;
    }
}