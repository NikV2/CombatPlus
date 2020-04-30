package me.nik.combatplus.listeners.fixes;

import me.nik.combatplus.api.Manager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

public class KillAura extends Manager {

    private final HashSet<UUID> timer = new HashSet<>();
    private final String entity = configString("advanced.settings.kill_aura.entity");
    private final int ticks = configInt("advanced.settings.kill_aura.teleport_ticks");
    private final int duration = configInt("advanced.settings.kill_aura.ticks_duration");
    private final boolean ignoreCreative = configBoolean("advanced.settings.kill_aura.ignore_creative");

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onCombat(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        if (ignoreCreative && p.getGameMode() == GameMode.CREATIVE) return;
        if (timer.contains(p.getUniqueId())) return;
        spawnNPC(p);
    }

    private void spawnNPC(Player p) {
        timer.add(p.getUniqueId());
        Location loc = p.getLocation();
        World world = p.getWorld();
        Entity npc = world.spawnEntity(loc, EntityType.valueOf(entity));
        npc.setInvulnerable(true);
        npc.setGravity(false);
        npc.setSilent(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (npc.isValid()) {
                    npc.teleport(p.getLocation().add(p.getLocation().getDirection().multiply(-3)));
                } else {
                    timer.remove(p.getUniqueId());
                    cancel();
                }
            }
        }.runTaskTimer(plugin, ticks, ticks);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (npc.isValid()) {
                    npc.remove();
                }
                timer.remove(p.getUniqueId());
            }
        }.runTaskLaterAsynchronously(plugin, duration);
    }
}
