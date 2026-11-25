package com.dragoncorpse;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EnderDragonEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.math.RotationAxis;

/** Vanilla dragon renderer, but with an extra pose transform (lift, nose-down pitch, roll) for the corpse. */
public class DragonCorpseRenderer extends EnderDragonEntityRenderer {
    public DragonCorpseRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(EnderDragonEntity dragon, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        if (dragon instanceof DragonCorpseEntity corpse) {
            matrices.translate(0.0, corpse.getCorpseYOffset(), 0.0);
            // head points where the player was facing when spawned
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - yaw));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-corpse.getCorpsePitch()));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(corpse.getCorpseRoll()));
        }
        super.render(dragon, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
