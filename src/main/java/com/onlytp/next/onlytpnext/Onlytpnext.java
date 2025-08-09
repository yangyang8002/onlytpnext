package com.onlytp.next.onlytpnext;

import net.fabricmc.api.ModInitializer;  // ✅ 改成 ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class Onlytpnext implements ModInitializer {  // ✅ implements ModInitializer
    @Override
    public void onInitialize() {  // ✅ 方法名也改成 onInitialize
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("otp")
                        .requires(src -> src.hasPermissionLevel(0))
                        .then(CommandManager.argument("target", net.minecraft.command.argument.EntityArgumentType.player())
                                .executes(ctx -> {
                                    ServerPlayerEntity self = ctx.getSource().getPlayerOrThrow();
                                    ServerPlayerEntity target = net.minecraft.command.argument.EntityArgumentType.getPlayer(ctx, "target");
                                    self.teleport(
                                            target.getServerWorld(),
                                            target.getX(), target.getY(), target.getZ(),
                                            self.getYaw(), self.getPitch()
                                    );
                                    self.sendMessage(Text.literal("已传送到玩家 " + target.getName().getString()), false);
                                    return 1;
                                })
                        )
                        .then(CommandManager.argument("x", net.minecraft.command.argument.Vec3ArgumentType.vec3())
                                .executes(ctx -> {
                                    ServerPlayerEntity self = ctx.getSource().getPlayerOrThrow();
                                    var vec = net.minecraft.command.argument.Vec3ArgumentType.getVec3(ctx, "x");
                                    BlockPos pos = new BlockPos((int) vec.x, (int) vec.y, (int) vec.z);
                                    self.teleport(
                                            self.getServerWorld(),
                                            pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                                            self.getYaw(), self.getPitch()
                                    );
                                    self.sendMessage(Text.literal("已传送到坐标 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()), false);
                                    return 1;
                                })
                        )
                )
        );
    }
}
