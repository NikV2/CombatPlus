package me.nik.combatplus.listeners;

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

import java.util.HashMap;
import java.util.UUID;

public class Blocking implements Listener {

    private final WorldUtils worldUtils = new WorldUtils();

    private final HashMap<UUID, Long> blocking = new HashMap<>();

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
        if (Config.Setting.SWORD_BLOCKING_IGNORE_SHIELDS.getBoolean() && holdsShield(e.getPlayer())) return;

        if (Config.Setting.SWORD_BLOCKING_CANCEL_SPRINTING.getBoolean() && p.isSprinting()) {
            p.setSprinting(false);
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(Config.Setting.SWORD_BLOCKING_EFFECT.getString()), Config.Setting.SWORD_BLOCKING_DURATION_TICKS.getInt(), Config.Setting.SWORD_BLOCKING_AMPLIFIER.getInt()));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Config.Setting.SWORD_BLOCKING_SLOW_DURATION_TICKS.getInt(), Config.Setting.SWORD_BLOCKING_SLOW_AMPLIFIER.getInt()));

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
            long secondsLeft = ((blocking.get(uuid) / 100) + Config.Setting.SWORD_BLOCKING_DURATION_TICKS.getInt()) - (System.currentTimeMillis() / 100);
            if (secondsLeft > 0) {
                e.setCancelled(true);
                Messenger.debug((Player) e.getDamager(), "&3Sword Blocking &f&l>> &6Cancelled: &a" + e.isCancelled() + " &6Blocking: &a" + blocking.containsKey(uuid));
                return;
            }
            blocking.remove(uuid);
        }
    }
}