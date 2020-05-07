package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.WorldUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class EnchantedGoldenApple implements Listener {

    private final CombatPlus plugin;
    private final WorldUtils worldUtils = new WorldUtils();

    private final HashMap<UUID, Long> cooldown = new HashMap<>();
    private final int cdtime = Config.get().getInt("golden_apple_cooldown.enchanted_golden_apple.cooldown");

    public EnchantedGoldenApple(CombatPlus plugin) {
        this.plugin = plugin;
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

    // This Listener adds a cooldown between eating Enchanted Golden Apples

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onEatEnchantedGoldenApple(PlayerItemConsumeEvent e) {
        if (worldUtils.gappleDisabledWorlds(e.getPlayer())) return;
        if (e.getPlayer().hasPermission("cp.bypass.gapple")) return;
        final Material handItem = e.getItem().getType();
        if (handItem == Material.ENCHANTED_GOLDEN_APPLE) {
            final UUID p = e.getPlayer().getUniqueId();
            final Player player = e.getPlayer();
            if (cooldown.containsKey(p)) {
                e.setCancelled(true);
                long secondsleft = ((cooldown.get(p) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                player.sendMessage(Messenger.message("enchanted_golden_apple_cooldown").replaceAll("%seconds%", String.valueOf(secondsleft)));
            } else {
                taskRun(p);
                Messenger.debug(player, "&3Enchanted Golden Apple Cooldown &f&l>> &6Added to cooldown: &atrue");
                if (Config.get().getBoolean("golden_apple_cooldown.enchanted_golden_apple.actionbar")) {
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (cooldown.containsKey(p)) {
                                long secondsleft = ((cooldown.get(p) / 1000) + cdtime) - (System.currentTimeMillis() / 1000);
                                String message = Lang.get().getString("enchanted_golden_apple_cooldown_actionbar").replaceAll("%seconds%", String.valueOf(secondsleft));
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