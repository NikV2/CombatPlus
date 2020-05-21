package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.handlers.UpdateChecker;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateReminder implements Listener {

    private final CombatPlus plugin;

    public UpdateReminder(CombatPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPermission("cp.admin")) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                e.getPlayer().sendMessage(Messenger.message("update_reminder").replaceAll("%current%", plugin.getDescription().getVersion()).replaceAll("%new%", UpdateChecker.VERSION));
            }
        }.runTaskLaterAsynchronously(plugin, 40);
    }
}