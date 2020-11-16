package me.nik.combatplus.modules.impl;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.Permissions;
import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.MsgType;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.WorldUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnchantedGoldenApple extends Module {

    private final Map<UUID, Long> cooldown;

    public EnchantedGoldenApple() {
        super("Enchanted Golden Apple Cooldown", Config.Setting.ENCHANTED_APPLE_ENABLED.getBoolean());
        this.cooldown = new HashMap<>();
    }

    public String getCooldown(UUID uuid) {
        if (cooldown.containsKey(uuid)) {
            long secondsleft = ((cooldown.get(uuid) / 1000) + Config.Setting.ENCHANTED_APPLE_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
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
        if (WorldUtils.goldenAppleDisabledWorlds(e.getPlayer())) return;
        if (e.getPlayer().hasPermission(Permissions.BYPASS_GAPPLE.getPermission())) return;
        if (e.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
            final UUID p = e.getPlayer().getUniqueId();
            final Player player = e.getPlayer();
            if (cooldown.containsKey(p)) {
                long secondsleft = ((cooldown.get(p) / 1000) + Config.Setting.ENCHANTED_APPLE_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
                if (secondsleft < 1) {
                    cooldown.remove(p);
                    return;
                }
                e.setCancelled(true);
                player.sendMessage(MsgType.ENCHANTED_GOLDEN_APPLE_COOLDOWN.getMessage().replaceAll("%seconds%", String.valueOf(secondsleft)));
            } else {
                cooldown.put(p, System.currentTimeMillis());
                debug(player, "&6Added to cooldown");
                if (Config.Setting.ENCHANTED_APPLE_ACTIONBAR.getBoolean()) {
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (cooldown.containsKey(p)) {
                                long secondsleft = ((cooldown.get(p) / 1000) + Config.Setting.ENCHANTED_APPLE_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
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
                    }.runTaskTimerAsynchronously(CombatPlus.getInstance(), 0, 20);
                }
            }
        }
    }
}