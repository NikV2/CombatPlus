package me.nik.combatplus.listeners.fixes;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class HealthSpoof implements Listener {

    private final CombatPlus plugin;

    public HealthSpoof(CombatPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCloseToDeath(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (holdsTotem(p)) return;
        double health = p.getHealth();
        double damage = e.getFinalDamage();
        if (damage >= health || health < 1) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    if (!p.isDead()) {
                        p.setHealth(0);
                        Messenger.debug(p, "&3Health Spoof &f&l>> &6Ensured Death: &a" + p.isDead());
                    } else cancel();
                }
            }.runTaskLater(plugin, 1);
        }
    }

    private boolean holdsTotem(Player p) {
        return p.getInventory().getItemInMainHand().getType().name().contains("TOTEM") || p.getInventory().getItemInOffHand().getType().name().contains("TOTEM");
    }
}
