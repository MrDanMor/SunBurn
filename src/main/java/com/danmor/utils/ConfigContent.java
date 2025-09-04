package com.danmor.utils;

import com.danmor.core.SunBurnSession;

@FunctionalInterface
interface InnerConfigContent {
    void execute(SunBurnSession instance, Object parameter);
}

public enum ConfigContent {
    TICK_PERIOD("cooldown-tick-period",
        Long.class,
        (c, p) -> c.setTickPeriod((Long) p)
    ),

    BURNING_DURATION("burning-duration",
        Integer.class,
        (c, p) -> c.getPlayerAffector().setBurningDuration((Integer) p)
    ),

    MODE("sun-burn-mode",
        SunBurnMode.class,
        (c, p) -> c.getPlayerAffector().getSunVisibilityManager().setMode((SunBurnMode) p)
    ),

    MIN_LIGHT_LEVEL("min-light-level",
        Integer.class,
        (c, p) -> c.getPlayerAffector().getSunVisibilityManager().setMinLightLevel((Integer) p)
    ),

    PLAYER_DAMAGE("player-damage",
        Integer.class,
        (c, p) -> c.getPlayerAffector().setPlayerDamage((Integer) p)
    ),

    HELMET_DAMAGE("helmet-damage",
        Integer.class,
        (c, p) -> c.getPlayerAffector().getHelmetAffector().setHelmetDamage((Integer) p)
    ),

    NON_HELMET_DROP_CHANCE("non-helmet-drop-chance",
        Float.class,
        (c, p) -> c.getPlayerAffector().getHelmetAffector().setNonHelmetDropChance((Float) p)
    ),

    WATER_DEFENCE("water-defence",
        Boolean.class,
        (c, p) -> c.getPlayerAffector().setWaterDefence((Boolean) p)
    );

    private final InnerConfigContent consumer;
    private final String sectionName;
    private final Class<?> type;

    ConfigContent(String path, Class<?> type, InnerConfigContent consumer) {
        sectionName = path;
        this.type = type;
        this.consumer = consumer;
    }

    public String getPath() {
        return sectionName;
    }

    public Class<?> getType() {
        return type;
    }

    public void runSessionMethod(SunBurnSession instance, Object parameter) {
        consumer.execute(instance, parameter);
    }

}
