package me.nik.combatplus.listeners.fixes;

import me.nik.combatplus.api.Manager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class HealthSpoof extends Manager {

    @EventHandler
    public void onCloseToDeath(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (shouldDie(e, p)) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    if (!p.isDead()) {
                        p.setHealth(0);
                        debug(p, "&3Health Spoof &f&l>> &6Ensured Death: &a" + p.isDead());
                    } else cancel();
                }
            }.runTaskLater(plugin, 1);
        }
    }

    private boolean shouldDie(EntityDamageEvent e, Player p) {
        double health = p.getHealth();
        double damage = e.getFinalDamage();
        return damage >= health || health < 1;
    }
}
