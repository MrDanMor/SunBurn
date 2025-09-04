package com.danmor.utils;

import org.bukkit.configuration.file.FileConfiguration;

import com.danmor.Plugin;
import com.danmor.parsers.IObjectParser;

public class ConfigEntry implements IObjectParser {
    private final Plugin plugin;
    private final String path;
    private final Class<?> type;

    private Object value;

    public ConfigEntry(Plugin plugin, String path, Class<?> type) {
        this.plugin = plugin;
        this.path = path;
        this.type = type;
    }


    @Override
    public void printTypeMismatch() {
        plugin.getLogger().warning("Invalid config value: " + path);
    }


    public Object getValue() {
        return value;
    }


    public String getPath() {
        return path;
    }


    public boolean read() {
        FileConfiguration config = plugin.getConfig();
        if (config == null) {
            return false;
        }

        value = tryParse(config.getString(path), type);

        return true;
    }


}
