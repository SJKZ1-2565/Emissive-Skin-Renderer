package com.sjkz1.emissive_skin_renderer.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sjkz1.emissive_skin_renderer.render.GlowingLayerSkull;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(CustomHeadLayer.class)
public class CustomHeadLayerMixin<T extends LivingEntity> {

    @Shadow
    @Final
    private Map<SkullBlock.Type, SkullModelBase> skullModels;

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SkullBlockRenderer;renderSkull(Lnet/minecraft/core/Direction;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/SkullModelBase;Lnet/minecraft/client/renderer/RenderType;)V", shift = At.Shift.AFTER))
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        ItemStack itemStack = ((LivingEntity) livingEntity).getItemBySlot(EquipmentSlot.HEAD);
        if (itemStack.isEmpty()) {
            return;
        }
        Item item = itemStack.getItem();
        CompoundTag compoundTag;
        GameProfile gameProfile = null;
        if (itemStack.hasTag() && (compoundTag = itemStack.getTag()).contains("SkullOwner", 10)) {
            gameProfile = NbtUtils.readGameProfile(compoundTag.getCompound("SkullOwner"));
        }
        SkullBlock.Type type = ((AbstractSkullBlock) ((BlockItem) item).getBlock()).getType();
        SkullModelBase skullModelBase = this.skullModels.get(type);
        RenderType renderType = GlowingLayerSkull.getRenderType(type,gameProfile);
        if (gameProfile != null) {
            GlowingLayerSkull.renderSkull(null, 180.0f, f, poseStack, multiBufferSource, i, skullModelBase, gameProfile,renderType);
        }
    }

}
