package me.nik.combatplus.listeners.fixes;

import me.nik.combatplus.utils.Messenger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class BadPackets implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPitch(PlayerMoveEvent e) {
        if (Math.abs(e.getPlayer().getLocation().getPitch()) > 90.0) {
            e.setCancelled(true);
            Messenger.debug(e.getPlayer(), "&3Bad Packets &f&l>> &6Illegal Pitch: &a" + e.getPlayer().getLocation().getPitch());
        }
    }
}
