package me.nik.combatplus.utils;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messenger {
    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String prefix(String msg) {
        return format(Lang.get().getString("prefix")) + format(msg);
    }

    public static String message(String msg) {
        return format(Lang.get().getString("prefix") + format(Lang.get().getString(msg)));
    }

    public static void debug(Player player, String message) {
        if (Config.get().getBoolean("settings.developer_mode") && player.hasPermission("cp.debug")) {
            player.sendMessage(Messenger.prefix(message));
        }
    }
}
