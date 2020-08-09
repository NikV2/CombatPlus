package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.utils.Messenger;
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

    private static final HashMap<UUID, Long> cooldown = new HashMap<>();

    public Enderpearl(CombatPlus plugin) {
        this.plugin = plugin;
    }

    /*
     This Listener Adds a cooldown between using Ender Pearls
     */

    public static String getCooldown(UUID uuid) {
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
            long secondsLeft = ((cooldown.get(p) / 1000) + Config.Setting.ENDERPEARL_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
            if (secondsLeft < 1) {
                cooldown.remove(p);
                return;
            }
            player.sendMessage(MsgType.ENDERPEARL_COOLDOWN.getMessage().replaceAll("%seconds%", String.valueOf(secondsLeft)));
        } else {
            cooldown.put(p, System.currentTimeMillis());
            Messenger.debug(player, "&3Ender Pearl Cooldown &f&l>> &6Added to cooldown: &atrue");
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
                }.runTaskTimerAsynchronously(plugin, 0, 20);
            }
        }
    }
}