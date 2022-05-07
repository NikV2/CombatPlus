package me.nik.combatplus.managers;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.utils.ChatUtils;
import org.bukkit.Bukkit;
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

            URLConnection connection = new URL("https://raw.githubusercontent.com/NikV2/CombatPlus/master/version.txt").openConnection();

            connection.addRequestProperty("User-Agent", "Mozilla/4.0");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            final String line = reader.readLine();

            reader.close();

            this.newVersion = line;

        } catch (IOException e) {
            plugin.getLogger().warning("Couldn't check for updates, Is the server connected to the internet?");
            return;
        }

        if (!plugin.getDescription().getVersion().equals(newVersion)) {
            ChatUtils.consoleMessage(MsgType.UPDATE_REMINDER.getMessage()
                    .replace("%current%", plugin.getDescription().getVersion())
                    .replace("%new%", newVersion));

            Bukkit.getPluginManager().registerEvents(this, plugin);
        } else ChatUtils.consoleMessage(MsgType.CONSOLE_UPDATE_NOT_FOUND.getMessage());
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        if (!e.getPlayer().hasPermission(Permissions.ADMIN.getPermission())) return;

        e.getPlayer().sendMessage(MsgType.UPDATE_REMINDER.getMessage()
                .replace("%current%", plugin.getDescription().getVersion())
                .replace("%new%", newVersion));
    }
}