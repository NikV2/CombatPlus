package me.nik.combatplus.api;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public class Manager implements Listener {

    protected CombatPlus plugin = CombatPlus.getInstance();

    public boolean gappleDisabledWorlds(Player player) {
        for (String world : configStringList("golden_apple_cooldown.disabled_worlds")) {
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

    public boolean offHandDisabledWorlds(Player player) {
        for (String world : configStringList("disable_offhand.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public boolean noCraftingDisabledWorlds(Player player) {
        for (String world : configStringList("disabled_items.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    public boolean itemFrameRotationDisabledWorlds(Player player) {
        for (String world : configStringList("disable_item_frame_rotation.disabled_worlds")) {
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

    public boolean ignoreSweepingEdge() {
        return configBoolean("combat.settings.disable_sweep_attacks.ignore_sweeping_edge");
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

    public List<String> configStringList(String stringList) {
        return Config.get().getStringList(stringList);
    }

    public void booleanSet(String path, boolean value) {
        Config.get().set(path, value);
    }

    public boolean isAsync() {
        return configBoolean("settings.async");
    }

    public void saveAndReload() {
        Config.save();
        Config.reload();
    }

    public void registerEvent(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
