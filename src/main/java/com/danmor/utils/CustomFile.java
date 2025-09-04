package com.danmor.utils;

import java.io.File;

import javax.annotation.Nonnull;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.danmor.Plugin;

public class CustomFile {
    private final Plugin plugin;
    private final String path;
    private final String name;

    private File file;
    private FileConfiguration config;

    public CustomFile(Plugin plugin, @Nonnull String path, @Nonnull String name) {
        this.plugin = plugin;
        this.path = path;
        this.name = name;

        load(false);
    }


    public boolean load(boolean replace) {
        File folder = new File(plugin.getDataFolder(), path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        file = new File(folder, name);
        if (!file.exists()) {
            plugin.saveResource(path + '/' + name, replace);
        }

        config = YamlConfiguration.loadConfiguration(file);

        return true;
    }


    public boolean save() {
        try {
            config.save(file);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to save config into state file: " + e.getMessage());
            return false;
        }

        return true;
    }


    public FileConfiguration getConfig() {
        return config;
    }


    public File getFile() {
        return file;
    }


}
