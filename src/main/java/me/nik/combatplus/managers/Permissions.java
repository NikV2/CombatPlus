package me.nik.combatplus.managers;

public enum Permissions {
    ADMIN("cp.admin"),
    BYPASS_COMBATLOG("cp.bypass.combatlog"),
    BYPASS_GAPPLE("cp.bypass.gapple"),
    BYPASS_EPEARL("cp.bypass.epearl"),
    BYPASS_ITEMS("cp.bypass.items"),
    BYPASS_OFFHAND("cp.bypass.offhand"),
    BYPASS_BOWBOOST("cp.bypass.bowboost"),
    DEBUG("cp.debug");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}