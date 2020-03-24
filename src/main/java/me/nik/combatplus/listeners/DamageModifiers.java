package me.nik.combatplus.listeners;

import me.nik.combatplus.files.Config;
import me.nik.combatplus.files.Lang;
import me.nik.combatplus.utils.ColourUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    private void disableSweep(EntityDamageEvent e, Entity player) {
        e.setCancelled(true);
        e.getEntity().setVelocity(new Vector().zero());
        boolean canceled = e.isCancelled();
        double x = e.getEntity().getVelocity().getX();
        double y = e.getEntity().getVelocity().getY();
        double z = e.getEntity().getVelocity().getZ();
        if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
            if (player.hasPermission("cp.admin")) {
                player.sendMessage(ColourUtils.format(Lang.get().getString("prefix")) + ChatColor.GREEN + "Canceled Sweep: " + canceled + ChatColor.RED + " Velocity: " + "X: " + x + " Y: " + y + " Z: " + z);
            }
        }
    }

    private void oldPickaxeDmg(EntityDamageByEntityEvent e, Entity player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg = damageDealt + 1;
        e.setDamage(newDmg);
        if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
            if (player.hasPermission("cp.admin")) {
                player.sendMessage(ColourUtils.format(Lang.get().getString("prefix")) + ChatColor.AQUA + "Tool: " + handItem + ChatColor.GREEN + " Damage Dealt: " + newDmg + ChatColor.RED + " Default Damage: " + damageDealt);
            }
        }
    }

    private void oldAxeDmg(EntityDamageByEntityEvent e, Entity player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg = damageDealt - 3;
        e.setDamage(newDmg);
        if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
            if (player.hasPermission("cp.admin")) {
                player.sendMessage(ColourUtils.format(Lang.get().getString("prefix")) + ChatColor.AQUA + "Tool: " + handItem + ChatColor.GREEN + " Damage Dealt: " + newDmg + ChatColor.RED + " Default Damage: " + damageDealt);
            }
        }
    }

    private void oldShovelDmg(EntityDamageByEntityEvent e, Entity player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg = damageDealt - 0.5;
        e.setDamage(newDmg);
        if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
            if (player.hasPermission("cp.admin")) {
                player.sendMessage(ColourUtils.format(Lang.get().getString("prefix")) + ChatColor.AQUA + "Tool: " + handItem + ChatColor.GREEN + " Damage Dealt: " + newDmg + ChatColor.RED + " Default Damage: " + damageDealt);
            }
        }
    }

    private void oldSwordDmg(EntityDamageByEntityEvent e, Entity player, ItemStack handItem) {
        double damageDealt = e.getDamage();
        double newDmg = damageDealt - 1;
        e.setDamage(newDmg);
        if (Config.get().getBoolean("settings.developer_mode")) { //Developer Mode
            if (player.hasPermission("cp.admin")) {
                player.sendMessage(ColourUtils.format(Lang.get().getString("prefix")) + ChatColor.AQUA + "Weapon: " + handItem + ChatColor.GREEN + " Damage Dealt: " + newDmg + ChatColor.RED + " Default Damage: " + damageDealt);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player) e.getDamager();
        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (Config.get().getBoolean("combat.settings.old_weapon_damage")) {
            if (handItem.getType().name().endsWith("_SWORD")) {
                oldSwordDmg(e, player, handItem);
            }
        }
        if (Config.get().getBoolean("combat.settings.old_tool_damage")) {
            if (handItem.getType().name().endsWith("_PICKAXE")) {
                oldPickaxeDmg(e, player, handItem);
            } else if (handItem.getType().name().endsWith("_AXE")) {
                oldAxeDmg(e, player, handItem);
            } else if (handItem.getType().name().endsWith("_SPADE") || handItem.getType().name().endsWith("_SHOVEL")) {
                oldShovelDmg(e, player, handItem);
            }
        }
        if (Config.get().getBoolean("combat.settings.disable_sweep_attacks")) {
            if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10")) {
                return;
            } else if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
                disableSweep(e, player);
            }
        }
    }
}