package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.utils.Messenger;
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

public class EnchantedGoldenApple implements Listener {

    private final CombatPlus plugin;
    private final WorldUtils worldUtils = new WorldUtils();

    private static final HashMap<UUID, Long> cooldown = new HashMap<>();

    public EnchantedGoldenApple(CombatPlus plugin) {
        this.plugin = plugin;
    }

    /*
     This Listener adds a cooldown between eating Enchanted Golden Apples
     */

    public static String getCooldown(UUID uuid) {
        if (cooldown.containsKey(uuid)) {
            long secondsleft = ((cooldown.get(uuid) / 1000) + Config.Setting.COOLDOWN_ENCHANTED_APPLE_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
            if (secondsleft < 1) {
                cooldown.remove(uuid);
                return "Ready";
            }
            return secondsleft + "s";
        }
        return "Ready";
    }

    @EventHandler(ignoreCancelled = true)
    public void onEatEnchantedGoldenApple(PlayerItemConsumeEvent e) {
        if (worldUtils.gappleDisabledWorlds(e.getPlayer())) return;
        if (e.getPlayer().hasPermission("cp.bypass.gapple")) return;
        if (isEnchantedGoldenApple(e)) {
            final UUID p = e.getPlayer().getUniqueId();
            final Player player = e.getPlayer();
            if (cooldown.containsKey(p)) {
                e.setCancelled(true);
                long secondsleft = ((cooldown.get(p) / 1000) + Config.Setting.COOLDOWN_ENCHANTED_APPLE_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
                if (secondsleft < 1) {
                    cooldown.remove(p);
                    return;
                }
                player.sendMessage(MsgType.ENCHANTED_GOLDEN_APPLE_COOLDOWN.getMessage().replaceAll("%seconds%", String.valueOf(secondsleft)));
            } else {
                cooldown.put(p, System.currentTimeMillis());
                Messenger.debug(player, "&3Enchanted Golden Apple Cooldown &f&l>> &6Added to cooldown: &atrue");
                if (Config.Setting.COOLDOWN_ENCHANTED_APPLE_ACTIONBAR.getBoolean()) {
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (cooldown.containsKey(p)) {
                                long secondsleft = ((cooldown.get(p) / 1000) + Config.Setting.COOLDOWN_ENCHANTED_APPLE_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
                                if (secondsleft < 1) {
                                    cooldown.remove(p);
                                    cancel();
                                }
                                String message = MsgType.ENCHANTED_GOLDEN_APPLE_COOLDOWN_ACTIONBAR.getMessage().replaceAll("%seconds%", String.valueOf(secondsleft));
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

    private boolean isEnchantedGoldenApple(PlayerItemConsumeEvent e) {
        try {
            return e.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE;
        } catch (NoSuchFieldError ignored) {
            ItemStack goldenApple = new ItemStack(Material.GOLDEN_APPLE);
            ItemStack consumedItem = e.getItem();
            return consumedItem.getType().name().equals("GOLDEN_APPLE") &&
                    !Objects.equals(goldenApple.getData(), consumedItem.getData());
        }
    }
}