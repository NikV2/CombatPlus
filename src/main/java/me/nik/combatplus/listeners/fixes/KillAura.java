package me.nik.combatplus.listeners.fixes;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

public class KillAura implements Listener {

    private final CombatPlus plugin;

    private final HashSet<UUID> timer = new HashSet<>();
    private final String entity = Config.get().getString("advanced.settings.kill_aura.entity");
    private final int ticks = Config.get().getInt("advanced.settings.kill_aura.teleport_ticks");
    private final int duration = Config.get().getInt("advanced.settings.kill_aura.ticks_duration");
    private final boolean ignoreCreative = Config.get().getBoolean("advanced.settings.kill_aura.ignore_creative");
    private final int range = Config.get().getInt("advanced.settings.kill_aura.range");

    public KillAura(CombatPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onCombat(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        if (ignoreCreative && p.getGameMode() == GameMode.CREATIVE) return;
        if (timer.contains(p.getUniqueId())) return;
        spawnNPC(p);
        Messenger.debug(p, "&3Kill Aura &f&l>> &6Spawned NPC: &atrue &6Entity: &a" + entity + " &6Duration: &a" + duration + " Ticks &6Range: &a" + range);
    }

    private void spawnNPC(Player p) {
        timer.add(p.getUniqueId());
        Location loc = p.getLocation();
        World world = p.getWorld();
        Entity npc = world.spawnEntity(loc, EntityType.valueOf(entity));
        npc.setInvulnerable(true);
        try {
            npc.setGravity(false);
        } catch (NoSuchMethodError ignored) {
        }
        npc.setSilent(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (npc.isValid()) {
                    npc.teleport(p.getLocation().add(p.getLocation().getDirection().multiply(range)));
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
