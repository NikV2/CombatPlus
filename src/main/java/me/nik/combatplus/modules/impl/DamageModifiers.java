package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import me.nik.combatplus.utils.WorldUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DamageModifiers extends Module {

    //Yes im lazy to make each feature seperate.
    public DamageModifiers() {
        super("Damage Modifier", Config.Setting.OLD_WEAPON_DAMAGE.getBoolean()
                || Config.Setting.OLD_TOOL_DAMAGE.getBoolean()
                || Config.Setting.OLD_SHARPNESS.getBoolean()
                || Config.Setting.DISABLE_SWEEP_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(final EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        final Player player = (Player) e.getDamager();
        if (WorldUtils.combatDisabledWorlds(player)) return;
        final ItemStack handItem = player.getInventory().getItemInMainHand();
        final String weapon = handItem.getType().name();
        if (Config.Setting.DISABLE_SWEEP_ENABLED.getBoolean() && e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
            disableSweep(e, player, handItem);
        }
        switch (weapon) {
            //Swords
            case "NETHERITE_SWORD":
                damageConverter(e, player, handItem, Config.Setting.ADV_NETHERITE_SWORD.getDouble());
                break;
            case "DIAMOND_SWORD":
                damageConverter(e, player, handItem, Config.Setting.ADV_DIAMOND_SWORD.getDouble());
                break;
            case "GOLDEN_SWORD":
            case "GOLD_SWORD":
                damageConverter(e, player, handItem, Config.Setting.ADV_GOLDEN_SWORD.getDouble());
                break;
            case "IRON_SWORD":
                damageConverter(e, player, handItem, Config.Setting.ADV_IRON_SWORD.getDouble());
                break;
            case "STONE_SWORD":
                damageConverter(e, player, handItem, Config.Setting.ADV_STONE_SWORD.getDouble());
                break;
            case "WOODEN_SWORD":
                damageConverter(e, player, handItem, Config.Setting.ADV_WOODEN_SWORD.getDouble());
                break;
            //Pickaxes
            case "NETHERITE_PICKAXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_NETHERITE_PICKAXE.getDouble());
                break;
            case "DIAMOND_PICKAXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_DIAMOND_PICKAXE.getDouble());
                break;
            case "GOLDEN_PICKAXE":
            case "GOLD_PICKAXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_GOLDEN_PICKAXE.getDouble());
                break;
            case "IRON_PICKAXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_IRON_PICKAXE.getDouble());
                break;
            case "STONE_PICKAXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_STONE_PICKAXE.getDouble());
                break;
            case "WOODEN_PICKAXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_WOODEN_PICKAXE.getDouble());
                break;
            //Axes
            case "NETHERITE_AXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_NETHERITE_AXE.getDouble());
                break;
            case "DIAMOND_AXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_DIAMOND_AXE.getDouble());
                break;
            case "GOLDEN_AXE":
            case "GOLD_AXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_GOLDEN_AXE.getDouble());
                break;
            case "IRON_AXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_IRON_AXE.getDouble());
                break;
            case "STONE_AXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_STONE_AXE.getDouble());
                break;
            case "WOODEN_AXE":
                damageConverter(e, player, handItem, Config.Setting.ADV_WOODEN_AXE.getDouble());
                break;
            //Hoes (Again, the tool!)
            case "NETHERITE_HOE":
                damageConverter(e, player, handItem, Config.Setting.ADV_NETHERITE_HOE.getDouble());
                break;
            case "DIAMOND_HOE":
                damageConverter(e, player, handItem, Config.Setting.ADV_DIAMOND_HOE.getDouble());
                break;
            case "GOLDEN_HOE":
            case "GOLD_HOE":
                damageConverter(e, player, handItem, Config.Setting.ADV_GOLDEN_HOE.getDouble());
                break;
            case "IRON_HOE":
                damageConverter(e, player, handItem, Config.Setting.ADV_IRON_HOE.getDouble());
                break;
            case "STONE_HOE":
                damageConverter(e, player, handItem, Config.Setting.ADV_STONE_HOE.getDouble());
                break;
            case "WOODEN_HOE":
                damageConverter(e, player, handItem, Config.Setting.ADV_WOODEN_HOE.getDouble());
                break;
            //Shovels
            case "NETHERITE_SHOVEL":
                damageConverter(e, player, handItem, Config.Setting.ADV_NETHERITE_SHOVEL.getDouble());
                break;
            case "DIAMOND_SHOVEL":
            case "DIAMOND_SPADE":
                damageConverter(e, player, handItem, Config.Setting.ADV_DIAMOND_SHOVEL.getDouble());
                break;
            case "GOLDEN_SHOVEL":
            case "GOLDEN_SPADE":
            case "GOLD_SHOVEL":
            case "GOLD_SPADE":
                damageConverter(e, player, handItem, Config.Setting.ADV_GOLDEN_SHOVEL.getDouble());
                break;
            case "IRON_SHOVEL":
            case "IRON_SPADE":
                damageConverter(e, player, handItem, Config.Setting.ADV_IRON_SHOVEL.getDouble());
                break;
            case "STONE_SHOVEL":
            case "STONE_SPADE":
                damageConverter(e, player, handItem, Config.Setting.ADV_STONE_SHOVEL.getDouble());
                break;
            case "WOODEN_SHOVEL":
            case "WOODEN_SPADE":
                damageConverter(e, player, handItem, Config.Setting.ADV_WOODEN_SHOVEL.getDouble());
                break;
        }
    }

    private void disableSweep(EntityDamageEvent e, Player player, ItemStack handItem) {
        if (handItem.containsEnchantment(Enchantment.SWEEPING_EDGE) && Config.Setting.DISABLE_SWEEP_IGNORE_SWEEPING_EDGE.getBoolean())
            return;
        Entity ent = e.getEntity();
        double x = ent.getVelocity().getX();
        double y = ent.getVelocity().getY();
        double z = ent.getVelocity().getZ();
        e.setCancelled(true);
        ent.setVelocity(new Vector().zero());
        debug(player, "&6Canceled Sweep Attack: &a" + e.isCancelled() + " &6Velocity: X = &a" + x + " &6Y = &a" + y + " &6Z = &a" + z);
    }

    private void damageConverter(EntityDamageByEntityEvent e, Player player, ItemStack item, double modifier) {
        final String type = item.getType().name();
        if (!Config.Setting.OLD_WEAPON_DAMAGE.getBoolean() && type.endsWith("_SWORD")) return;
        if (!Config.Setting.OLD_TOOL_DAMAGE.getBoolean() && !type.endsWith("_SWORD")) return;
        double damageDealt = e.getDamage();
        double newDmg = damageDealt + modifier;
        if (item.containsEnchantment(Enchantment.DAMAGE_ALL) && Config.Setting.OLD_SHARPNESS.getBoolean()) {
            double sharpLvl = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            double oldSharpDmg = sharpLvl >= 1 ? 1 + (sharpLvl - 1) * 0.5 : 0; //1.9+
            double newSharpDmg = sharpLvl >= 1 ? sharpLvl * 1.25 : 0; //1.8
            newDmg = newDmg + newSharpDmg - oldSharpDmg;
        }
        e.setDamage(newDmg);
        debug(player, "&6Item: &a" + item.getType().name() + " &6Old Damage: &a" + damageDealt + " &6New Damage: &a" + newDmg);
    }
}