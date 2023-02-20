package me.nik.combatplus.api.events;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.CombatPlusAPI;
import me.nik.combatplus.modules.impl.CombatLog;

import java.util.UUID;

public class CombatPlusAPIBackend implements CombatPlusAPI {

    private final CombatPlus plugin;

    public CombatPlusAPIBackend(CombatPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public void tagPlayer(UUID uuid) {

        CombatLog combatLog = (CombatLog) this.plugin.getModule(CombatLog.class);

        if (combatLog != null) combatLog.tagPlayer(uuid);
    }

    @Override
    public void unTagPlayer(UUID uuid) {

        CombatLog combatLog = (CombatLog) this.plugin.getModule(CombatLog.class);

        if (combatLog != null) combatLog.unTagPlayer(uuid);
    }
}