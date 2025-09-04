package com.danmor.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SunBurnTimer extends BukkitRunnable{
    private final SunBurnSession session;
    private final SunStrikeRegistry registry;

    public SunBurnTimer(SunBurnSession session) {
        this.session = session;
        this.registry = new SunStrikeRegistry();
    }
    

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            executeStrike(player);
        }
    }


    private void executeStrike(Player player) {
        boolean playerWasAffected = session.getPlayerAffector().affectPlayer(player);
        if (!playerWasAffected) {
            registry.remove(player);
            return;
        }
        
        registry.strike(player);
    }


}
