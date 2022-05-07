package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HideToolFlags extends Module {
    private static final String[] TOOLS = new String[]{
            "WOODEN_SWORD", "WOODEN_SHOVEL", "WOODEN_PICKAXE",
            "WOODEN_AXE", "STONE_SWORD", "STONE_SHOVEL",
            "STONE_PICKAXE", "STONE_AXE", "GOLDEN_SWORD",
            "GOLDEN_SHOVEL", "GOLDEN_PICKAXE", "GOLDEN_AXE",
            "IRON_SWORD", "IRON_SHOVEL", "IRON_PICKAXE",
            "IRON_AXE", "DIAMOND_SWORD", "DIAMOND_SHOVEL",
            "DIAMOND_PICKAXE", "DIAMOND_AXE", "NETHERITE_SWORD",
            "NETHERITE_SHOVEL", "NETHERITE_PICKAXE", "NETHERITE_AXE",
            "IRON_SPADE", "WOOD_SWORD", "WOOD_SPADE", "WOOD_PICKAXE",
            "WOOD_AXE", "STONE_SPADE", "DIAMOND_SPADE", "GOLD_SWORD",
            "GOLD_SPADE", "GOLD_PICKAXE", "GOLD_AXE"
    };

    public HideToolFlags() {
        super(Config.Setting.HIDE_TOOL_FLAGS.getBoolean());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent e) {
        handle(e.getCurrentItem(), (Player) e.getWhoClicked());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDrag(InventoryDragEvent e) {
        handle(e.getCursor(), (Player) e.getWhoClicked());
    }

    private void handle(ItemStack item, Player player) {
        if (item == null || item.getItemMeta() == null) return;

        String type = item.getType().name();

        if (Arrays.stream(TOOLS).noneMatch(type::equals)) return; //Not a tool

        ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        debug(player, "&6Item: &a" + type);
    }
}