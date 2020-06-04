package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.MiscUtils;
import me.nik.combatplus.utils.WorldUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class Enderpearl implements Listener {

    private final CombatPlus plugin;
    private final WorldUtils worldUtils = new WorldUtils();

    private final HashMap<UUID, Long> cooldown = new HashMap<>();
    private final int cdtime = Config.get().getInt("enderpearl_cooldown.cooldown");
    private final boolean actionbar = Config.get().getBoolean("enderpearl_cooldown.actionbar");
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

    /*
     This Listener Adds a cooldown between using Ender Pearls
     */

    @EventHandler(ignoreCancelled = true)
    public void onLaunch(ProjectileLaunchEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player)) return;
        if (!(e.getEntity().getType() == EntityType.ENDER_PEARL)) return;
        Player player = (Player) e.getEntity().getShooter();
        if (worldUtils.enderpearlDisabledWorlds(player)) return;
        if (player.hasPermission("cp.bypass.epearl")) return;
        final UUID p = player.getUniqueId();
        if (cooldown.containsKey(p)) {
            e.setCancelled(true);
            if (!(player.getGameMode() == GameMode.CREATIVE) && plugin.serverVersion("1.8") || plugin.serverVersion("1.9") || plugin.serverVersion("1.10")) {
                ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL, 1);
                player.getInventory().addItem(enderpearl);
            }
            long secondsLeft = ((cooldown.get(p) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
            player.sendMessage(Messenger.message("enderpearl_cooldown").replaceAll("%seconds%", String.valueOf(secondsLeft)));
        } else {
            taskRun(p);
            if (MiscUtils.isPlaceholderApiEnabled()) {
                setupPlaceholder(p);
            }
            Messenger.debug(player, "&3Ender Pearl Cooldown &f&l>> &6Added to cooldown: &atrue");
            if (actionbar) {
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