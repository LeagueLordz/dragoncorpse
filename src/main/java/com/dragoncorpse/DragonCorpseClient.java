package com.dragoncorpse;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class DragonCorpseClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(DragonCorpseMod.DRAGON_CORPSE, DragonCorpseRenderer::new);
    }
}
