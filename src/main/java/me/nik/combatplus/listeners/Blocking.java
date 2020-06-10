package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.WorldUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

public class Blocking implements Listener {

    private final CombatPlus plugin;

    private final WorldUtils worldUtils = new WorldUtils();

    private final int blockDuration = Config.get().getInt("combat.settings.sword_blocking.duration_ticks");
    private final PotionEffect effect = new PotionEffect(PotionEffectType.getByName(Config.get().getString("combat.settings.sword_blocking.effect")), blockDuration, Config.get().getInt("combat.settings.sword_blocking.amplifier"));
    private final int slowAmplifier = Config.get().getInt("combat.settings.sword_blocking.slow_amplifier");
    private final int slowDuration = Config.get().getInt("combat.settings.sword_blocking.slow_duration_ticks");
    private final boolean ignoreShields = Config.get().getBoolean("combat.settings.sword_blocking.ignore_shields");
    private final PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, slowDuration, slowAmplifier);
    private final boolean cancelSprinting = Config.get().getBoolean("combat.settings.sword_blocking.cancel_sprinting");

    private final HashSet<UUID> blocking = new HashSet<>();

    public Blocking(CombatPlus plugin) {
        this.plugin = plugin;
    }

    private static boolean interactiveBlock(Block block) {
        String b = block.getType().name();
        return b.contains("DOOR")
                || b.contains("TABLE")
                || b.contains("STAND")
                || b.contains("CHEST")
                || b.contains("GATE")
                || b.contains("DISPENSER")
                || b.contains("DROPPER")
                || b.contains("NOTE")
                || b.contains("REDSTONE")
                || b.contains("DIODE")
                || b.contains("FRAME")
                || b.contains("LEVER")
                || b.contains("SHULKER")
                || b.contains("FURNACE")
                || b.contains("BARREL");
    }

    private boolean holdsShield(Player p) {
        return p.getInventory().getItemInOffHand().getType().name().contains("SHIELD");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        Action action = e.getAction();
        if (!action.name().contains("RIGHT_CLICK")) return;

        final Block block = e.getClickedBlock();
        if (action == Action.RIGHT_CLICK_BLOCK && block != null && interactiveBlock(block)) return;
        if (!e.getItem().getType().name().contains("SWORD")) return;

        Player p = e.getPlayer();
        if (worldUtils.combatDisabledWorlds(p)) return;
        if (ignoreShields && holdsShield(e.getPlayer())) return;

        if (cancelSprinting && p.isSprinting()) {
            p.setSprinting(false);
        }
        p.addPotionEffect(effect);
        p.addPotionEffect(slowness);

        final UUID uuid = p.getUniqueId();
        if (!blocking.contains(uuid)) {
            blocking.add(uuid);
            runTask(uuid);
        }

        Messenger.debug(p, "&3Sword Blocking &f&l>> &6Action: &a" + action.toString() + " &6Holds Shield: &a" + holdsShield(p));
    }

    @EventHandler
    public void onInteractWhileBlocking(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        UUID uuid = e.getDamager().getUniqueId();
        if (blocking.contains(uuid)) {
            e.setCancelled(true);
            Messenger.debug((Player) e.getDamager(), "&3Sword Blocking &f&l>> &6Cancelled: &a" + e.isCancelled() + " &6Blocking: &a" + blocking.contains(uuid));
        }
    }

    private void runTask(UUID player) {
        new BukkitRunnable() {
            public void run() {
                if (blocking.contains(player)) {
                    blocking.remove(player);
                } else {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(plugin, blockDuration, blockDuration);
    }
}
