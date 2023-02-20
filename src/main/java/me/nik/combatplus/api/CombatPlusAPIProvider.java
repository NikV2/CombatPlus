package me.nik.combatplus.api;

public class CombatPlusAPIProvider {

    private static CombatPlusAPI API = null;

    public CombatPlusAPIProvider() {
    }

    public static CombatPlusAPI getAPI() {
        return API;
    }

    @Deprecated
    public static void register(CombatPlusAPI api) {
        API = api;
    }
}