package me.nik.combatplus.listeners.fixes;

import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.MiscUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Criticals implements Listener {

    /*
     Patches the Criticals Cheat if a player crits while he's on the ground
     */

    @EventHandler
    public void onCritical(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player) || e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        Player p = (Player) e.getDamager();
        Location pLoc = p.getLocation();
        if (MiscUtils.isCritical(p)) {
            if ((pLoc.getY() % 1.0 == 0 || pLoc.getY() % 0.5 == 0) && pLoc.clone().subtract(0, 1.0, 0).getBlock().getType().isSolid()) {
                e.setCancelled(true);
                Messenger.debug(p, "&3Criticals &f&l>> &6Canceled: &a" + e.isCancelled() + " &6Invalid: &atrue");
            }
        }
    }
}
