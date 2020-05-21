package me.nik.combatplus.listeners.fixes;

import me.nik.combatplus.utils.Messenger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoFall implements Listener {

    private boolean lastOnGround, lastLastOnGround;

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getPlayer().getAllowFlight()) return;
        boolean onGround = isNearGround(e.getTo());

        boolean lastOnGround = this.lastOnGround;
        this.lastOnGround = onGround;

        boolean lastLastOnGround = this.lastLastOnGround;
        this.lastLastOnGround = lastOnGround;

        if (!onGround && !lastOnGround && !lastLastOnGround) {
            if (e.getPlayer().isOnGround()) {
                e.setCancelled(true);
                Messenger.debug(e.getPlayer(), "&3No Fall &f&l>> &6Canceled: &atrue");
            }
        }
    }

    private boolean isNearGround(Location location) {
        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (location.clone().add(x, -0.5001, z).getBlock().getType() != Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }
}
