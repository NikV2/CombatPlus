package me.nik.combatplus.modules;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.managers.Permissions;
import me.nik.combatplus.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class Module implements Listener {

    private final boolean enabled;
    private final String name;

    public Module(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void load() {
        if (!this.enabled) return;
        Bukkit.getPluginManager().registerEvents(this, CombatPlus.getInstance());
    }

    public void shutdown() {
        if (!this.enabled) return;
        HandlerList.unregisterAll(this);
    }

    protected void debug(Player player, String information) {
        if (Config.Setting.DEVELOPER_MODE.getBoolean() && player.hasPermission(Permissions.DEBUG.getPermission())) {
            player.sendMessage(MsgType.PREFIX.getMessage() + this.name + ChatUtils.format(" &f&l>> &r" + information));
        }
    }
}