package me.nik.combatplus.managers;

import me.nik.combatplus.files.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CombatLog extends BukkitRunnable implements Listener {

    private static final Map<UUID, Long> taggedPlayers = new ConcurrentHashMap<>();

    private final int timer = Config.Setting.COMBATLOG_COOLDOWN.getInt();
    private final boolean disableFly = Config.Setting.COMBATLOG_DISABLE_FLY.getBoolean();
    private final boolean actionbar = Config.Setting.COMBATLOG_ACTIONBAR.getBoolean();

    public static String getCooldown(UUID uuid) {
        if (taggedPlayers.containsKey(uuid)) {
            long secondsleft = ((taggedPlayers.get(uuid) / 1000) + Config.Setting.COMBATLOG_COOLDOWN.getInt()) - (System.currentTimeMillis() / 1000);
            if (secondsleft < 1) {
                taggedPlayers.remove(uuid);
                return "Ready";
            }
            return secondsleft + "s";
        }
        return "Ready";
    }

    protected boolean isTagged(Player player) {
        return taggedPlayers.containsKey(player.getUniqueId());
    }

    protected void tagPlayer(Player player) {

        if (player.hasPermission(Permissions.BYPASS_COMBATLOG)) return;

        if (disableFly) {
            player.setFlying(false);
            player.setAllowFlight(false);
        }

        if (!taggedPlayers.containsKey(player.getUniqueId())) {
            player.sendMessage(MsgType.COMBATLOG_TAGGED.getMessage());
        }

        taggedPlayers.put(player.getUniqueId(), System.currentTimeMillis());
    }

    protected void unTagPlayer(Player player) {
        taggedPlayers.remove(player.getUniqueId());
    }

    @Override
    public void run() {
        if (taggedPlayers.isEmpty()) return;
        taggedPlayers.forEach((uuid, aLong) -> {
            Player p = Bukkit.getPlayer(uuid);
            if (p == null) return;

            long secondsLeft = ((taggedPlayers.get(uuid) / 1000) + timer) - (System.currentTimeMillis() / 1000);
            if (secondsLeft > 0) {
                if (actionbar) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(MsgType.COMBATLOG_ACTIONBAR.getMessage().replace("%seconds%", String.valueOf(secondsLeft))));
                }
            } else {
                taggedPlayers.remove(uuid, aLong);
                p.sendMessage(MsgType.COMBATLOG_UNTAGGED.getMessage());
            }
        });
    }
}