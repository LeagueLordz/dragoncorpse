package com.dragoncorpse;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * A frozen Ender Dragon: no AI, no movement, invulnerable.
 * Pose is tunable (and synced to the client) so you can adjust it without recompiling:
 *   neck    - how much the neck/head bends down (positive = down)
 *   pitch   - whole body nose-down angle in degrees (positive = nose down)
 *   roll    - whole body roll in degrees (180 = belly up)
 *   yoffset - lift/sink the model relative to the entity position (blocks)
 */
public class DragonCorpseEntity extends EnderDragonEntity {
    private static final TrackedData<Float> NECK = DataTracker.registerData(DragonCorpseEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> PITCH = DataTracker.registerData(DragonCorpseEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> ROLL = DataTracker.registerData(DragonCorpseEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> YOFFSET = DataTracker.registerData(DragonCorpseEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public DragonCorpseEntity(EntityType<? extends EnderDragonEntity> type, World world) {
        super(type, world);
        this.setInvulnerable(true);
        this.setPersistent();
        this.setNoGravity(true);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(NECK, 1.0f);
        this.dataTracker.startTracking(PITCH, 0.0f);
        this.dataTracker.startTracking(ROLL, 0.0f);
        this.dataTracker.startTracking(YOFFSET, 1.0f);
    }

    public void setCorpsePose(float neck, float pitch, float roll, float yOffset) {
        this.dataTracker.set(NECK, neck);
        this.dataTracker.set(PITCH, pitch);
        this.dataTracker.set(ROLL, roll);
        this.dataTracker.set(YOFFSET, yOffset);
    }

    public float getCorpseNeck() { return this.dataTracker.get(NECK); }
    public float getCorpsePitch() { return this.dataTracker.get(PITCH); }
    public float getCorpseRoll() { return this.dataTracker.get(ROLL); }
    public float getCorpseYOffset() { return this.dataTracker.get(YOFFSET); }

    /** Vanilla dragon flies around here. The corpse just lies still. */
    @Override
    public void tickMovement() {
        this.setVelocity(Vec3d.ZERO);
    }

    /**
     * The dragon model builds its neck/head from these "segments" (index 0 = head side, higher = body side).
     * We return a fixed, bent set so the neck curves down.
     */
    @Override
    public double[] getSegmentProperties(int segmentNumber, float tickDelta) {
        double y = segmentNumber >= 5 ? 0.0 : getCorpseNeck() * (segmentNumber - 5);
        return new double[]{0.0, y, 0.0};
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("CorpseNeck", getCorpseNeck());
        nbt.putFloat("CorpsePitch", getCorpsePitch());
        nbt.putFloat("CorpseRoll", getCorpseRoll());
        nbt.putFloat("CorpseYOffset", getCorpseYOffset());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("CorpseNeck")) {
            setCorpsePose(nbt.getFloat("CorpseNeck"), nbt.getFloat("CorpsePitch"),
                    nbt.getFloat("CorpseRoll"), nbt.getFloat("CorpseYOffset"));
        }
    }
}
