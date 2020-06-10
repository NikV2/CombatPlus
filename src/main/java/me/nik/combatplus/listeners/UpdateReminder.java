package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.handlers.UpdateChecker;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateReminder implements Listener {

    private final CombatPlus plugin;
    private final UpdateChecker updateChecker;

    public UpdateReminder(CombatPlus plugin, UpdateChecker updateChecker) {
        this.plugin = plugin;
        this.updateChecker = updateChecker;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPermission("cp.admin")) return;
        String newVersion = updateChecker.getNewVersion();
        e.getPlayer().sendMessage(Messenger.message(MsgType.UPDATE_REMINDER).replaceAll("%current%", plugin.getDescription().getVersion()).replaceAll("%new%", newVersion));
    }
}