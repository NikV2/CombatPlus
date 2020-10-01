package me.nik.combatplus.listeners.combatlog;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.managers.CombatLog;
import me.nik.combatplus.managers.MsgType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener extends CombatLog {

    private final List<String> excludedCommands;

    public CommandListener() {
        this.excludedCommands = Config.Setting.COMBATLOG_COMMANDS_EXCLUDED.getStringList();
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();

        if (!isTagged(p)) return;

        final String command = e.getMessage().replace("/", "");

        for (String cmd : excludedCommands) {
            if (cmd.contains(command)) return;
        }

        e.setCancelled(true);
        p.sendMessage(MsgType.COMBATLOG_COMMAND.getMessage());
    }
}