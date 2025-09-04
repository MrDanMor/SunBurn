package com.danmor.mechanics;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;

public interface ISunDamager {
    default DamageSource getSunDamageSource() {
        DamageType sunType = Registry.DAMAGE_TYPE.get(
            new NamespacedKey("sunburn", "sun")
        );

        DamageSource source = DamageSource.builder(sunType).build();
        return source;
    }

    
}
