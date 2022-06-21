package com.sjkz1.emissive_skin_renderer.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sjkz1.emissive_skin_renderer.render.GlowingLayerSkull;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public class BlockEntityWithoutLevelRenderMixin {

    @Shadow
    private Map<SkullBlock.Type, SkullModelBase> skullModels;

    @Inject(method = "renderByItem", at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/blockentity/SkullBlockRenderer.renderSkull(Lnet/minecraft/core/Direction;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/SkullModelBase;Lnet/minecraft/client/renderer/RenderType;)V", shift = At.Shift.AFTER))
    private void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, CallbackInfo ci) {
        Item item = itemStack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof AbstractSkullBlock) {
                GameProfile gameProfile2 = null;
                if (itemStack.hasTag()) {
                    CompoundTag compoundTag = itemStack.getTag();
                    if (compoundTag.contains("SkullOwner", 10)) {
                        gameProfile2 = NbtUtils.readGameProfile(compoundTag.getCompound("SkullOwner"));
                    } else if (compoundTag.contains("SkullOwner", 8) && !StringUtils.isBlank(compoundTag.getString("SkullOwner"))) {
                        gameProfile2 = new GameProfile(null, compoundTag.getString("SkullOwner"));
                        compoundTag.remove("SkullOwner");
                        SkullBlockEntity.updateGameprofile(gameProfile2, gameProfile -> compoundTag.put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), gameProfile)));
                    }
                }
                SkullBlock.Type type = ((AbstractSkullBlock) block).getType();
                RenderType renderType = GlowingLayerSkull.getRenderType(type,gameProfile2);
                SkullModelBase skullModelBase = this.skullModels.get(type);
                if (gameProfile2 != null) {
                    GlowingLayerSkull.renderSkull(null, 180.0f, 0.0f, poseStack, multiBufferSource, i, skullModelBase, gameProfile2, renderType);
                }
                return;
            }
        }
    }
}


