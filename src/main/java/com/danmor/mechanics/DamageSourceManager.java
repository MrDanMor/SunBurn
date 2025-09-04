package com.danmor.mechanics;

import org.bukkit.NamespacedKey;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;

import com.danmor.Plugin;

public class DamageSourceManager {
    private final Plugin plugin;

    public DamageSourceManager(Plugin plugin) {
        this.plugin = plugin;
    }


    public DamageSource getSunDamageSource() {
        NamespacedKey key = new NamespacedKey(plugin, "sun");
        DamageType sunType = plugin.getServer().getRegistry(DamageType.class).get(key);
        DamageSource source = DamageSource.builder(sunType).build();

        return source;
    }


}
