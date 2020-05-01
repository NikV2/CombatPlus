package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.Manager;
import me.nik.combatplus.utils.Messenger;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Enderpearl extends Manager {
    private final HashMap<UUID, Long> cooldown = new HashMap<>();
    private final int cdtime = configInt("enderpearl_cooldown.cooldown");

    public Enderpearl(CombatPlus plugin) {
        super(plugin);
    }

    private void taskRun(UUID uuid) {
        cooldown.put(uuid, System.currentTimeMillis());
        new BukkitRunnable() {

            @Override
            public void run() {
                cooldown.remove(uuid);
            }
        }.runTaskLaterAsynchronously(plugin, cdtime * 20);
    }

    private boolean holdsEnderPearl(Player p) {
        return p.getInventory().getItemInMainHand().getType() == Material.ENDER_PEARL || p.getInventory().getItemInOffHand().getType() == Material.ENDER_PEARL;
    }

    // This Listener Adds a cooldown between using Ender Pearls

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = e.getPlayer();
            if (holdsEnderPearl(player) && !enderpearlDisabledWorlds(player)) {
                if (player.hasPermission("cp.bypass.epearl")) return;
                final UUID p = player.getUniqueId();
                if (cooldown.containsKey(p)) {
                    e.setCancelled(true);
                    player.updateInventory();
                    long secondsLeft = ((cooldown.get(p) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                    player.sendMessage(Messenger.message("enderpearl_cooldown").replaceAll("%seconds%", String.valueOf(secondsLeft)));
                } else {
                    taskRun(p);
                    debug(player, "&3Ender Pearl Cooldown &f&l>> &6Added to cooldown: &atrue");
                    if (configBoolean("enderpearl_cooldown.actionbar")) {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (cooldown.containsKey(p)) {
                                    long secondsleft = ((cooldown.get(p) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Messenger.sendLang("enderpearl_cooldown_actionbar").replaceAll("%seconds%", String.valueOf(secondsleft))));
                                } else {
                                    cancel();
                                }
                            }
                        }.runTaskTimerAsynchronously(plugin, 0, 20);
                    }
                }
            }
        }
    }
}