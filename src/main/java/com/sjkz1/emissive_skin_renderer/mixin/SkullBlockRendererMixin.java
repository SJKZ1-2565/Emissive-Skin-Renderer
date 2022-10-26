package com.sjkz1.emissive_skin_renderer.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sjkz1.emissive_skin_renderer.render.GlowingLayerSkull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Objects;

@Mixin(SkullBlockRenderer.class)
public abstract class SkullBlockRendererMixin implements BlockEntityRenderer<SkullBlockEntity> {

    @Shadow
    @Final
    private Map<SkullBlock.Type, SkullModelBase> modelByType;

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/SkullBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("TAIL"))
    public void render(SkullBlockEntity skullBlockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, CallbackInfo ci) {
        if (skullBlockEntity.getOwnerProfile() != null) {
            float g = skullBlockEntity.getMouthAnimation(f);
            BlockState blockState = skullBlockEntity.getBlockState();
            boolean bl = blockState.getBlock() instanceof WallSkullBlock;
            Direction direction = bl ? blockState.getValue(WallSkullBlock.FACING) : null;
            float h = 22.5f * (float) (bl ? (2 + direction.get2DDataValue()) * 4 : blockState.getValue(SkullBlock.ROTATION));
            SkullBlock.Type type = ((AbstractSkullBlock) blockState.getBlock()).getType();
            SkullModelBase skullModelBase = this.modelByType.get(type);
            RenderType renderType = GlowingLayerSkull.getRenderType(type, skullBlockEntity.getOwnerProfile());
            GlowingLayerSkull.renderSkull(direction, h, g, poseStack, multiBufferSource, i, skullModelBase, skullBlockEntity.getOwnerProfile(), renderType, (int) Objects.requireNonNull(skullBlockEntity.getLevel()).getGameTime());
        }
    }
}




