package me.nik.combatplus.api;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public class Manager implements Listener {

    protected final CombatPlus plugin;

    public Manager(CombatPlus plugin) {
        this.plugin = plugin;
    }

    protected boolean gappleDisabledWorlds(Player player) {
        for (String world : configStringList("golden_apple_cooldown.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    protected boolean combatDisabledWorlds(Player player) {
        for (String world : configStringList("combat.settings.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    protected boolean offHandDisabledWorlds(Player player) {
        for (String world : configStringList("disable_offhand.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    protected boolean noCraftingDisabledWorlds(Player player) {
        for (String world : configStringList("disabled_items.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    protected boolean itemFrameRotationDisabledWorlds(Player player) {
        for (String world : configStringList("disable_item_frame_rotation.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    protected boolean enderpearlDisabledWorlds(Player player) {
        for (String world : configStringList("enderpearl_cooldown.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    protected boolean customStatsWorlds(Player player) {
        for (String world : configStringList("custom.disabled_worlds")) {
            if (player.getWorld().getName().equalsIgnoreCase(world))
                return true;
        }
        return false;
    }

    protected void debug(Player player, String message) {
        if (configBoolean("settings.developer_mode") && player.hasPermission("cp.debug")) {
            player.sendMessage(Messenger.prefix(message));
        }
    }

    protected boolean configBoolean(String booleans) {
        return Config.get().getBoolean(booleans);
    }

    protected int configInt(String ints) {
        return Config.get().getInt(ints);
    }

    protected String configString(String string) {
        return Config.get().getString(string);
    }

    protected double configDouble(String doubles) {
        return Config.get().getDouble(doubles);
    }

    protected List<String> configStringList(String stringList) {
        return Config.get().getStringList(stringList);
    }

    protected void booleanSet(String path, boolean value) {
        Config.get().set(path, value);
    }

    protected boolean isAsync() {
        return configBoolean("settings.async");
    }

    protected void saveAndReload() {
        Config.save();
        Config.reload();
    }
}
