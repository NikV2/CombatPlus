package me.nik.combatplus.modules.impl;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.Permissions;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.WorldUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Enderpearl extends Module {

    private final Map<UUID, Long> cooldown;

    public Enderpearl() {
        super("Ender Pearl Cooldown", Config.Setting.ENDERPEARL_ENABLED.getBoolean());
        this.cooldown = new HashMap<>();
    }

    public String getCooldown(UUID uuid) {
        if (cooldown.containsKey(uuid)) {
            long secondsleft = ((cooldown.get(uuid) / 1000) + Config.Setting.ENDERPEARL_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
            if (secondsleft < 1) {
                cooldown.remove(uuid);
                return "Ready";
            }
            return secondsleft + "s";
        }
        return "Ready";
    }

    @EventHandler(ignoreCancelled = true)
    public void onLaunch(final ProjectileLaunchEvent e) {

        if (!(e.getEntity().getShooter() instanceof Player)) return;

        if (!(e.getEntity().getType() == EntityType.ENDER_PEARL)) return;

        Player player = (Player) e.getEntity().getShooter();

        if (WorldUtils.enderpearlDisabledWorlds(player)) return;

        if (player.hasPermission(Permissions.BYPASS_EPEARL.getPermission())) return;

        final UUID p = player.getUniqueId();

        if (cooldown.containsKey(p)) {

            e.setCancelled(true);

            long secondsLeft = ((cooldown.get(p) / 1000) + Config.Setting.ENDERPEARL_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);

            if (secondsLeft < 1) {
                cooldown.remove(p);
                return;
            }

            player.sendMessage(MsgType.ENDERPEARL_COOLDOWN.getMessage().replaceAll("%seconds%", String.valueOf(secondsLeft)));
        } else {

            cooldown.put(p, System.currentTimeMillis());

            debug(player, "&6Added to cooldown");

            if (Config.Setting.ENDERPEARL_ACTIONBAR.getBoolean()) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (cooldown.containsKey(p)) {
                            long secondsleft = ((cooldown.get(p) / 1000) + Config.Setting.ENDERPEARL_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
                            if (secondsleft < 1) {
                                cooldown.remove(p);
                                cancel();
                            }
                            String message = MsgType.ENDERPEARL_COOLDOWN_ACTIONBAR.getMessage().replaceAll("%seconds%", String.valueOf(secondsleft));
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                        } else {
                            cancel();
                        }
                    }
                }.runTaskTimerAsynchronously(CombatPlus.getInstance(), 0, 20);
            }
        }
    }
}