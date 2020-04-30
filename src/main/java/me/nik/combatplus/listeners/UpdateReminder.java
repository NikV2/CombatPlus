package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.handlers.UpdateChecker;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateReminder implements Listener {

    private final CombatPlus plugin = CombatPlus.getInstance();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPermission("cp.admin")) return;
        if (!UpdateChecker.hasUpdate) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                e.getPlayer().sendMessage(Messenger.message("update_reminder"));
            }
        }.runTaskLaterAsynchronously(plugin, 20);
    }
}
