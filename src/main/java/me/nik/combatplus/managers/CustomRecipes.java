package me.nik.combatplus.managers;

import me.nik.combatplus.CombatPlus;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CustomRecipes {

    private final CombatPlus plugin;

    public CustomRecipes(CombatPlus plugin) {
        this.plugin = plugin;
    }

    public ShapedRecipe enchantedGoldenAppleRecipe() {
        ItemStack item = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);

        NamespacedKey key = new NamespacedKey(plugin, "enchanted_golden_apple");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("BBB", "BAB", "BBB");

        recipe.setIngredient('B', Material.GOLD_BLOCK);
        recipe.setIngredient('A', Material.APPLE);

        return recipe;
    }
}