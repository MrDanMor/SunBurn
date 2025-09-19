package com.danmor.bootstrap;

import com.danmor.mechanics.DamageTypes;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.registry.event.RegistryEvents;

public class DamageTypeManager implements PluginBootstrap {
    @Override
    public void bootstrap(BootstrapContext context) {
        context.getLifecycleManager().registerEventHandler(
            RegistryEvents.DAMAGE_TYPE.compose().newHandler(DamageTypes::register)
        );
    }
}
