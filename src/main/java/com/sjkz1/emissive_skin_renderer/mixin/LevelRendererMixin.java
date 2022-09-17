package com.sjkz1.emissive_skin_renderer.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.sjkz1.emissive_skin_renderer.utils.EmissiveUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin
{
    @Shadow private @Nullable ClientLevel level;

    @Shadow @Final private RenderBuffers renderBuffers;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;checkPoseStack(Lcom/mojang/blaze3d/vertex/PoseStack;)V",ordinal = 2,shift = At.Shift.AFTER),cancellable = true)
    public void test(PoseStack poseStack, float f, long l, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo ci) {
        var vec3 = camera.getPosition();
        var blockPos = camera.getBlockPosition();
        var blockState = this.level.getBlockState(blockPos);
        var bufferSource = this.renderBuffers.bufferSource();
        var d = vec3.x();
        var e = vec3.y();
        var g = vec3.z();
        if (this.level.getWorldBorder().isWithinBounds(blockPos)) {
            EmissiveUtils.ore_location.forEach(resourceLocation -> {
                VertexConsumer vertexConsumer3 = bufferSource.getBuffer(RenderType.eyes(resourceLocation));
                this.renderGlow(poseStack, vertexConsumer3, camera.getEntity(), d, e, g, blockPos, blockState);
            });
        }
    }

    private void renderGlow(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState) {
        LevelRendererMixin.renderShape(poseStack, vertexConsumer, blockState.getShape(this.level, blockPos, CollisionContext.of(entity)), (double) blockPos.getX() - d, (double) blockPos.getY() - e, (double) blockPos.getZ() - f, 0.0f, 0.0f, 0.0f, 0.4f);
    }

    private static void renderShape(PoseStack poseStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float g, float h, float i, float j) {
        PoseStack.Pose pose = poseStack.last();
        voxelShape.forAllEdges((k, l, m, n, o, p) -> {
            float q = (float) (n - k);
            float r = (float) (o - l);
            float s = (float) (p - m);
            float t = Mth.sqrt(q * q + r * r + s * s);
            vertexConsumer.vertex(pose.pose(), (float) (k + d), (float) (l + e), (float) (m + f)).color(g, h, i, j).normal(pose.normal(), q /= t, r /= t, s /= t).endVertex();
            vertexConsumer.vertex(pose.pose(), (float) (n + d), (float) (o + e), (float) (p + f)).color(g, h, i, j).normal(pose.normal(), q, r, s).endVertex();
        });
    }
}
