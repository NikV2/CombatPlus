package me.nik.combatplus.utils;

import me.nik.combatplus.utils.custom.CombatPlusException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Stream;

public final class MiscUtils {

    //We need to do something similar to this in order for this module to work on pre 1.13
    public static final ItemStack ENCHANTED_GOLDEN_APPLE = Stream.of(new String[]{"1.8", "1.10", "1.11", "1.12"})
            .anyMatch(string -> Bukkit.getVersion().contains(string))
            ? new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1)
            : new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
    public static final ItemStack GOLDEN_APPLE = new ItemStack(Material.GOLDEN_APPLE);

    private MiscUtils() {
        throw new CombatPlusException("This is a static class dummy!");
    }
}