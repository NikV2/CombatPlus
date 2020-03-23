package me.nik.combatplus.listeners;

import me.nik.combatplus.files.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class DamageModifiers implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent e){
        if (!(e.getDamager() instanceof Player)){
            return;
        }
        if (!Config.get().getBoolean("combat.settings.old_weapon_damage")){
            return;
        }
        Player player = (Player) e.getDamager();
        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (handItem.getType().name().endsWith("_SWORD")){
            double damageDealt = e.getDamage();
            double newDmg = damageDealt - 1;
            e.setDamage(newDmg);
            if (Config.get().getBoolean("settings.developer_mode")){
                if (player.hasPermission("cp.admin")){
                    player.sendMessage(handItem + ": " + newDmg);
                }
            }
        }else if (!Config.get().getBoolean("combat.settings.old_tool_damage")){
            return;
        }
        if (handItem.getType().name().endsWith("_PICKAXE")){
            double damageDealt = e.getDamage();
            double newDmg = damageDealt + 1;
            e.setDamage(newDmg);
            if (Config.get().getBoolean("settings.developer_mode")){
                if (player.hasPermission("cp.admin")){
                    player.sendMessage(handItem + ": " + newDmg);
                }
            }
        }else if (handItem.getType().name().endsWith("_AXE")){
            double damageDealt = e.getDamage();
            double newDmg = damageDealt - 3;
            e.setDamage(newDmg);
            if (Config.get().getBoolean("settings.developer_mode")){
                if (player.hasPermission("cp.admin")){
                    player.sendMessage(handItem + ": " + newDmg);
                }
            }
        }else if (handItem.getType().name().endsWith("_SPADE") || handItem.getType().name().endsWith("_SHOVEL")){
            double damageDealt = e.getDamage();
            double newDmg = damageDealt - 0.5;
            e.setDamage(newDmg);
            if (Config.get().getBoolean("settings.developer_mode")){
                if (player.hasPermission("cp.admin")){
                    player.sendMessage(handItem + ": " + newDmg);
                }
            }
        }
    }
}
