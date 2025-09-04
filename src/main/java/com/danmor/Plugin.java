package com.danmor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.danmor.core.SunBurnSession;


public class Plugin extends JavaPlugin {
    private final SunBurnSession session = new SunBurnSession(this);
    private final FileManager fileManager = new FileManager(this);

    private final String COMMAND_NAME = "sunburn";
    private final String USAGE_MESSAGE = "Usage: /sunburn <on|off|reload|status>";


    @Override
    public void onEnable() {
        getLogger().info("SunBurn plugin has been enabled");

        fileManager.loadFiles();
        fileManager.readConfigParameters();
        fileManager.readSettings();
        fileManager.readState();
        this.getCommand(COMMAND_NAME).setExecutor(this);
    }


    @Override
    public void onDisable() {
        getLogger().info("SunBurn plugin has been disabled");
        session.DisableBurning(null);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase(COMMAND_NAME)) {
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(USAGE_MESSAGE);
            return true;
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "on":
                session.EnableBurning(sender);
                fileManager.writeState();
                break;
            case "off":
                session.DisableBurning(sender);
                fileManager.writeState();
                break;
            case "reload":
                fileManager.loadFiles();
                fileManager.readConfigParameters();
                fileManager.readSettings();
                fileManager.readState();

                sender.sendMessage("Config was reloaded!");
                break;
            case "status":
                session.printStatus(sender);
                break;
            default:
                sender.sendMessage("Unknown subcommand: " + subCommand);
                sender.sendMessage(USAGE_MESSAGE);
                break;
        }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("on", "off", "reload", "status");
        }

        return Collections.emptyList();
    }


    public SunBurnSession getSession() {
        return session;
    }


}
