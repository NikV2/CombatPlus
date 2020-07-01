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

    private final String netheritePickaxeDamage;
    private final String diamondPickaxeDamage;
    private final String goldenPickaxeDamage;
    private final String ironPickaxeDamage;
    private final String stonePickaxeDamage;
    private final String woodenPickaxeDamage;

    private final String netheriteAxeDamage;
    private final String diamondAxeDamage;
    private final String goldenAxeDamage;
    private final String ironAxeDamage;
    private final String stoneAxeDamage;
    private final String woodenAxeDamage;

    private final String netheriteShovelDamage;
    private final String diamondShovelDamage;
    private final String goldenShovelDamage;
    private final String ironShovelDamage;
    private final String stoneShovelDamage;
    private final String woodenShovelDamage;

    private final String netheriteHoeDamage;
    private final String diamondHoeDamage;
    private final String goldenHoeDamage;
    private final String ironHoeDamage;
    private final String stoneHoeDamage;
    private final String woodenHoeDamage;

    private final String netheriteSwordDamage;
    private final String diamondSwordDamage;
    private final String goldenSwordDamage;
    private final String ironSwordDamage;
    private final String stoneSwordDamage;
    private final String woodenSwordDamage;

    private final boolean oldWeaponDamage;
    private final boolean oldToolDamage;
    private final boolean sweepAttacks;
    private final boolean oldSharp;

    public DamageModifiers(CombatPlus plugin) {
        this.plugin = plugin;
        this.worldUtils = new WorldUtils(plugin);
        this.oldWeaponDamage = plugin.getConfig().getBoolean("combat.settings.old_weapon_damage");
        this.oldToolDamage = plugin.getConfig().getBoolean("combat.settings.old_tool_damage");
        this.sweepAttacks = plugin.getConfig().getBoolean("combat.settings.disable_sweep_attacks.enabled");
        this.oldSharp = plugin.getConfig().getBoolean("combat.settings.old_sharpness");
        //Pickaxes
        this.netheritePickaxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.netherite_pickaxe");
        this.diamondPickaxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.diamond_pickaxe");
        this.goldenPickaxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.golden_pickaxe");
        this.ironPickaxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.iron_pickaxe");
        this.stonePickaxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.stone_pickaxe");
        this.woodenPickaxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.wooden_pickaxe");
        //Axes
        this.netheriteAxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.netherite_axe");
        this.diamondAxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.diamond_axe");
        this.goldenAxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.golden_axe");
        this.ironAxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.iron_axe");
        this.stoneAxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.stone_axe");
        this.woodenAxeDamage = plugin.getConfig().getString("advanced.settings.modifiers.wooden_axe");
        //Shovel
        this.netheriteShovelDamage = plugin.getConfig().getString("advanced.settings.modifiers.netherite_shovel");
        this.diamondShovelDamage = plugin.getConfig().getString("advanced.settings.modifiers.diamond_shovel");
        this.goldenShovelDamage = plugin.getConfig().getString("advanced.settings.modifiers.golden_shovel");
        this.ironShovelDamage = plugin.getConfig().getString("advanced.settings.modifiers.iron_shovel");
        this.stoneShovelDamage = plugin.getConfig().getString("advanced.settings.modifiers.stone_shovel");
        this.woodenShovelDamage = plugin.getConfig().getString("advanced.settings.modifiers.wooden_shovel");
        //Hoes (The tool)
        this.netheriteHoeDamage = plugin.getConfig().getString("advanced.settings.modifiers.netherite_hoe");
        this.diamondHoeDamage = plugin.getConfig().getString("advanced.settings.modifiers.diamond_hoe");
        this.goldenHoeDamage = plugin.getConfig().getString("advanced.settings.modifiers.golden_hoe");
        this.ironHoeDamage = plugin.getConfig().getString("advanced.settings.modifiers.iron_hoe");
        this.stoneHoeDamage = plugin.getConfig().getString("advanced.settings.modifiers.stone_hoe");
        this.woodenHoeDamage = plugin.getConfig().getString("advanced.settings.modifiers.wooden_hoe");
        //Swords
        this.netheriteSwordDamage = plugin.getConfig().getString("advanced.settings.modifiers.netherite_sword");
        this.diamondSwordDamage = plugin.getConfig().getString("advanced.settings.modifiers.diamond_sword");
        this.goldenSwordDamage = plugin.getConfig().getString("advanced.settings.modifiers.golden_sword");
        this.ironSwordDamage = plugin.getConfig().getString("advanced.settings.modifiers.iron_sword");
        this.stoneSwordDamage = plugin.getConfig().getString("advanced.settings.modifiers.stone_sword");
        this.woodenSwordDamage = plugin.getConfig().getString("advanced.settings.modifiers.wooden_sword");
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
        if (sweepAttacks && e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
            disableSweep(e, player, handItem);
        }
        switch (weapon) {
            //Swords
            case "NETHERITE_SWORD":
                damageConverter(e, player, handItem, netheriteSwordDamage);
                break;
            case "DIAMOND_SWORD":
                damageConverter(e, player, handItem, diamondSwordDamage);
                break;
            case "GOLDEN_SWORD":
            case "GOLD_SWORD":
                damageConverter(e, player, handItem, goldenSwordDamage);
                break;
            case "IRON_SWORD":
                damageConverter(e, player, handItem, ironSwordDamage);
                break;
            case "STONE_SWORD":
                damageConverter(e, player, handItem, stoneSwordDamage);
                break;
            case "WOODEN_SWORD":
                damageConverter(e, player, handItem, woodenSwordDamage);
                break;
            //Pickaxes
            case "NETHERITE_PICKAXE":
                damageConverter(e, player, handItem, netheritePickaxeDamage);
                break;
            case "DIAMOND_PICKAXE":
                damageConverter(e, player, handItem, diamondPickaxeDamage);
                break;
            case "GOLDEN_PICKAXE":
            case "GOLD_PICKAXE":
                damageConverter(e, player, handItem, goldenPickaxeDamage);
                break;
            case "IRON_PICKAXE":
                damageConverter(e, player, handItem, ironPickaxeDamage);
                break;
            case "STONE_PICKAXE":
                damageConverter(e, player, handItem, stonePickaxeDamage);
                break;
            case "WOODEN_PICKAXE":
                damageConverter(e, player, handItem, woodenPickaxeDamage);
                break;
            //Axes
            case "NETHERITE_AXE":
                damageConverter(e, player, handItem, netheriteAxeDamage);
                break;
            case "DIAMOND_AXE":
                damageConverter(e, player, handItem, diamondAxeDamage);
                break;
            case "GOLDEN_AXE":
            case "GOLD_AXE":
                damageConverter(e, player, handItem, goldenAxeDamage);
                break;
            case "IRON_AXE":
                damageConverter(e, player, handItem, ironAxeDamage);
                break;
            case "STONE_AXE":
                damageConverter(e, player, handItem, stoneAxeDamage);
                break;
            case "WOODEN_AXE":
                damageConverter(e, player, handItem, woodenAxeDamage);
                break;
            //Hoes (Again, the tool!)
            case "NETHERITE_HOE":
                damageConverter(e, player, handItem, netheriteHoeDamage);
                break;
            case "DIAMOND_HOE":
                damageConverter(e, player, handItem, diamondHoeDamage);
                break;
            case "GOLDEN_HOE":
            case "GOLD_HOE":
                damageConverter(e, player, handItem, goldenHoeDamage);
                break;
            case "IRON_HOE":
                damageConverter(e, player, handItem, ironHoeDamage);
                break;
            case "STONE_HOE":
                damageConverter(e, player, handItem, stoneHoeDamage);
                break;
            case "WOODEN_HOE":
                damageConverter(e, player, handItem, woodenHoeDamage);
                break;
            //Shovels
            case "NETHERITE_SHOVEL":
                damageConverter(e, player, handItem, netheriteShovelDamage);
                break;
            case "DIAMOND_SHOVEL":
            case "DIAMOND_SPADE":
                damageConverter(e, player, handItem, diamondShovelDamage);
                break;
            case "GOLDEN_SHOVEL":
            case "GOLDEN_SPADE":
            case "GOLD_SHOVEL":
            case "GOLD_SPADE":
                damageConverter(e, player, handItem, goldenShovelDamage);
                break;
            case "IRON_SHOVEL":
            case "IRON_SPADE":
                damageConverter(e, player, handItem, ironShovelDamage);
                break;
            case "STONE_SHOVEL":
            case "STONE_SPADE":
                damageConverter(e, player, handItem, stoneShovelDamage);
                break;
            case "WOODEN_SHOVEL":
            case "WOODEN_SPADE":
                damageConverter(e, player, handItem, woodenShovelDamage);
                break;
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

    private void damageConverter(EntityDamageByEntityEvent e, Player player, ItemStack item, String modifier) {
        final String type = item.getType().name();
        if (!oldWeaponDamage && type.endsWith("_SWORD")) return;
        if (!oldToolDamage && !type.endsWith("_SWORD")) return;
        double damageDealt = e.getDamage();
        double newDmg;
        if (divide(modifier)) {
            newDmg = damageDealt / convertToDivide(modifier);
        } else {
            newDmg = damageDealt + Double.parseDouble(modifier);
        }
        if (item.containsEnchantment(Enchantment.DAMAGE_ALL) && oldSharp) {
            double sharpLvl = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            double oldSharpDmg = sharpLvl >= 1 ? 1 + (sharpLvl - 1) * 0.5 : 0; //1.9+
            double newSharpDmg = sharpLvl >= 1 ? sharpLvl * 1.25 : 0; //1.8
            newDmg = newDmg + newSharpDmg - oldSharpDmg;
        }
        e.setDamage(newDmg);
        Messenger.debug(player, "&3Damage Modifier &f&l>> &6Item: &a" + item.getType().name() + " &6Old Damage: &a" + damageDealt + " &6New Damage: &a" + newDmg);
    }

    private boolean divide(String value) {
        return value.contains("/");
    }

    private double convertToDivide(String value) {
        String s = value.replaceAll("/", "");
        return Double.parseDouble(s);
    }
}