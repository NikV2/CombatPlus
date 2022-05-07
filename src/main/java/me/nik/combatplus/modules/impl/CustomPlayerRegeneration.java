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
        super(Config.Setting.CUSTOM_PLAYER_REGENERATION_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegen(EntityRegainHealthEvent e) {
        if (e.getEntityType() != EntityType.PLAYER || e.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED)
            return;

        Player player = (Player) e.getEntity();

        e.setCancelled(true);

        UUID uuid = player.getUniqueId();

        double playerHealth = player.getHealth();

        float previousExhaustion = player.getExhaustion();

        //Milliseconds to seconds
        long currentTime = System.currentTimeMillis() / 1000L;

        long lastHealTime = this.healTimes.computeIfAbsent(uuid, id -> currentTime);

        if ((currentTime - lastHealTime) < Config.Setting.CUSTOM_PLAYER_REGENERATION_FREQUENCY.getLong()) {

            TaskUtils.taskLaterAsync(() -> {

                player.setExhaustion(previousExhaustion);

                debug(player, "&6Skipped");

            }, 1L);

            return;
        }

        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        if (playerHealth < maxHealth) {

            player.setHealth(MathUtils.clampDouble(playerHealth + Config.Setting.CUSTOM_PLAYER_REGENERATION_AMOUNT.getDouble(), maxHealth));

            this.healTimes.put(uuid, currentTime);
        }

        float exhaustionToApply = Config.Setting.CUSTOM_PLAYER_REGENERATION_EXCHAUSTION.getFloat();

        TaskUtils.taskLaterAsync(() -> {

            player.setExhaustion(previousExhaustion + exhaustionToApply);

            debug(player, "&6Old exhaustion: &a" + previousExhaustion + " &6New exhaustion: &a" + exhaustionToApply);

        }, 1L);
    }
}