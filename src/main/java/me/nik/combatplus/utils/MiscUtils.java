package me.nik.combatplus.utils;

import me.nik.combatplus.utils.custom.CombatPlusException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class MiscUtils {

    //We need to do something similar to this in order for this module to work on pre 1.13
    public static final ItemStack ENCHANTED_GOLDEN_APPLE = ServerUtils.isLegacy()
            ? new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1)
            : new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
    public static final ItemStack GOLDEN_APPLE = new ItemStack(Material.GOLDEN_APPLE);

    private MiscUtils() {
        throw new CombatPlusException("This is a static class dummy!");
    }
}