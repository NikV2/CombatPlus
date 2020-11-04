package me.nik.combatplus.modules.impl;

import me.nik.combatplus.Permissions;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.WorldUtils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class BowBoost extends Module {

    public BowBoost() {
        super("Bow Boost", Config.Setting.DISABLE_ARROW_BOOST.getBoolean());
    }

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Arrow)) return;
        Player p = (Player) e.getEntity();
        if (WorldUtils.combatDisabledWorlds(p)) return;
        if (p.hasPermission(Permissions.BYPASS_BOWBOOST.getPermission())) return;
        Arrow arrow = (Arrow) e.getDamager();
        ProjectileSource holder = arrow.getShooter();
        if (holder instanceof Player) {
            Player holderPlayer = (Player) holder;
            if (p.getUniqueId().equals(holderPlayer.getUniqueId())) {
                e.setCancelled(true);
                debug(p, "&6Cancelled: &a" + e.isCancelled());
            }
        }
    }
}
