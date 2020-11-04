package me.nik.combatplus.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CombatPlusLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public CombatPlusLoadEvent() {
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}