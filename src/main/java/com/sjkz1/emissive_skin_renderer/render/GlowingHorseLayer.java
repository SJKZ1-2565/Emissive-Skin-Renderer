package com.sjkz1.emissive_skin_renderer.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.DyeableHorseArmorItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GlowingHorseLayer
        extends RenderLayer<Horse, HorseModel<Horse>> {
    private final HorseModel<Horse> model;

    public GlowingHorseLayer(RenderLayerParent<Horse, HorseModel<Horse>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new HorseModel<>(entityModelSet.bakeLayer(ModelLayers.HORSE_ARMOR));
    }

    private final ResourceLocation resourceLocation = new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/entity/skin/horse_armor_leather.png");

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, Horse horse, float f, float g, float h, float j, float k, float l) {
        float p;
        float o;
        float n;
        ItemStack itemStack = horse.getArmor();
        if (!(itemStack.getItem() instanceof HorseArmorItem horseArmorItem)) {
            return;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(horse, f, g, h);
        this.model.setupAnim(horse, f, g, j, k, l);
        if (horseArmorItem instanceof DyeableHorseArmorItem && EmissiveSkinRenderer.CONFIG.main.glowingHorseArmor) {
            int m = ((DyeableHorseArmorItem) horseArmorItem).getColor(itemStack);
            n = (float) (m >> 16 & 0xFF) / 255.0f;
            o = (float) (m >> 8 & 0xFF) / 255.0f;
            p = (float) (m & 0xFF) / 255.0f;
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.dragonExplosionAlpha(resourceLocation));
            this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, n, o, p, 1.0f);
        }
    }
}
