package me.nik.combatplus.handlers;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.listeners.UpdateReminder;
import me.nik.combatplus.managers.MsgType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateChecker extends BukkitRunnable {

    private final CombatPlus plugin;

    public UpdateChecker(CombatPlus plugin) {
        this.plugin = plugin;
    }

    private String newVersion;

    @Override
    public void run() {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=76788").openConnection();
            String version = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

            if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                newVersion = version;
                plugin.consoleMessage(MsgType.UPDATE_REMINDER.getMessage().replaceAll("%current%", plugin.getDescription().getVersion()).replaceAll("%new%", newVersion));
                plugin.registerEvent(new UpdateReminder(plugin, this));
            } else {
                plugin.consoleMessage(MsgType.CONSOLE_UPDATE_NOT_FOUND.getMessage());
            }
        } catch (IOException ignored) {
        }
    }

    public String getNewVersion() {
        return newVersion;
    }
}