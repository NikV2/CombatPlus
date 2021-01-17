package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.WorldUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HealthBar extends Module {

    public HealthBar() {
        super("HealthBar", Config.Setting.HEALTHBAR_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCombat(final EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity)) return;

        final LivingEntity entity = (LivingEntity) e.getEntity();

        if (e.getDamager() instanceof Player) {

            final Player p = (Player) e.getDamager();

            if (WorldUtils.healthBarDisabledWorlds(p)) return;

            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Messenger.format(getHealth(entity))));
        } else if (e.getDamager() instanceof Projectile) {

            if (!(((Projectile) e.getDamager()).getShooter() instanceof Player)) return;

            final Player shooter = (Player) ((Projectile) e.getDamager()).getShooter();

            if (WorldUtils.healthBarDisabledWorlds(shooter)) return;

            shooter.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Messenger.format(getHealth(entity))));
        }
    }

    private String getHealth(LivingEntity entity) {
        if (entity.getLastDamageCause() == null) {
            return "&8>> &a|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||| &8<<";
        }

        final double damage = entity.getLastDamage();

        final double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        final double health = entity.getHealth() - damage;

        final double onePercent = (maxHealth / 100);

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