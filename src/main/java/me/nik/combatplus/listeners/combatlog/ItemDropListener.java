package me.nik.combatplus.listeners.combatlog;

import me.nik.combatplus.managers.CombatLog;
import me.nik.combatplus.managers.MsgType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener extends CombatLog {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent e) {
        final Player p = e.getPlayer();

        if (!isTagged(p)) return;

        e.setCancelled(true);
        p.sendMessage(MsgType.COMBATLOG_ITEM_DROP.getMessage());
    }
}