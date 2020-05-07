package me.nik.combatplus.listeners;

import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.WorldUtils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class BowBoost implements Listener {

    private final WorldUtils worldUtils = new WorldUtils();

    /*
     Disables the ability to Boost yourself up using a Bow, By shooting yourself
     This Listener prevents players hitting themselves with a bow
     */

    @EventHandler(priority = EventPriority.NORMAL)
    public void onArrowDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Arrow)) return;
        Player player = (Player) e.getEntity();
        if (worldUtils.combatDisabledWorlds(player)) return;
        if (player.hasPermission("cp.bypass.bowboost")) return;
        Arrow arrow = (Arrow) e.getDamager();
        ProjectileSource holder = arrow.getShooter();
        if (holder instanceof Player) {
            Player holderPlayer = (Player) holder;
            if (player.getUniqueId().equals(holderPlayer.getUniqueId())) {
                e.setCancelled(true);
                Messenger.debug(player, "&3Bow Boost &f&l>> &6Canceled: &a" + e.isCancelled());
            }
        }
    }
}
