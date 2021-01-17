package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.WorldUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Blocking extends Module {

    private final Map<UUID, Long> blocking;

    public Blocking() {
        super("Sword Blocking", Config.Setting.SWORD_BLOCKING_ENABLED.getBoolean());
        this.blocking = new HashMap<>();
    }

    private boolean holdsShield(Player p) {
        return p.getInventory().getItemInOffHand().getType().name().contains("SHIELD");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlock(final PlayerInteractEvent e) {
        if (e.getItem() == null || !e.getAction().name().contains("RIGHT_CLICK")) return;

        Action action = e.getAction();

        final Block block = e.getClickedBlock();

        if (action == Action.RIGHT_CLICK_BLOCK && block != null) return;

        if (!e.getItem().getType().name().contains("SWORD")) return;

        if (block != null && block.getType().isInteractable()) return;

        Player p = e.getPlayer();

        if (WorldUtils.combatDisabledWorlds(p)) return;

        boolean hasShield = holdsShield(p);

        if (Config.Setting.SWORD_BLOCKING_IGNORE_SHIELDS.getBoolean() && hasShield) return;

        if (Config.Setting.SWORD_BLOCKING_CANCEL_SPRINTING.getBoolean() && p.isSprinting()) {
            p.setSprinting(false);
        }

        p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(Config.Setting.SWORD_BLOCKING_EFFECT.getString()), Config.Setting.SWORD_BLOCKING_DURATION_TICKS.getInt(), Config.Setting.SWORD_BLOCKING_AMPLIFIER.getInt()));

        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Config.Setting.SWORD_BLOCKING_SLOW_DURATION_TICKS.getInt(), Config.Setting.SWORD_BLOCKING_SLOW_AMPLIFIER.getInt()));

        final UUID uuid = p.getUniqueId();

        if (!blocking.containsKey(uuid)) blocking.put(uuid, System.currentTimeMillis());

        debug(p, "&6Action: &a" + action.toString() + " &6Holds Shield: &a" + holdsShield(p));
    }

    @EventHandler
    public void onInteractWhileBlocking(final EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        UUID uuid = e.getDamager().getUniqueId();
        if (blocking.containsKey(uuid)) {
            long secondsLeft = ((blocking.get(uuid) / 100) + Config.Setting.SWORD_BLOCKING_DURATION_TICKS.getInt()) - (System.currentTimeMillis() / 100);
            if (secondsLeft > 0) {
                e.setCancelled(true);
                debug((Player) e.getDamager(), "&6Cancelled: &a" + e.isCancelled() + " &6Blocking: &a" + blocking.containsKey(uuid));
                return;
            }
            blocking.remove(uuid);
        }
    }

    @EventHandler
    public void cleanCache(final PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if (!this.blocking.containsKey(uuid)) return;
        this.blocking.remove(uuid);
    }
}