package me.nik.combatplus.modules;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.Permissions;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class Module implements Listener {

    private final boolean enabled;
    private final String name;

    public Module(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void init() {
        if (!enabled) return;
        Bukkit.getPluginManager().registerEvents(this, CombatPlus.getInstance());
    }

    public void disInit() {
        if (!enabled) return;
        HandlerList.unregisterAll(this);
    }

    protected void debug(Player player, String information) {
        if (Config.Setting.DEVELOPER_MODE.getBoolean() && player.hasPermission(Permissions.DEBUG.getPermission())) {
            player.sendMessage(MsgType.PREFIX.getMessage() + this.name + Messenger.format(" &f&l>> &r" + information));
        }
    }
}