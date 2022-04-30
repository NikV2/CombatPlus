package me.nik.combatplus.modules.impl;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.modules.Module;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DamageModifiers extends Module {

    private static final Vector ZERO_VELOCITY = new Vector();

    public DamageModifiers() {
        super("Damage Modifiers", Config.Setting.DAMAGE_MODIFIERS_DISABLE_SWEEP.getBoolean()
                || Config.Setting.DAMAGE_MODIFIERS_OLD_SHARPNESS.getBoolean()
                || Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_ENABLED.getBoolean());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;

        Player player = (Player) e.getDamager();

        ItemStack item = player.getInventory().getItemInMainHand();

        if (Config.Setting.DAMAGE_MODIFIERS_DISABLE_SWEEP.getBoolean() && e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {

            e.setCancelled(true);

            e.getEntity().setVelocity(ZERO_VELOCITY);

            debug(player, "&6Cancelled Sweep Attack");

        } else {

            if (Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_ENABLED.getBoolean()) {

                final double modifiedDamage;

                String type = item.getType().name();

                switch (type) {

                    //Swords
                    case "NETHERITE_SWORD":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_NETHERITE_SWORD.getDouble();
                        break;

                    case "DIAMOND_SWORD":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_DIAMOND_SWORD.getDouble();
                        break;

                    case "GOLDEN_SWORD":
                    case "GOLD_SWORD":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_GOLDEN_SWORD.getDouble();
                        break;

                    case "IRON_SWORD":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_IRON_SWORD.getDouble();
                        break;

                    case "STONE_SWORD":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_STONE_SWORD.getDouble();
                        break;

                    case "WOODEN_SWORD":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_WOODEN_SWORD.getDouble();
                        break;

                    //Pickaxes
                    case "NETHERITE_PICKAXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_NETHERITE_PICKAXE.getDouble();
                        break;

                    case "DIAMOND_PICKAXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_DIAMOND_PICKAXE.getDouble();
                        break;

                    case "GOLDEN_PICKAXE":
                    case "GOLD_PICKAXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_GOLDEN_PICKAXE.getDouble();
                        break;

                    case "IRON_PICKAXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_IRON_PICKAXE.getDouble();
                        break;

                    case "STONE_PICKAXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_STONE_PICKAXE.getDouble();
                        break;

                    case "WOODEN_PICKAXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_WOODEN_PICKAXE.getDouble();
                        break;

                    //Axes
                    case "NETHERITE_AXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_NETHERITE_AXE.getDouble();
                        break;

                    case "DIAMOND_AXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_DIAMOND_AXE.getDouble();
                        break;

                    case "GOLDEN_AXE":
                    case "GOLD_AXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_GOLDEN_AXE.getDouble();
                        break;

                    case "IRON_AXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_IRON_AXE.getDouble();
                        break;

                    case "STONE_AXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_STONE_AXE.getDouble();
                        break;

                    case "WOODEN_AXE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_WOODEN_AXE.getDouble();
                        break;

                    //Hoes (Again, the tool!)
                    case "NETHERITE_HOE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_NETHERITE_HOE.getDouble();
                        break;

                    case "DIAMOND_HOE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_DIAMOND_HOE.getDouble();
                        break;

                    case "GOLDEN_HOE":
                    case "GOLD_HOE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_GOLDEN_HOE.getDouble();
                        break;

                    case "IRON_HOE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_IRON_HOE.getDouble();
                        break;

                    case "STONE_HOE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_STONE_HOE.getDouble();
                        break;

                    case "WOODEN_HOE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_WOODEN_HOE.getDouble();
                        break;

                    //Shovels
                    case "NETHERITE_SHOVEL":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_NETHERITE_SHOVEL.getDouble();
                        break;

                    case "DIAMOND_SHOVEL":
                    case "DIAMOND_SPADE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_DIAMOND_SHOVEL.getDouble();
                        break;

                    case "GOLDEN_SHOVEL":
                    case "GOLDEN_SPADE":
                    case "GOLD_SHOVEL":
                    case "GOLD_SPADE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_GOLDEN_SHOVEL.getDouble();
                        break;

                    case "IRON_SHOVEL":
                    case "IRON_SPADE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_IRON_SHOVEL.getDouble();
                        break;

                    case "STONE_SHOVEL":
                    case "STONE_SPADE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_STONE_SHOVEL.getDouble();
                        break;

                    case "WOODEN_SHOVEL":
                    case "WOODEN_SPADE":
                        modifiedDamage = Config.Setting.DAMAGE_MODIFIERS_CUSTOM_TOOL_DAMAGES_TOOLS_WOODEN_SHOVEL.getDouble();
                        break;

                    default:
                        modifiedDamage = Double.MIN_VALUE;
                        break;
                }

                //Not any items listed
                if (modifiedDamage == Double.MIN_VALUE) return;

                double damageDealt = e.getDamage();

                double newDmg = damageDealt + modifiedDamage;

                if (Config.Setting.DAMAGE_MODIFIERS_OLD_SHARPNESS.getBoolean() && item.containsEnchantment(Enchantment.DAMAGE_ALL)) {

                    double sharpLvl = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);

                    double oldSharpDmg = sharpLvl >= 1 ? 1 + (sharpLvl - 1) * 0.5 : 0; //1.9+

                    double newSharpDmg = sharpLvl >= 1 ? sharpLvl * 1.25 : 0; //1.8

                    newDmg = newDmg + newSharpDmg - oldSharpDmg;
                }

                e.setDamage(newDmg);

                debug(player, "&6Item: &a" + type + " &6Old Damage: &a" + damageDealt + " &6New Damage: &a" + newDmg);
            }
        }
    }
}