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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blocking implements Listener {

    private final WorldUtils worldUtils = new WorldUtils();

    private final PotionEffect effect = new PotionEffect(PotionEffectType.getByName(Config.get().getString("combat.settings.sword_blocking.effect")), Config.get().getInt("combat.settings.sword_blocking.duration_ticks"), Config.get().getInt("combat.settings.sword_blocking.amplifier"));
    private final int slowAmplifier = Config.get().getInt("combat.settings.sword_blocking.slow_amplifier");
    private final int slowDuration = Config.get().getInt("combat.settings.sword_blocking.slow_duration_ticks");
    private final boolean ignoreShields = Config.get().getBoolean("combat.settings.sword_blocking.ignore_shields");
    private final PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, slowDuration, slowAmplifier);
    private final boolean cancelSprinting = Config.get().getBoolean("combat.settings.sword_blocking.cancel_sprinting");

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
        Messenger.debug(p, "&3Sword Blocking &f&l>> &6Action: &a" + action.toString() + " &6Holds Shield: &a" + holdsShield(p));
    }

    private boolean holdsShield(Player p) {
        return p.getInventory().getItemInOffHand().getType().name().contains("SHIELD");
    }
}
