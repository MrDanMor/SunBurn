package com.danmor.core;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class SunStrikeRegistry {
    private HashMap<Player, Integer> affectedPlayers = new HashMap<>();

    public SunStrikeRegistry() {}


    public int getSize() {
        return affectedPlayers.size();
    }


    public boolean contains(Player player) {
        return affectedPlayers.containsKey(player);
    }


    public void remove(Player player) {
        if (!contains(player)) return;

        affectedPlayers.remove(player);
    }


    public void strike(Player player) {
        if (!contains(player)) {
            affectedPlayers.put(player, 0);
        }
        
        int currentStrikeCounter = affectedPlayers.get(player);
        affectedPlayers.replace(player, currentStrikeCounter + 1);
    }


    public int getStrikeCounter(Player player) {
        return affectedPlayers.get(player);
    }


}
