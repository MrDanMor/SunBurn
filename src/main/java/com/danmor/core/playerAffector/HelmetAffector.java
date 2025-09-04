package com.danmor.core.playerAffector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import com.danmor.utils.IDecimalCustomizer;

public class HelmetAffector implements IDecimalCustomizer {
    private final int MAX_HELMET_DAMAGE = 50;
    private final int MAX_CHANCE = 100;
    private final int chanceAccuracy = 4;

    private int helmetDamage = 0;
    private double nonHelmetDropChance = 0;

    private Collection<Material> helmetMaterials = new ArrayList<>();
    private Collection<Material> nonProtectingMaterials = new ArrayList<>();
    private Collection<Material> permProtectingMaterials = new ArrayList<>();


    public void setHelmetDamage(int damage) {
        this.helmetDamage = Math.clamp(damage, 0, MAX_HELMET_DAMAGE);
    }


    public void setNonHelmetDropChance(double chance) {
        if (chance < 0) nonHelmetDropChance = -1;

        double formattedChance = roundToDigits(chance, chanceAccuracy);

        if (formattedChance >= 100) {
            nonHelmetDropChance = 100;
            return;
        }
        
        double minValue = Math.pow(10, - chanceAccuracy);
        nonHelmetDropChance = Math.clamp(formattedChance, minValue, 100);

        this.nonHelmetDropChance = Math.clamp(chance, 0, MAX_CHANCE);
    }


    public void setHelmetMaterials(Collection<Material> helmetMaterials) {
        this.helmetMaterials = helmetMaterials;
    }


    public void setNonProtectingMaterials(Collection<Material> nonProtectingMaterials) {
        this.nonProtectingMaterials = nonProtectingMaterials;
    }


    public void setPermProtectingMaterials(Collection<Material> permProtectingMaterials) {
        this.permProtectingMaterials = permProtectingMaterials;
    }


    public void printStatus(Player player) {
        player.sendMessage("Helmet is damaged by " + helmetDamage + " point(s) per each sun strike");

        if (nonHelmetDropChance < 0) {
            player.sendMessage("Item in helmet slot does not protect from sun");
        } else if (nonHelmetDropChance == 0) {
            player.sendMessage("Item in helmet slot protects from sun permanently");
        } else {
            String value = getFormattedFloat((float) nonHelmetDropChance, chanceAccuracy);
            player.sendMessage("Item in helmet slot is dropped with " + value + "% chance");
        }
    }


    public boolean affectHelmet(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        Material type = helmet.getType();
        if (helmet == null || helmet.getType().isAir()) {
            return false;
        }

        if (nonProtectingMaterials.contains(type)) {
            return false;
        }

        if (permProtectingMaterials.contains(type)) {
            return true;
        }

        if (!helmetMaterials.contains(type)) {
            return !dropHelmetRandomly(player);
        }

        return damageHelmet(player, helmetDamage);
    }


    private boolean damageHelmet(Player player, int damage) {
        if (!player.isValid()) return false;

        ItemStack helmet = player.getInventory().getHelmet();
        ItemMeta meta = helmet.getItemMeta();
        Damageable damageable = (Damageable) meta;

        if (damageable.isUnbreakable()) return true;

        boolean hasMaxDamage = damageable.hasMaxDamage();
        int maxDurability = hasMaxDamage ? damageable.getMaxDamage() : helmet.getType().getMaxDurability();
        damageable.setDamage(damageable.getDamage() + damage);
        helmet.setItemMeta(meta);

        if (damageable.getDamage() >= maxDurability) {
            player.getInventory().setHelmet(null);
        }

        return true;
    }


    private boolean dropHelmetRandomly(Player player) {
        if (!player.isValid()) return false;

        if (nonHelmetDropChance < 0) {
            return false;
        }

        Random random = new Random();
        Double randValue = random.nextDouble(MAX_CHANCE);
        if (randValue >= nonHelmetDropChance) {
            return false;
        }

        ItemStack item = player.getInventory().getHelmet().clone();
        player.getWorld().dropItemNaturally(player.getEyeLocation(), item);
        player.getInventory().setHelmet(null);
        return true;
    }
}
