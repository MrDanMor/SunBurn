package com.danmor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.configuration.file.FileConfiguration;

import com.danmor.core.PlayerAffector;
import com.danmor.core.SunBurnSession;
import com.danmor.parsers.ICollectionParser;
import com.danmor.utils.ConfigContent;
import com.danmor.utils.ConfigEntry;
import com.danmor.utils.CustomFile;
import com.danmor.utils.SettingsEntry;

public class FileManager implements ICollectionParser{
    private FileConfiguration config;

    private CustomFile stateFile;
    private CustomFile transparentMaterialsFile;
    private CustomFile transparentTagsFile;
    private CustomFile helmetMaterialsFile;

    private final Plugin plugin;

    public FileManager(Plugin plugin) {
        this.plugin = plugin;

        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        loadFiles();
    }


    public FileConfiguration getConfig() {
        return config;
    }


    public void loadFiles() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();
        
        transparentMaterialsFile = new CustomFile(plugin, "settings", "transparent-materials.yml");

        transparentTagsFile = new CustomFile(plugin, "settings", "transparent-tags.yml");

        helmetMaterialsFile = new CustomFile(plugin, "settings", "helmet-materials.yml");

        stateFile = new CustomFile(plugin, "state", "current-state.yml");
        
        plugin.getLogger().info("Successfully loaded all plugin files!");
    }


    public void readConfigParameters() {
        plugin.getLogger().info("Reading config parameters");
        
        for (ConfigContent parameter : ConfigContent.values()) {
            ConfigEntry entry = new ConfigEntry(plugin, parameter.getPath(), parameter.getType());
            entry.read();

            if (entry.getValue() == null) {
                plugin.getLogger().warning("Skipping config parameter [" + entry.getPath() + "]");
                continue;
            }

            parameter.runSessionMethod(plugin.getSession(), entry.getValue());
        }
    }


    public void readSettings() {
        SunBurnSession session = plugin.getSession();
        PlayerAffector playerManager = session.getPlayerAffector();

        playerManager.getSunVisibilityManager().setTransparentMaterials(readTransparentMaterials());
        playerManager.getSunVisibilityManager().setTransparentTags(readTransparentTags());
        playerManager.getHelmetAffector().setHelmetMaterials(readHelmetMaterials());
        playerManager.getHelmetAffector().setNonProtectingMaterials(readNonProtectingMaterials());
        playerManager.getHelmetAffector().setPermProtectingMaterials(readPermProtectingMaterials());
    }


    public void readState() {
        String value = stateFile.getConfig().getString("current-sun-activity");
        boolean newState;
        try {
            newState = Boolean.valueOf(value);
        } catch (Exception e) {
            plugin.getLogger().warning("Invalid sun activity state! Session is not updated...");
            return;
        }

        boolean currentState = plugin.getSession().isActive();
        if (currentState && !newState) {
            plugin.getSession().DisableBurning(null);
            return;
        }
            
        if (!currentState && newState) {
            plugin.getSession().EnableBurning(null);
        }
    }


    public void writeState() {
        Boolean sunActivity = plugin.getSession().isActive();
        stateFile.getConfig().set("current-sun-activity", sunActivity);
        stateFile.save();
    }


    private Collection<Material> readTransparentMaterials() {
        plugin.getLogger().info("Reading transparent materials from settings:");
        SettingsEntry entry = new SettingsEntry(plugin, "materials");
        entry.read(transparentMaterialsFile.getConfig());

        Collection<Material> collection = parseMaterial(entry.getValue());
        plugin.getLogger().info("... registered " + collection.size() + " materials");
        return collection;
    }


    private Collection<Tag<Material>> readTransparentTags() {
        plugin.getLogger().info("Reading transparent tags from settings:");
        SettingsEntry entry = new SettingsEntry(plugin, "tags");
        entry.read(transparentTagsFile.getConfig());

        Collection<Tag<Material>> collection = parseTag(entry.getValue());
        plugin.getLogger().info("... registered " + collection.size() + " tags");
        return collection;
    }


    private Collection<Material> readHelmetMaterials() {
        plugin.getLogger().info("Reading helmet materials from settings:");
        SettingsEntry entry = new SettingsEntry(plugin, "helmet-materials");
        entry.read(helmetMaterialsFile.getConfig());

        Collection<Material> collection = parseMaterial(entry.getValue());
        plugin.getLogger().info("... registered " + collection.size() + " materials");
        return collection;
    }


    private Collection<Material> readNonProtectingMaterials() {
        plugin.getLogger().info("Reading non-protecting materials from settings:");
        SettingsEntry entry = new SettingsEntry(plugin, "non-protecting-materials");
        Collection<String> variants = new ArrayList<>();
        variants.add("copy-from-transparent-materials");
        entry.allowProperty(variants);
        entry.read(helmetMaterialsFile.getConfig());

        switch (entry.getProperty()) {
            case "copy-from-transparent-materials":
                plugin.getLogger().info("Is copied from transparent-materials");
                return readTransparentMaterials();
        }

        Collection<Material> collection = parseMaterial(entry.getValue());
        plugin.getLogger().info("... registered " + collection.size() + " materials");
        return collection;
    }


    private Collection<Material> readPermProtectingMaterials() {
        plugin.getLogger().info("Reading perm-protecting materials from settings:");
        SettingsEntry entry = new SettingsEntry(plugin, "perm-protecting-materials");
        entry.read(helmetMaterialsFile.getConfig());

        Collection<Material> collection = parseMaterial(entry.getValue());
        plugin.getLogger().info("... registered " + collection.size() + " materials");
        return collection;
    }


}