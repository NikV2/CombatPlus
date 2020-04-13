package me.nik.combatplus.listeners;

import me.nik.combatplus.CombatPlus;
import me.nik.combatplus.api.Manager;
import me.nik.combatplus.utils.Messenger;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DamageModifiers extends Manager {
    public DamageModifiers(CombatPlus plugin) {
        super(plugin);
    }

    private void disableSweep(EntityDamageEvent e, Entity player, ItemStack handItem) {
        if (handItem.containsEnchantment(Enchantment.SWEEPING_EDGE) && ignoreSweepingEdge()) return;
        Entity ent = e.getEntity();
        double x = ent.getVelocity().getX();
        double y = ent.getVelocity().getY();
        double z = ent.getVelocity().getZ();
        e.setCancelled(true);
        ent.setVelocity(new Vector().zero());
        if (debug((Player) player)) {
            player.sendMessage(Messenger.prefix(ChatColor.GREEN + "Canceled Sweep: " + e.isCancelled() + ChatColor.RED + " Velocity: " + "X: " + x + " Y: " + y + " Z: " + z));
        }
    }

    private void oldPickaxeDmg(EntityDamageByEntityEvent e, Entity player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg = damageDealt + configDouble("advanced.settings.modifiers.old_pickaxes_damage");
        e.setDamage(newDmg);
        if (debug((Player) player)) {
            player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Tool: " + "Pickaxe" + ChatColor.GREEN + " Damage Dealt: " + newDmg + ChatColor.RED + " Default Damage: " + damageDealt));
        }
    }

    private void oldAxeDmg(EntityDamageByEntityEvent e, Entity player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg = damageDealt + configDouble("advanced.settings.modifiers.old_axes_damage");
        e.setDamage(newDmg);
        if (debug((Player) player)) {
            player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Tool: " + "Axe" + ChatColor.GREEN + " Damage Dealt: " + newDmg + ChatColor.RED + " Default Damage: " + damageDealt));
        }
    }

    private void oldShovelDmg(EntityDamageByEntityEvent e, Entity player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg = damageDealt + configDouble("advanced.settings.modifiers.old_shovels_damage");
        e.setDamage(newDmg);
        if (debug((Player) player)) {
            player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Tool: " + "Shovel" + ChatColor.GREEN + " Damage Dealt: " + newDmg + ChatColor.RED + " Default Damage: " + damageDealt));
        }
    }

    private void oldSwordDmg(EntityDamageByEntityEvent e, Entity player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg = damageDealt + configDouble("advanced.settings.modifiers.old_swords_damage");
        e.setDamage(newDmg);
        if (debug((Player) player)) {
            player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Weapon: " + "Sword" + ChatColor.GREEN + " Damage Dealt: " + newDmg + ChatColor.RED + " Default Damage: " + damageDealt));
        }
    }

    private void oldSharpDamage(EntityDamageByEntityEvent e, Entity player, ItemStack handItem) {
        if (handItem.containsEnchantment(Enchantment.DAMAGE_ALL)) {
            double damageDealt = e.getDamage();
            double sharpLvl = handItem.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            double oldSharpDmg = sharpLvl >= 1 ? 1 + (sharpLvl - 1) * 0.5 : 0; //1.9+
            double newSharpDmg = sharpLvl >= 1 ? sharpLvl * 1.25 : 0; //1.8
            double total = damageDealt + newSharpDmg;
            e.setDamage(damageDealt + newSharpDmg);
            if (debug((Player) player)) {
                player.sendMessage(Messenger.prefix(ChatColor.AQUA + "Weapon: " + "Sword" + ChatColor.GREEN + " Total Damage Dealt: " + total + ChatColor.GREEN + " Modified Sharp Damage: " + newSharpDmg + ChatColor.RED + " Default Sharp Damage: " + oldSharpDmg));
            }
        }
    }

    // This Listener Changes the Damage Dealt to All Entities to the Old Values

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getDamager();
        if (combatDisabledWorlds(player)) return;
        ItemStack handItem = player.getInventory().getItemInMainHand();
        String isWeapon = handItem.getType().name();
        if (configBoolean("combat.settings.old_weapon_damage")) {
            if (handItem.getType().name().endsWith("_SWORD")) {
                oldSwordDmg(e, player, handItem);
            }
        }
        if (configBoolean("combat.settings.old_tool_damage")) {
            if (isWeapon.endsWith("_PICKAXE")) {
                oldPickaxeDmg(e, player, handItem);
            } else if (isWeapon.endsWith("_AXE")) {
                oldAxeDmg(e, player, handItem);
            } else if (isWeapon.endsWith("_SPADE") || isWeapon.endsWith("_SHOVEL")) {
                oldShovelDmg(e, player, handItem);
            }
        }

        // This one disables Sweep Attacks and Reverts Sharpness Damage

        if (configBoolean("combat.settings.disable_sweep_attacks.enabled")) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
                disableSweep(e, player, handItem);
            }
        }
        if (configBoolean("combat.settings.old_sharpness")) {
            oldSharpDamage(e, player, handItem);
        }
    }
}