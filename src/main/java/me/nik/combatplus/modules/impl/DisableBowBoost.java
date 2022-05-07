package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.Permissions;
import me.nik.combatplus.modules.Module;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class DisableBowBoost extends Module {

    public DisableBowBoost() {
        super(Config.Setting.DISABLE_ARROW_BOOST.getBoolean());
    }

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)
                || !(e.getDamager() instanceof Arrow)
                || (!Config.Setting.DISABLE_BYPASS_PERMISSIONS.getBoolean() && e.getEntity().hasPermission(Permissions.BYPASS_BOWBOOST.getPermission())))
            return;

        Arrow arrow = (Arrow) e.getDamager();

        ProjectileSource holder = arrow.getShooter();

        if (holder instanceof Player) {

            Player player = (Player) e.getEntity();

            Player holderPlayer = (Player) holder;

            if (player.getUniqueId().equals(holderPlayer.getUniqueId())) {

                e.setCancelled(true);

                arrow.remove();

                debug(player, "&6Cancelled");
            }
        }
    }
}
