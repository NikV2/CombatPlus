package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.MiscUtils;
import me.nik.combatplus.utils.WorldUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Enderpearl implements Listener {

    private final CombatPlus plugin;
    private final WorldUtils worldUtils = new WorldUtils();

    private final HashMap<UUID, Long> cooldown = new HashMap<>();
    private final int cdtime = Config.get().getInt("enderpearl_cooldown.cooldown");
    public static String PAPICOOLDOWN = "Ready";

    public Enderpearl(CombatPlus plugin) {
        this.plugin = plugin;
    }

    private void taskRun(UUID uuid) {
        cooldown.put(uuid, System.currentTimeMillis());
        new BukkitRunnable() {

            @Override
            public void run() {
                cooldown.remove(uuid);
                PAPICOOLDOWN = "Ready";
            }
        }.runTaskLaterAsynchronously(plugin, cdtime * 20);
    }

    private boolean holdsEnderPearl(Player p) {
        return p.getInventory().getItemInMainHand().getType() == Material.ENDER_PEARL || p.getInventory().getItemInOffHand().getType() == Material.ENDER_PEARL;
    }

    // This Listener Adds a cooldown between using Ender Pearls

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {
        if (worldUtils.enderpearlDisabledWorlds(e.getPlayer())) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = e.getPlayer();
            if (holdsEnderPearl(player)) {
                if (player.hasPermission("cp.bypass.epearl")) return;
                final UUID p = player.getUniqueId();
                if (cooldown.containsKey(p)) {
                    e.setCancelled(true);
                    player.updateInventory();
                    long secondsLeft = ((cooldown.get(p) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                    player.sendMessage(Messenger.message("enderpearl_cooldown").replaceAll("%seconds%", String.valueOf(secondsLeft)));
                } else {
                    taskRun(p);
                    if (MiscUtils.isPlaceholderApiEnabled()) {
                        setupPlaceholder(p);
                    }
                    Messenger.debug(player, "&3Ender Pearl Cooldown &f&l>> &6Added to cooldown: &atrue");
                    if (Config.get().getBoolean("enderpearl_cooldown.actionbar")) {
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (cooldown.containsKey(p)) {
                                    long secondsleft = ((cooldown.get(p) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                                    String message = Lang.get().getString("enderpearl_cooldown_actionbar").replaceAll("%seconds%", String.valueOf(secondsleft));
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Messenger.format(message)));
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

    private void setupPlaceholder(UUID p) {

        new BukkitRunnable() {

            @Override
            public void run() {
                if (cooldown.containsKey(p)) {
                    long secondsleft = ((cooldown.get(p) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                    PAPICOOLDOWN = String.valueOf(secondsleft);
                } else {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20);
    }
}