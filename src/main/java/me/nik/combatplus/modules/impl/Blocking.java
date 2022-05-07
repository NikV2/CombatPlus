package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.custom.ExpiringMap;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.UUID;

public class Blocking extends Module {

    private final ExpiringMap<UUID, Long> blocking = new ExpiringMap<>(5000L);

    public Blocking() {
        super(Config.Setting.SWORD_BLOCKING_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlock(PlayerInteractEvent e) {
        if (e.getItem() == null
                || !e.getItem().getType().name().endsWith("_SWORD")
                || (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)) return;

        Action action = e.getAction();

        final Block block = e.getClickedBlock();

        if (action == Action.RIGHT_CLICK_BLOCK && block != null) return;

        if (block != null && block.getType().isInteractable()) return;

        Player player = e.getPlayer();

        if (Config.Setting.SWORD_BLOCKING_IGNORE_SHIELDS.getBoolean() && player.getInventory().getItemInOffHand().getType().name().contains("SHIELD"))
            return;

        player.addPotionEffects(Arrays.asList(
                new PotionEffect(PotionEffectType.getByName(Config.Setting.SWORD_BLOCKING_EFFECT.getString()), Config.Setting.SWORD_BLOCKING_DURATION_TICKS.getInt(), Config.Setting.SWORD_BLOCKING_AMPLIFIER.getInt()),
                new PotionEffect(PotionEffectType.SLOW, Config.Setting.SWORD_BLOCKING_SLOW_DURATION_TICKS.getInt(), Config.Setting.SWORD_BLOCKING_SLOW_AMPLIFIER.getInt())
        ));

        final UUID uuid = player.getUniqueId();

        if (!blocking.containsKey(uuid)) blocking.put(uuid, System.currentTimeMillis());

        debug(player, "&6Action: &a" + action.name());
    }

    @EventHandler
    public void onInteractWhileBlocking(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)
                || e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK
                || !this.blocking.containsKey(e.getDamager().getUniqueId())) return;

        UUID uuid = e.getDamager().getUniqueId();

        long secondsLeft = ((this.blocking.get(uuid) / 100L) + Config.Setting.SWORD_BLOCKING_DURATION_TICKS.getInt()) - (System.currentTimeMillis() / 100L);

        if (secondsLeft > 0L) {

            e.setCancelled(true);

            debug((Player) e.getDamager(), "&6Cancelled");

        } else this.blocking.remove(uuid);
    }
}