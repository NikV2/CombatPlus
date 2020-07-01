package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.utils.Messenger;
import me.nik.combatplus.utils.WorldUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DamageModifiers implements Listener {

    private final WorldUtils worldUtils;
    private final CombatPlus plugin;

    private final String oldPickaxeDamage;
    private final String oldAxeDamage;
    private final String oldShovelDamage;
    private final String oldSwordDamage;

    private final boolean oldWeaponDamage;
    private final boolean oldToolDamage;
    private final boolean sweepAttacks;
    private final boolean oldSharp;

    public DamageModifiers(CombatPlus plugin) {
        this.plugin = plugin;
        this.worldUtils = new WorldUtils(plugin);
        this.oldPickaxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.old_pickaxes_damage");
        this.oldAxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.old_axes_damage");
        this.oldShovelDamage = plugin.getConfig().getString("advanced.settings.modifiers.old_shovels_damage");
        this.oldSwordDamage = plugin.getConfig().getString("advanced.settings.modifiers.old_swords_damage");
        this.oldWeaponDamage = plugin.getConfig().getBoolean("combat.settings.old_weapon_damage");
        this.oldToolDamage = plugin.getConfig().getBoolean("combat.settings.old_tool_damage");
        this.sweepAttacks = plugin.getConfig().getBoolean("combat.settings.disable_sweep_attacks.enabled");
        this.oldSharp = plugin.getConfig().getBoolean("combat.settings.old_sharpness");
    }

    /*
     This Listener Changes the Damage Dealt to All Entities to the Old Values
     */

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getDamager();
        if (worldUtils.combatDisabledWorlds(player)) return;
        ItemStack handItem = player.getInventory().getItemInMainHand();
        String weapon = handItem.getType().name();
        if (sweepAttacks) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
                disableSweep(e, player, handItem);
            }
        }
        switch (weapon) {
            case "WOODEN_SWORD":
            case "STONE_SWORD":
            case "IRON_SWORD":
            case "GOLDEN_SWORD":
            case "GOLD_SWORD":
            case "DIAMOND_SWORD":
            case "NETHERITE_SWORD":
                if (oldWeaponDamage) {
                    oldSwordDmg(e, player, handItem);
                    break;
                }
            case "WOODEN_PICKAXE":
            case "STONE_PICKAXE":
            case "IRON_PICKAXE":
            case "GOLDEN_PICKAXE":
            case "GOLD_PICKAXE":
            case "DIAMOND_PICKAXE":
            case "NETHERITE_PICKAXE":
                if (oldToolDamage) {
                    oldPickaxeDmg(e, player, handItem);
                    break;
                }
            case "WOODEN_AXE":
            case "STONE_AXE":
            case "IRON_AXE":
            case "GOLDEN_AXE":
            case "GOLD_AXE":
            case "DIAMOND_AXE":
            case "NETHERITE_AXE":
                if (oldToolDamage) {
                    oldAxeDmg(e, player, handItem);
                    break;
                }
            case "WOODEN_SPADE":
            case "WOODEN_SHOVEL":
            case "STONE_SPADE":
            case "STONE_SHOVEL":
            case "IRON_SPADE":
            case "IRON_SHOVEL":
            case "GOLD_SPADE":
            case "GOLDEN_SHOVEL":
            case "DIAMOND_SPADE":
            case "DIAMOND_SHOVEL":
            case "NETHERITE_SHOVEL":
                if (oldToolDamage) {
                    oldShovelDmg(e, player, handItem);
                    break;
                }
        }
    }

    private void disableSweep(EntityDamageEvent e, Player player, ItemStack handItem) {
        if (handItem.containsEnchantment(Enchantment.SWEEPING_EDGE) && plugin.getConfig().getBoolean("combat.settings.disable_sweep_attacks.ignore_sweeping_edge"))
            return;
        Entity ent = e.getEntity();
        double x = ent.getVelocity().getX();
        double y = ent.getVelocity().getY();
        double z = ent.getVelocity().getZ();
        e.setCancelled(true);
        ent.setVelocity(new Vector().zero());
        Messenger.debug(player, "&3Damage Modifier &f&l>> &6Canceled Sweep Attack: &a" + e.isCancelled() + " &6Velocity: X = &a" + x + " &6Y = &a" + y + " &6Z = &a" + z);
    }

    private void oldPickaxeDmg(EntityDamageByEntityEvent e, Player player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg;
        if (divide(oldPickaxeDamage)) {
            newDmg = damageDealt / convertToDivide(oldPickaxeDamage);
        } else {
            newDmg = damageDealt + Double.parseDouble(oldPickaxeDamage);
        }
        if (handItem.containsEnchantment(Enchantment.DAMAGE_ALL) && oldSharp) {
            double sharpLvl = handItem.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            double oldSharpDmg = sharpLvl >= 1 ? 1 + (sharpLvl - 1) * 0.5 : 0; //1.9+
            double newSharpDmg = sharpLvl >= 1 ? sharpLvl * 1.25 : 0; //1.8
            newDmg = newDmg + newSharpDmg - oldSharpDmg;
        }
        e.setDamage(newDmg);
        Messenger.debug(player, "&3Damage Modifier &f&l>> &6Item: &aPickaxe &6Old Damage: &a" + damageDealt + " &6New Damage: &a" + newDmg);
    }

    private void oldAxeDmg(EntityDamageByEntityEvent e, Player player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg;
        if (divide(oldAxeDamage)) {
            newDmg = damageDealt / convertToDivide(oldAxeDamage);
        } else {
            newDmg = damageDealt + Double.parseDouble(oldAxeDamage);
        }
        if (handItem.containsEnchantment(Enchantment.DAMAGE_ALL) && oldSharp) {
            double sharpLvl = handItem.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            double oldSharpDmg = sharpLvl >= 1 ? 1 + (sharpLvl - 1) * 0.5 : 0; //1.9+
            double newSharpDmg = sharpLvl >= 1 ? sharpLvl * 1.25 : 0; //1.8
            newDmg = newDmg + newSharpDmg - oldSharpDmg;
        }
        e.setDamage(newDmg);
        Messenger.debug(player, "&3Damage Modifier &f&l>> &6Item: &aAxe &6Old Damage: &a" + damageDealt + " &6New Damage: &a" + newDmg);
    }

    private void oldShovelDmg(EntityDamageByEntityEvent e, Player player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg;
        if (divide(oldShovelDamage)) {
            newDmg = damageDealt / convertToDivide(oldShovelDamage);
        } else {
            newDmg = damageDealt + Double.parseDouble(oldShovelDamage);
        }
        if (handItem.containsEnchantment(Enchantment.DAMAGE_ALL) && oldSharp) {
            double sharpLvl = handItem.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            double oldSharpDmg = sharpLvl >= 1 ? 1 + (sharpLvl - 1) * 0.5 : 0; //1.9+
            double newSharpDmg = sharpLvl >= 1 ? sharpLvl * 1.25 : 0; //1.8
            newDmg = newDmg + newSharpDmg - oldSharpDmg;
        }
        e.setDamage(newDmg);
        Messenger.debug(player, "&3Damage Modifier &f&l>> &6Item: &aShovel &6Old Damage: &a" + damageDealt + " &6New Damage: &a" + newDmg);
    }

    private void oldSwordDmg(EntityDamageByEntityEvent e, Player player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg;
        if (divide(oldSwordDamage)) {
            newDmg = damageDealt / convertToDivide(oldSwordDamage);
        } else {
            newDmg = damageDealt + Double.parseDouble(oldSwordDamage);
        }
        if (handItem.containsEnchantment(Enchantment.DAMAGE_ALL) && oldSharp) {
            double sharpLvl = handItem.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            double oldSharpDmg = sharpLvl >= 1 ? 1 + (sharpLvl - 1) * 0.5 : 0; //1.9+
            double newSharpDmg = sharpLvl >= 1 ? sharpLvl * 1.25 : 0; //1.8
            newDmg = newDmg + newSharpDmg - oldSharpDmg;
        }
        e.setDamage(newDmg);
        Messenger.debug(player, "&3Damage Modifier &f&l>> &6Item: &aSword &6Old Damage: &a" + damageDealt + " &6New Damage: &a" + newDmg);
    }

    private boolean divide(String value) {
        return value.contains("/");
    }

    private double convertToDivide(String value) {
        String s = value.replaceAll("/", "");
        return Double.parseDouble(s);
    }
}
