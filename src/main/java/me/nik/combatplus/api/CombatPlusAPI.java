package me.nik.combatplus.api;

import java.util.UUID;

public interface CombatPlusAPI {

    /**
     * Tag a player from the combat log
     *
     * @param uuid the player's uuid
     */
    void tagPlayer(UUID uuid);

    /**
     * Untag a player from the combat log
     *
     * @param uuid the player's uuid
     */
    void unTagPlayer(UUID uuid);
}