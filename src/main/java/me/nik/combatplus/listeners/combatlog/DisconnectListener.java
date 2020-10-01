package me.nik.combatplus.listeners.combatlog;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.CombatLog;
import me.nik.combatplus.managers.MsgType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

public class DisconnectListener extends CombatLog {

    private final boolean broadcast = Config.Setting.COMBATLOG_BROADCAST.getBoolean();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {

        final Player p = e.getPlayer();

        if (!isTagged(p)) return;

        p.setHealth(0);
        unTagPlayer(p);

        if (broadcast) {
            Bukkit.broadcastMessage(MsgType.COMBATLOG_BROADCAST.getMessage().replace("%player%", p.getName()));
        }
    }
}