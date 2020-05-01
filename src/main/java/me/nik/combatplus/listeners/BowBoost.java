package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.Manager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class BowBoost extends Manager {

    public BowBoost(CombatPlus plugin) {
        super(plugin);
    }

    // Disables the ability to Boost yourself up using a Bow, By shooting yourself
    // This Listener prevents players hitting themselves with a bow

    @EventHandler(priority = EventPriority.NORMAL)
    public void onArrowDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Arrow)) return;
        Player player = (Player) e.getEntity();
        if (player.hasPermission("cp.bypass.bowboost")) return;
        Arrow arrow = (Arrow) e.getDamager();
        if (combatDisabledWorlds(player)) return;
        ProjectileSource holder = arrow.getShooter();
        if (holder instanceof Player) {
            Player holderPlayer = (Player) holder;
            if (player.getUniqueId().equals(holderPlayer.getUniqueId())) {
                e.setCancelled(true);
                debug(player, "&3Bow Boost &f&l>> &6Canceled: &a" + e.isCancelled());
            }
        }
    }
}
