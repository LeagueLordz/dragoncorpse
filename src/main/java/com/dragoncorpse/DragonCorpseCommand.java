package com.dragoncorpse;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public final class DragonCorpseCommand {
    private DragonCorpseCommand() {}

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess access,
                                CommandManager.RegistrationEnvironment env) {
        dispatcher.register(CommandManager.literal("dragoncorpse")
                .requires(src -> src.hasPermissionLevel(2))
                .executes(c -> spawn(c.getSource(), 1.0f, 0.0f, 0.0f, 1.0f))
                .then(CommandManager.argument("neck", FloatArgumentType.floatArg(-5.0f, 5.0f))
                        .executes(c -> spawn(c.getSource(),
                                FloatArgumentType.getFloat(c, "neck"), 0.0f, 0.0f, 1.0f))
                        .then(CommandManager.argument("pitch", FloatArgumentType.floatArg(-90.0f, 90.0f))
                                .executes(c -> spawn(c.getSource(),
                                        FloatArgumentType.getFloat(c, "neck"),
                                        FloatArgumentType.getFloat(c, "pitch"), 0.0f, 1.0f))
                                .then(CommandManager.argument("roll", FloatArgumentType.floatArg(-180.0f, 180.0f))
                                        .executes(c -> spawn(c.getSource(),
                                                FloatArgumentType.getFloat(c, "neck"),
                                                FloatArgumentType.getFloat(c, "pitch"),
                                                FloatArgumentType.getFloat(c, "roll"), 1.0f))
                                        .then(CommandManager.argument("yoffset", FloatArgumentType.floatArg(-10.0f, 10.0f))
                                                .executes(c -> spawn(c.getSource(),
                                                        FloatArgumentType.getFloat(c, "neck"),
                                                        FloatArgumentType.getFloat(c, "pitch"),
                                                        FloatArgumentType.getFloat(c, "roll"),
                                                        FloatArgumentType.getFloat(c, "yoffset"))))))));
    }

    private static int spawn(ServerCommandSource src, float neck, float pitch, float roll, float yOffset) {
        ServerWorld world = src.getWorld();
        DragonCorpseEntity corpse = DragonCorpseMod.DRAGON_CORPSE.create(world);
        if (corpse == null) {
            return 0;
        }
        Vec3d p = src.getPosition();
        corpse.refreshPositionAndAngles(p.x, p.y, p.z, src.getRotation().y, 0.0f);
        corpse.setCorpsePose(neck, pitch, roll, yOffset);
        world.spawnEntity(corpse);
        src.sendFeedback(() -> Text.literal("Dragon corpse spawned (neck=" + neck + ", pitch=" + pitch
                + ", roll=" + roll + ", yoffset=" + yOffset + ")"), true);
        return 1;
    }
}
