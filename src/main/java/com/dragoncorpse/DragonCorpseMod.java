package com.dragoncorpse;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DragonCorpseMod implements ModInitializer {
    public static final String MOD_ID = "dragoncorpse";

    public static final EntityType<DragonCorpseEntity> DRAGON_CORPSE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "dragon_corpse"),
            EntityType.Builder.<DragonCorpseEntity>create(DragonCorpseEntity::new, SpawnGroup.MISC)
                    .setDimensions(16.0f, 8.0f)
                    .makeFireImmune()
                    .maxTrackingRange(10)
                    .build("dragon_corpse"));

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(DRAGON_CORPSE, EnderDragonEntity.createEnderDragonAttributes());
        CommandRegistrationCallback.EVENT.register(DragonCorpseCommand::register);
    }
}
