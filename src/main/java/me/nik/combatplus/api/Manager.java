package me.nik.combatplus.api;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.List;

public abstract class Manager implements Listener {
    public Plugin plugin = CombatPlus.getPlugin(CombatPlus.class);

    public boolean gappleDisabledWorlds(Player player) {
        for (String world : configStringList("general.settings.golden_apple_cooldown.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public boolean combatDisabledWorlds(Player player) {
        for (String world : configStringList("combat.settings.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public boolean noCraftingDisabledWorlds(World disWorld) {
        for (String world : configStringList("general.settings.disabled_items.disabled_worlds")) {
            if (disWorld.getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public boolean offHandDisabledWorlds(Player player) {
        for (String world : configStringList("general.settings.disable_offhand.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public boolean debug(Player player) {
        if (configBoolean("settings.developer_mode")) {
            return player.hasPermission("cp.admin");
        }
        return false;
    }

    public boolean configBoolean(String booleans) {
        return Config.get().getBoolean(booleans);
    }

    public int configInt(String ints) {
        return Config.get().getInt(ints);
    }

    public double configDouble(String doubles) {
        return Config.get().getDouble(doubles);
    }

    public String configString(String string) {
        return Config.get().getString(string);
    }

    public List<String> configStringList(String stringList) {
        return Config.get().getStringList(stringList);
    }

    public void booleanSet(String path, boolean value) {
        Config.get().set(path, value);
    }

    public boolean isAsync() {
        return configBoolean("settings.async");
    }

    public void registerEvent(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
