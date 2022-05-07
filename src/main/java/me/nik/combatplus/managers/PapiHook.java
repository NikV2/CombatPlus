package me.nik.combatplus.managers;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.modules.impl.CombatLog;
import me.nik.combatplus.modules.impl.EnchantedGoldenAppleCooldown;
import me.nik.combatplus.modules.impl.EnderpearlCooldown;
import me.nik.combatplus.modules.impl.GoldenAppleCooldown;
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
                GoldenAppleCooldown goldenApple = (GoldenAppleCooldown) this.plugin.getModule(GoldenAppleCooldown.class);
                if (goldenApple == null) return "";
                return goldenApple.getCooldown(player.getUniqueId());

            case "enchantedgoldenapple":
                EnchantedGoldenAppleCooldown enchantedGoldenApple = (EnchantedGoldenAppleCooldown) this.plugin.getModule(EnchantedGoldenAppleCooldown.class);
                if (enchantedGoldenApple == null) return "";
                return enchantedGoldenApple.getCooldown(player.getUniqueId());

            case "enderpearl":
                EnderpearlCooldown enderpearl = (EnderpearlCooldown) this.plugin.getModule(EnderpearlCooldown.class);
                if (enderpearl == null) return "";
                return enderpearl.getCooldown(player.getUniqueId());

            case "combatlog":
                CombatLog combatLog = (CombatLog) this.plugin.getModule(CombatLog.class);
                if (combatLog == null) return "";
                return combatLog.getCooldown(player.getUniqueId());
        }

        return null;
    }
}