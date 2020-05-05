package me.nik.combatplus.api;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import org.bukkit.event.Listener;

public class Manager implements Listener {

    protected final CombatPlus plugin;

    public Manager(CombatPlus plugin) {
        this.plugin = plugin;
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

    protected void booleanSet(String path, boolean value) {
        Config.get().set(path, value);
    }

    protected void saveAndReload() {
        Config.save();
        Config.reload();
    }
}
