package com.danmor.mechanics;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.DamageTypeRegistryEntry;
import io.papermc.paper.registry.event.RegistryComposeEvent;
import io.papermc.paper.registry.keys.DamageTypeKeys;
import net.kyori.adventure.key.Key;
import org.bukkit.damage.DamageEffect;
import org.bukkit.damage.DamageScaling;
import org.bukkit.damage.DamageType;

public final class DamageTypes {
    private DamageTypes() {}

    public static final TypedKey<DamageType> SUN_KEY
        = DamageTypeKeys.create(Key.key("sunburn:sun"));

    public static void register(RegistryComposeEvent<DamageType, DamageTypeRegistryEntry.Builder> event) {
        event.registry().register(SUN_KEY, b -> b
            .messageId("sun")
            .exhaustion(0.1f)
            .damageScaling(DamageScaling.NEVER)
            .damageEffect(DamageEffect.HURT)
        );
    }

    private static DamageType cached;
    
    public static DamageType sun() {
        if (cached == null) {
            var reg = RegistryAccess.registryAccess().getRegistry(RegistryKey.DAMAGE_TYPE);
            cached = reg.getOrThrow(SUN_KEY);
        }

        return cached;
    }
}
