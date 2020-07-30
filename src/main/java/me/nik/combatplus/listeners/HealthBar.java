package me.nik.combatplus.listeners;

import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.WorldUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HealthBar implements Listener {

    private final WorldUtils worldUtils = new WorldUtils();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCombat(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;

        if (worldUtils.healthBarDisabledWorlds((Player) e.getDamager())) return;

        if (!(e.getEntity() instanceof LivingEntity)) return;

        Player p = (Player) e.getDamager();
        LivingEntity entity = (LivingEntity) e.getEntity();

        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Messenger.format(getHealth(entity))));
    }

    private String getHealth(LivingEntity entity) {
        if (entity.getLastDamageCause() == null)
            return "&8>> &a|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||| &8<<";
        double damage = entity.getLastDamage();
        double maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double health = entity.getHealth() - damage;
        double onePercent = (maxHealth / 100);
        int currentPercent = (int) Math.round((health / onePercent));
        if (currentPercent <= 0) {
            return "&8>> &7|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||| &8<<";
        }
        int missingPercent = (100 - currentPercent);
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