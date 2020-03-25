package me.nik.combatplus.listeners;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class BowBoost implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onArrowDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Arrow)) return;
        Player player = (Player) e.getEntity();
        Arrow arrow = (Arrow) e.getDamager();
        ProjectileSource holder = arrow.getShooter();
        if (holder instanceof Player) {
            Player holderPlayer = (Player) holder;
            if (player.getUniqueId().equals(holderPlayer.getUniqueId())) {
                e.setCancelled(true);
                if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
                    if (player.hasPermission("cp.admin")) {
                        boolean isCanceled = e.isCancelled();
                        double damageDealt = e.getDamage();
                        player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Bow Boost Canceled: " + isCanceled + ChatColor.GREEN + " Damage Dealt: " + damageDealt));
                    }
                }
            }
        }
    }
}
