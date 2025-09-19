package com.danmor.core.playerAffector;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.danmor.utils.SunBurnMode;

public class SunVisibilityManager {
    private final int MAX_LIGHT_LEVEL = 16;

    private SunBurnMode mode = SunBurnMode.SKYLIGHT;
    private int minLightLevel = 15;

    private Collection<Material> transparentMaterials = new ArrayList<>();
    private Collection<Tag<Material>> transparentTags = new ArrayList<>();


    public void setMode(SunBurnMode mode) {
        this.mode = mode;
    }


    public void setMinLightLevel(int level) {
        minLightLevel = Math.clamp(level, 0, MAX_LIGHT_LEVEL);
    }


    public void setTransparentMaterials(Collection<Material> materials) {
        transparentMaterials = materials;
    }


    public void setTransparentTags(Collection<Tag<Material>> tags) {
        transparentTags = tags;
    }


    public void printStatus(Player player) {
        player.sendMessage("Current plugin mode is: §e" + mode + "\n");
        player.sendMessage("Minimal dangerous light level is: §3" + minLightLevel + "\n");
    }


    public boolean canSeeSun(Player player) {
        World world = player.getWorld();
        if (!isDaytime(world)) {
            return false;
        }

        switch (mode) {
            case SunBurnMode.MATERIALCONFIG:
                return !isOpaqueBlockAbove(player);
            case SunBurnMode.SKYLIGHT:
                return isOpenToSkyLight(player);
            case SunBurnMode.ANYLIGHT:
                return isOpenToAnyLight(player);
        }

        return false;
    }


    private boolean isOpenToSkyLight(Player player) {
        Block block = player.getEyeLocation().getBlock();
        int lightAmount = block.getLightFromSky();

        if (lightAmount < minLightLevel) {
            return false;
        }

        return true;
    }


    private boolean isOpenToAnyLight(Player player) {
        Block block = player.getEyeLocation().getBlock();
        int lightAmount = Math.max(block.getLightFromSky(), block.getLightFromBlocks());

        if (lightAmount < minLightLevel) {
            return false;
        }

        return true;
    }


    private boolean isOpaqueBlockAbove(Player player) {
        World world = player.getWorld();
        int playerY = player.getEyeLocation().getBlockY();
        if (playerY >= world.getMaxHeight()) {
            return true;
        }

        int x = (int) player.getLocation().getX();
        int z = (int) player.getLocation().getZ();
        int highestY = world.getHighestBlockYAt(x, z);

        if (highestY < playerY + 1) {
            return true;
        }

        int y = highestY;
        while (y > playerY) {
            Block block = world.getBlockAt(x, y, z);
            if (!isBlockTransparent(block)) {
                return false;
            }

            y -= 1;
        }

        return true;
    }


    private boolean isBlockTransparent(Block block) {
        Material material = block.getType();
        boolean isMaterial = transparentMaterials.contains(material);
        if (isMaterial) return true;

        for (Tag<Material> tag : transparentTags) {
            if (tag.isTagged(block.getType())) {
                return true;
            }
        }

        return false;
    }


    private boolean isDaytime(World world) {
        long time = world.getTime();

        return time > 0 && time < 12000;
    }


}
