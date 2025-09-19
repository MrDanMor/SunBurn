package com.danmor;
import org.bukkit.plugin.java.JavaPlugin;

import com.danmor.commands.SunBurn;
import com.danmor.core.SunBurnSession;

import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;


public class Plugin extends JavaPlugin {
    private final SunBurnSession session = new SunBurnSession(this);
    private final FileManager fileManager = new FileManager(this);


    @Override
    public void onEnable() {
        getLogger().info("SunBurn plugin has been enabled");

        registerCommands();

        fileManager.loadFiles();
        fileManager.readConfigParameters();
        fileManager.readSettings();
        fileManager.readState();
    }


    @Override
    public void onDisable() {
        getLogger().info("SunBurn plugin has been disabled");
        session.DisableBurning(null);
    }


    public SunBurnSession getSession() {
        return session;
    }


    public FileManager getFileManager() {
        return fileManager;
    }


    private void registerCommands() {
        LifecycleEventManager<org.bukkit.plugin.Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register(SunBurn.createCommand(this),
                "SunBurn core command"
            );

            event.registrar().register(SunBurn.createAlias(this),
                "SunBurn core command alias"
            );
        });
    }


}
