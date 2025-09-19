package com.danmor.core;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.danmor.Plugin;
import com.danmor.utils.IDecimalCustomizer;

public class SunBurnSession implements IDecimalCustomizer {
    private final Plugin plugin;

    private SunBurnTimer timer;
    private boolean isActive = false;
    private long tickPeriod = 60L;

    private PlayerAffector playerAffector = new PlayerAffector();

    public SunBurnSession(Plugin plugin) {
        this.plugin = plugin;
    }


    public Plugin getPlugin() {
        return plugin;
    }


    public PlayerAffector getPlayerAffector() {
        return playerAffector;
    }


    public boolean isActive() {
        return isActive;
    }


    public void EnableBurning(CommandSender executor) {
        if (isActive) {
            if (executor != null) {
                executor.sendMessage("§cWas enabled already...");
            }

            return;
        }
        
        timer = new SunBurnTimer(this);
        timer.runTaskTimer(plugin, 0, tickPeriod);
        isActive = true;

        if (executor != null) {
            executor.sendMessage("§cSun is now combusting every player in the Overworld");
        }
    }


    public void DisableBurning(CommandSender executor) {
        if (!isActive) {
            if (executor != null) {
                executor.sendMessage("§cWas disabled already...");
            }

            return;
        }

        timer.cancel();
        timer = null;
        isActive = false;

        if (executor != null) {
            executor.sendMessage("§cSun stops being dangerous in Overworld");
        }
    }


    public void printStatus(CommandSender executor) {
        if (!(executor instanceof Player)) return;
        Player player = (Player) executor;

        player.sendMessage("");
        player.sendMessage("§6-- SUNBURN PLUGIN STATUS --");

        if (isActive) {
            player.sendMessage("Current sun activity: §2" + isActive);
        } else {
            player.sendMessage("Current sun activity: §4" + isActive);
        }
        
        player.sendMessage("Sun strikes each §e" + tickPeriod + "§f ticks");

        playerAffector.printStatus(player);

        player.sendMessage("§6--- Thanks for using my plugin!");
        player.sendMessage("§6--- Author: DanMor (Mr__Palladium)");
        player.sendMessage("");
    }


    public void setTickPeriod(long period) {
        tickPeriod = Math.clamp(period, 20, 100);

        reloadTimer();
    }


    private void reloadTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new SunBurnTimer(this);
        timer.runTaskTimer(plugin, 0, tickPeriod);

        plugin.getLogger().info("Timer has been reloaded");
    }


}
