package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
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

import java.util.HashMap;
import java.util.UUID;

public class Blocking implements Listener {

    private final WorldUtils worldUtils;

    private final int blockDuration;
    private final PotionEffect effect;
    private final boolean ignoreShields;
    private final PotionEffect slowness;
    private final boolean cancelSprinting;

    private final HashMap<UUID, Long> blocking = new HashMap<>();

    public Blocking(CombatPlus plugin) {
        this.worldUtils = new WorldUtils(plugin);
        this.blockDuration = plugin.getConfig().getInt("combat.settings.sword_blocking.duration_ticks");
        this.effect = new PotionEffect(PotionEffectType.getByName(plugin.getConfig().getString("combat.settings.sword_blocking.effect")), blockDuration, plugin.getConfig().getInt("combat.settings.sword_blocking.amplifier"));
        int slowAmplifier = plugin.getConfig().getInt("combat.settings.sword_blocking.slow_amplifier");
        int slowDuration = plugin.getConfig().getInt("combat.settings.sword_blocking.slow_duration_ticks");
        this.ignoreShields = plugin.getConfig().getBoolean("combat.settings.sword_blocking.ignore_shields");
        this.slowness = new PotionEffect(PotionEffectType.SLOW, slowDuration, slowAmplifier);
        this.cancelSprinting = plugin.getConfig().getBoolean("combat.settings.sword_blocking.cancel_sprinting");
    }

    private boolean interactiveBlock(Block block) {
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
        if (!blocking.containsKey(uuid)) {
            blocking.put(uuid, System.currentTimeMillis());
        }
        Messenger.debug(p, "&3Sword Blocking &f&l>> &6Action: &a" + action.toString() + " &6Holds Shield: &a" + holdsShield(p));
    }

    @EventHandler
    public void onInteractWhileBlocking(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        UUID uuid = e.getDamager().getUniqueId();
        if (blocking.containsKey(uuid)) {
            long secondsLeft = ((blocking.get(uuid) / 100) + blockDuration) - (System.currentTimeMillis() / 100);
            if (secondsLeft > 0) {
                e.setCancelled(true);
                Messenger.debug((Player) e.getDamager(), "&3Sword Blocking &f&l>> &6Cancelled: &a" + e.isCancelled() + " &6Blocking: &a" + blocking.containsKey(uuid));
                return;
            }
            blocking.remove(uuid);
        }
    }
}
