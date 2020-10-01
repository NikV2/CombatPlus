package me.nik.combatplus.listeners.combatlog;

import me.nik.combatplus.managers.CombatLog;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class ItemPickListener extends CombatLog {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemPick(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        final Player p = (Player) e.getEntity();

        if (!isTagged(p)) return;

        e.setCancelled(true);
    }
}