package me.nik.combatplus.handlers;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.managers.MsgType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker extends BukkitRunnable implements Listener {

    private final CombatPlus plugin;

    public UpdateChecker(CombatPlus plugin) {
        this.plugin = plugin;
    }

    private String newVersion;

    @Override
    public void run() {
        try {
            newVersion = readLines();
        } catch (IOException e) {
            plugin.getLogger().warning("Couldn't check for updates, Is the server connected to the internet?");
            return;
        }

        if (!plugin.getDescription().getVersion().equals(newVersion)) {
            plugin.consoleMessage(MsgType.UPDATE_REMINDER.getMessage().replaceAll("%current%", plugin.getDescription().getVersion()).replaceAll("%new%", newVersion));
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        } else {
            plugin.consoleMessage(MsgType.CONSOLE_UPDATE_NOT_FOUND.getMessage());
        }
    }

    private String readLines() throws IOException {
        URLConnection connection = new URL("https://raw.githubusercontent.com/NikV2/CombatPlus/master/version.txt").openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.0");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        return reader.readLine();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPermission("cp.admin")) return;
        e.getPlayer().sendMessage(MsgType.UPDATE_REMINDER.getMessage().replaceAll("%current%", plugin.getDescription().getVersion()).replaceAll("%new%", newVersion));
    }
}