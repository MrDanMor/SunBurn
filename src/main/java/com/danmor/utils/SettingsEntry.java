package com.danmor.utils;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.configuration.file.FileConfiguration;

import com.danmor.Plugin;
import com.danmor.parsers.IObjectParser;

public class SettingsEntry implements IObjectParser {
    private final Plugin plugin;
    private final String path;

    private boolean allowProperty = false;
    private boolean hasProperty = false;
    private String property;
    private Collection<String> variants;

    private Collection<String> collection = new ArrayList<>();

    public SettingsEntry(Plugin plugin, String path) {
        this.plugin = plugin;
        this.path = path;
    }


    public Collection<String> getValue() {
        return collection;
    }


    public String getPath() {
        return path;
    }
    

    public void allowProperty(Collection<String> variants) {
        this.allowProperty = true;
        this.variants = variants;
    }


    public boolean hasProperty() {
        return hasProperty;
    }


    public String getProperty() {
        return property;
    }


    public boolean read(FileConfiguration file) {
        if (file == null) {
            return false;
        }

        if (allowProperty) {
            hasProperty = tryProperty(file);
        }

        if (hasProperty) {
            return true;
        }

        for (String s : file.getStringList(path)) {
            try {
                collection.add(s);
            } catch (Exception e) {
                plugin.getLogger().warning("Invalid [" + path + "] value!");
            }
        }

        return true;
    }


    private boolean tryProperty(FileConfiguration config) {
        try {
            String option = config.getString(path);
            
            for (String variant : variants) {
                if (option.equals(variant)) {
                    plugin.getLogger().info("This entry is read as an option");
                    this.property = option;
                    return true;
                }
            }

            throw new Exception("Invalid section value, trying to read as a list");

        } catch (Exception e) {
            plugin.getLogger().info("This entry is read as a list");
            return false;
        }
    }


}
