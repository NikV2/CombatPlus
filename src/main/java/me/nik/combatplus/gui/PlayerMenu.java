package me.nik.combatplus.gui;

import org.bukkit.entity.Player;

public class PlayerMenu {

    private Player owner;

    public PlayerMenu(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
