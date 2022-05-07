package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.ChatUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HealthBar extends Module {

    public HealthBar() {
        super(Config.Setting.HEALTHBAR_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCombat(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity)
                || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK
                || (!(e.getEntity() instanceof Player) && Config.Setting.HEALTHBAR_PLAYERS_ONLY.getBoolean())) return;

        Entity damager = e.getDamager();

        Player player = null;

        if (damager instanceof Player) {

            player = (Player) damager;

        } else if (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof Player) {

            player = (Player) ((Projectile) damager).getShooter();
        }

        if (player == null || disabledWorld(player)) return;

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatUtils.format(getHealth((LivingEntity) e.getEntity()))));
    }

    private boolean disabledWorld(Player player) {
        return Config.Setting.HEALTHBAR_DISABLED_WORLDS.getStringList().stream().anyMatch(world -> world.equals(player.getWorld().getName()));
    }

    private String getHealth(LivingEntity entity) {
        if (entity.getLastDamageCause() == null) {
            return "&8>> &a|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||| &8<<";
        }

        final double health = entity.getHealth() - entity.getLastDamage();

        final double onePercent = (entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() / 100);

        final int currentPercent = (int) Math.round((health / onePercent));

        if (currentPercent <= 0) {
            return "&8>> &7|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||| &8<<";
        }

        final int missingPercent = (100 - currentPercent);

        StringBuilder visualization = new StringBuilder("&8>> &a");

        for (int i = 0; i < currentPercent; i++) {
            visualization.append("|");
        }

        visualization.append("&7");

        for (int i = 0; i < missingPercent; i++) {
            visualization.append("|");
        }

        visualization.append(" &8<<");

        return visualization.toString();
    }
}