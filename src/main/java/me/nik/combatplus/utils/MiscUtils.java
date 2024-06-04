package me.nik.combatplus.utils;

import me.nik.combatplus.utils.custom.CombatPlusException;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public final class MiscUtils {

    //We need to do something similar to this in order for this module to work on pre 1.13
    public static final ItemStack ENCHANTED_GOLDEN_APPLE = ServerUtils.isLegacy()
            ? new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1)
            : new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);

    public static final ItemStack GOLDEN_APPLE = new ItemStack(Material.GOLDEN_APPLE);

    //In 1.20.5+ some potion effects, enchants and entities were changed so we need to do this
    public static final PotionEffectType SLOWNESS = PotionEffectType.getByName("SLOW") != null
            ? PotionEffectType.getByName("SLOW")
            : PotionEffectType.getByName("SLOWNESS");

    public static final PotionEffectType RESISTANCE = PotionEffectType.getByName("DAMAGE_RESISTANCE") != null
            ? PotionEffectType.getByName("DAMAGE_RESISTANCE")
            : PotionEffectType.getByName("RESISTANCE");

    public static final Enchantment SHARPNESS = Enchantment.getByName("DAMAGE_ALL") != null
            ? Enchantment.getByName("DAMAGE_ALL")
            : Enchantment.getByName("SHARPNESS");

    public static final EntityType FISHING_HOOK = EntityType.fromName("FISHING_HOOK") != null
            ? EntityType.fromName("FISHING_HOOK")
            : EntityType.fromName("FISHING_BOBBER");

    private MiscUtils() {
        throw new CombatPlusException("This is a static class dummy!");
    }
}