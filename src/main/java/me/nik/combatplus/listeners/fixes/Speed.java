package me.nik.combatplus.listeners.fixes;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Speed implements Listener {

    private double lastDist;
    private boolean lastOnGround;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent e) {
        if (e.getPlayer().getAllowFlight()) return;
        double distX = e.getTo().getX() - e.getFrom().getX();
        double distZ = e.getTo().getZ() - e.getFrom().getZ();
        double dist = (distX * distX) + (distZ * distZ);
        double lastDist = this.lastDist;
        this.lastDist = dist;

        boolean onGround = e.getPlayer().isOnGround();
        boolean lastOnGround = this.lastOnGround;
        this.lastOnGround = onGround;

        float friction = 0.91F;
        double shiftedLastDist = lastDist * friction;
        double equalness = dist - shiftedLastDist;
        double scaledEqualness = equalness * 138;

        if (!onGround && !lastOnGround) {
            if (scaledEqualness >= 1.0) {
                e.setCancelled(true);
                debug(e.getPlayer());
            }
        }
    }

    private void debug(Player player) {
        if (Config.get().getBoolean("settings.developer_mode") && player.hasPermission("cp.debug")) {
            player.sendMessage(Messenger.prefix("&3Speed &f&l>> &6Canceled: &atrue"));
        }
    }
}
