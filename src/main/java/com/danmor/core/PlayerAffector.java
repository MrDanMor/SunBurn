package com.danmor.core;

import org.bukkit.GameMode;
import org.bukkit.World.Environment;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Player;

import com.danmor.core.playerAffector.HelmetAffector;
import com.danmor.core.playerAffector.SunVisibilityManager;
import com.danmor.mechanics.DamageTypes;

public class PlayerAffector {
    private final int MAX_BURNING_DURATION = 500;
    private final int MAX_PLAYER_DAMAGE = 100;

    private int burningDuration = 100;
    private int playerDamage = 0;
    private boolean waterDefence = false;

    private SunVisibilityManager sunVisibilityManager = new SunVisibilityManager();
    private HelmetAffector helmetAffector = new HelmetAffector();


    public SunVisibilityManager getSunVisibilityManager() {
        return sunVisibilityManager;
    }


    public HelmetAffector getHelmetAffector() {
        return helmetAffector;
    }


    public void setBurningDuration(int duration) {
        burningDuration = Math.clamp(duration, 0, MAX_BURNING_DURATION);
    }


    public void setPlayerDamage(int damage) {
        playerDamage = Math.clamp(damage, 0, MAX_PLAYER_DAMAGE);
    }


    public void setWaterDefence(boolean isDefending) {
        waterDefence = isDefending;
    }


    public void printStatus(Player player) {
        sunVisibilityManager.printStatus(player);

        player.sendMessage("Players are damaged by §e" + playerDamage + "§f point(s) each sun strike\n");
        player.sendMessage("Players are burning §e" + burningDuration + "§f ticks after each strike\n");

        if (waterDefence) {
            player.sendMessage("Water protects from sun permanently\n");
        } else {
            player.sendMessage("Water does not protect from sun permanently\n");
        }

        helmetAffector.printStatus(player);
    }


    public boolean affectPlayer(Player player) {
        if (!player.isValid()) return false;
        
        if (!canBeBurned(player)) return false;

        boolean canSeeSun = sunVisibilityManager.canSeeSun(player);
        if (!canSeeSun) return false;

        boolean wasInHelmet = helmetAffector.affectHelmet(player);
        if (wasInHelmet) return false;

        applyBurning(player);
        damagePlayer(player);

        return true;
    }


    private boolean canBeBurned(Player player) {
        if (player.getWorld().getEnvironment() != Environment.NORMAL) {
            return false;
        }

        GameMode gamemode = player.getGameMode();
        if (!(gamemode.equals(GameMode.SURVIVAL) || gamemode.equals(GameMode.ADVENTURE))) {
            return false;
        }

        if (player.isInWater() && waterDefence) {
            return false;
        }

        return true;
    }


    private void applyBurning(Player player) {
        int currentFireDuration = player.getFireTicks();
        if (burningDuration > currentFireDuration) {
            player.setFireTicks(burningDuration);
        }
    }


    private void damagePlayer(Player player) {
        DamageSource source = DamageSource.builder(DamageTypes.sun()).build();
        player.damage(playerDamage, source);
    }

    
}
