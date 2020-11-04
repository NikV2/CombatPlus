package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.MathUtils;
import me.nik.combatplus.utils.TaskUtils;
import me.nik.combatplus.utils.WorldUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerRegen extends Module {

    private final Map<UUID, Long> healTimes;

    public PlayerRegen() {
        super("Old Regen", Config.Setting.OLD_REGEN.getBoolean());
        this.healTimes = new HashMap<>();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegen(final EntityRegainHealthEvent e) {
        if (e.getEntityType() != EntityType.PLAYER || e.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED)
            return;

        final Player p = (Player) e.getEntity();

        if (WorldUtils.combatDisabledWorlds(p)) return;

        e.setCancelled(true);
        final UUID playerID = p.getUniqueId();
        double playerHealth = p.getHealth();
        double playerSaturation = p.getSaturation();
        long currentTime = System.currentTimeMillis() / 1000;
        long lastHealTime = healTimes.computeIfAbsent(playerID, id -> System.currentTimeMillis() / 1000);
        if (currentTime - lastHealTime < Config.Setting.ADV_REGEN_FREQUENCY.getInt()) return;
        double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (playerHealth < maxHealth) {
            p.setHealth(MathUtils.clamp(playerHealth + Config.Setting.ADV_REGEN_AMOUNT.getInt(), maxHealth));
            healTimes.put(playerID, currentTime);
        }
        final float previousExhaustion = p.getExhaustion();
        final float exhaustionToApply = Config.Setting.ADV_REGEN_EXHAUSTION.getFloat();
        TaskUtils.taskLater(() -> {
            p.setExhaustion(previousExhaustion + exhaustionToApply);
            debug(p, "&6Old exhaustion: &a" + previousExhaustion + " &6New exhaustion: &a" + exhaustionToApply + " &6Saturation: &a" + playerSaturation);
        }, 1);
    }

    @EventHandler
    public void cleanCache(final PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if (!healTimes.containsKey(uuid)) return;
        healTimes.remove(uuid);
    }
}