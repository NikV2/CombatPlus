package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.MathUtils;
import me.nik.combatplus.utils.TaskUtils;
import me.nik.combatplus.utils.custom.ExpiringMap;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.UUID;

public class CustomPlayerRegeneration extends Module {

    private final ExpiringMap<UUID, Long> healTimes = new ExpiringMap<>(10000L);

    public CustomPlayerRegeneration() {
        super("Custom Player Regeneration", Config.Setting.CUSTOM_PLAYER_REGENERATION_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegen(EntityRegainHealthEvent e) {
        if (e.getEntityType() != EntityType.PLAYER || e.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED)
            return;

        final Player p = (Player) e.getEntity();

        e.setCancelled(true);

        final UUID uuid = p.getUniqueId();

        final double playerHealth = p.getHealth();

        final long currentTime = System.currentTimeMillis() / 1000L;

        final long lastHealTime = healTimes.computeIfAbsent(uuid, id -> System.currentTimeMillis() / 1000L);

        if (currentTime - lastHealTime < Config.Setting.CUSTOM_PLAYER_REGENERATION_FREQUENCY.getInt()) return;

        final double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        if (playerHealth < maxHealth) {

            p.setHealth(MathUtils.clampDouble(playerHealth + Config.Setting.CUSTOM_PLAYER_REGENERATION_AMOUNT.getInt(), maxHealth));

            healTimes.put(uuid, currentTime);
        }

        final float previousExhaustion = p.getExhaustion();

        final float exhaustionToApply = Config.Setting.CUSTOM_PLAYER_REGENERATION_EXCHAUSTION.getFloat();

        TaskUtils.taskLater(() -> {
            p.setExhaustion(previousExhaustion + exhaustionToApply);
            debug(p, "&6Old exhaustion: &a" + previousExhaustion + " &6New exhaustion: &a" + exhaustionToApply);
        }, 1);
    }
}