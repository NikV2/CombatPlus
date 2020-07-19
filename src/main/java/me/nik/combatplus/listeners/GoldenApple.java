package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.MiscUtils;
import me.nik.combatplus.utils.WorldUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class GoldenApple implements Listener {

    private final CombatPlus plugin;
    private final WorldUtils worldUtils = new WorldUtils();

    private final HashMap<UUID, Long> cooldown = new HashMap<>();

    public static String papiCooldown = "Ready";

    public GoldenApple(CombatPlus plugin) {
        this.plugin = plugin;
    }

    private void taskRun(UUID uuid) {
        cooldown.put(uuid, System.currentTimeMillis());
        new BukkitRunnable() {

            @Override
            public void run() {
                cooldown.remove(uuid);
                papiCooldown = "Ready";
            }
        }.runTaskLaterAsynchronously(plugin, Config.Setting.COOLDOWN_GOLDEN_APPLE_COOLDOWN.getInt() * 20);
    }

    /*
     This Listener adds a cooldown between eating Golden Apples
     */

    @EventHandler(ignoreCancelled = true)
    public void onEatGoldenApple(PlayerItemConsumeEvent e) {
        if (worldUtils.gappleDisabledWorlds(e.getPlayer())) return;
        if (e.getPlayer().hasPermission("cp.bypass.gapple")) return;
        if (isGoldenApple(e)) {
            final UUID p = e.getPlayer().getUniqueId();
            final Player player = e.getPlayer();
            if (cooldown.containsKey(p)) {
                e.setCancelled(true);
                long secondsleft = ((cooldown.get(p) / 1000) + Config.Setting.COOLDOWN_GOLDEN_APPLE_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
                player.sendMessage(MsgType.GOLDEN_APPLE_COOLDOWN.getMessage().replaceAll("%seconds%", String.valueOf(secondsleft)));
            } else {
                taskRun(p);
                if (MiscUtils.isPlaceholderApiEnabled()) {
                    setupPlaceholder(p);
                }
                Messenger.debug(player, "&3Golden Apple Cooldown &f&l>> &6Added to cooldown: &atrue");
                if (Config.Setting.COOLDOWN_GOLDEN_APPLE_ACTIONBAR.getBoolean()) {
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (cooldown.containsKey(p)) {
                                long secondsleft = ((cooldown.get(p) / 1000) + Config.Setting.COOLDOWN_GOLDEN_APPLE_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
                                String message = MsgType.GOLDEN_APPLE_COOLDOWN_ACTIONBAR.getMessage().replaceAll("%seconds%", String.valueOf(secondsleft));
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

    private void setupPlaceholder(UUID p) {

        new BukkitRunnable() {

            @Override
            public void run() {
                if (cooldown.containsKey(p)) {
                    long secondsleft = ((cooldown.get(p) / 1000) + Config.Setting.COOLDOWN_GOLDEN_APPLE_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
                    papiCooldown = secondsleft + "s";
                } else {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20);
    }

    private boolean isGoldenApple(PlayerItemConsumeEvent e) {
        ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE);
        ItemStack consumedItem = e.getItem();
        if (plugin.serverVersion("1.8")
                || plugin.serverVersion("1.9")
                || plugin.serverVersion("1.10")
                || plugin.serverVersion("1.11")
                || plugin.serverVersion("1.12")) {
            return consumedItem.getType().name().contains("GOLDEN_APPLE") &&
                    Objects.equals(goldenApple.getData(), consumedItem.getData());
        } else {
            return consumedItem.getType() == Material.GOLDEN_APPLE;
        }
    }
}