package com.sjkz1.minetils.gui.screen;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.gui.widget.ColorSliderWidget;
import com.sjkz1.minetils.render.PlayerForRender;
import com.sjkz1.minetils.utils.ColorMatching;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemStack.TooltipSection;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SpecialMemberScreen extends Screen {


    public final Identifier BG = new Identifier(Minetils.MOD_ID + ":textures/gui/background.png");

    private float playerXRot = 0;
    private float ticks = 0;
    public static int ONLINE_USER;

    protected int x;

    private final List<String> list = Lists.newCopyOnWriteArrayList();
    protected final int backgroundWidth = 256;
    protected final int backgroundHeight = 166;
    private boolean err = false;
    private boolean enable = true;
    private ButtonWidget buttonSkin1;
    private ButtonWidget buttonSkin2;
    private ButtonWidget buttonSkin3;
    private ButtonWidget buttonSkin4;
    private ButtonWidget buttonSkin5;
    private ButtonWidget buttonSkin6;
    private ButtonWidget buttonSkin7;
    private ButtonWidget buttonSkin8;
    private ButtonWidget buttonSkin9;
    private ButtonWidget buttonSkin10;
    private ButtonWidget buttonSkin11;
    private ButtonWidget buttonSkin12;

    public SpecialMemberScreen(Text text) {
        super(text);
    }

    @Override
    protected void init() {
        super.init();
        this.addDrawableChild(new ColorSliderWidget((this.width / 2) - 120, 130, 98, 20, Text.of("Delete Rates: " + Minetils.CONFIG.main.palletsRate), Minetils.CONFIG.main.palletsRate) {
            @Override
            protected void updateMessage() {
                setMessage(Text.of("Delete Rate: " + Minetils.CONFIG.main.palletsRate));
            }

            @Override
            protected void applyValue() {
                Minetils.CONFIG.main.palletsRate = MathHelper.floor(MathHelper.clampedLerp(100.0D, 150.0D, this.value));
            }

            @Override
            public void renderButton(MatrixStack matrixStack, int i, int j, float f) {
                super.renderButton(matrixStack, i, j, f);
            }
        });
        this.addDrawableChild(new ButtonWidget((this.width / 2) + 20, 130, 98, 20, Text.of("Create Skin"), buttonWidget -> ColorMatching.createGlowingSkinImage()));

        buttonSkin1 = this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 20, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            buttonWidget.active = false;
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
        }));
        buttonSkin2 = this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 50, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HAT.getMinUvX(),SkinPart.Part.HAT.getMinUvY(),SkinPart.Part.HAT.getMaxUvX(),SkinPart.Part.HAT.getMaxUvY()));
            buttonWidget.active = true;
        }));
        buttonSkin3 = this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 80, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            buttonWidget.active = true;
        }));
        buttonSkin4 = this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 110, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            buttonWidget.active = true;
        }));
        buttonSkin5 = this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 140, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            //		SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            buttonWidget.active = true;
        }));
        buttonSkin6 = this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 170, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            buttonWidget.active = true;
        }));
        buttonSkin7 = this.addDrawableChild(new ButtonWidget((this.width / 2) + 132, 20, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            buttonWidget.active = true;
        }));
        buttonSkin8 = this.addDrawableChild(new ButtonWidget((this.width / 2) + 132, 50, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            buttonWidget.active = true;
        }));
        buttonSkin9 = this.addDrawableChild(new ButtonWidget((this.width / 2) + 132, 80, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            buttonWidget.active = true;
        }));
        buttonSkin10 = this.addDrawableChild(new ButtonWidget((this.width / 2) + 132, 110, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            buttonWidget.active = true;
        }));
        buttonSkin11 = this.addDrawableChild(new ButtonWidget((this.width / 2) + 132, 140, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            buttonWidget.active = true;
        }));
        buttonSkin12 = this.addDrawableChild(new ButtonWidget((this.width / 2) + 132, 170, 80, 20, Text.of("Coming soon..."), (buttonWidget) -> {
            //		SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            buttonWidget.active = true;
        }));

        list.clear();
        list.add("Special Member");
        try {
            int online = ONLINE_USER;
            list.add("Discord Online member :" + online);
        } catch (Exception e) {
            err = true;
            list.add("Couldn't get Discord Member!");
        }
        for (String listName : Minetils.SPECIAL_MEMBER) {
            list.add(listName);
        }
    }

    @Override
    public void render(MatrixStack mat, int mouseX, int mouseY, float partialTicks) {
        ticks += 0.01F * partialTicks;
        playerXRot -= 0.15 * partialTicks;
        if (playerXRot <= -179.85) {
            playerXRot = 180;
        }
        Map<String, ItemStack> MEMBER_LIST = Util.make(Maps.newHashMap(), hashMap -> {
            hashMap.put("SJKZ1", Items.IRON_PICKAXE.getDefaultStack());
            hashMap.put("ToastKung", Items.IRON_PICKAXE.getDefaultStack());
        });
        Color color = Color.getHSBColor(ticks, 0.9f, 1);
        renderBackgroundGG(mat);
        var height = 0;
        for (String string : list) {
            if (string.equals("Special Member")) {
                DrawableHelper.drawStringWithShadow(mat, this.textRenderer, string, (this.width / 2), 35 + height, color.getRGB());
            } else {
                for (String listName : Minetils.SPECIAL_MEMBER) {
                    if (string.equals(listName)) {
                        DrawableHelper.drawStringWithShadow(mat, this.textRenderer, string, (this.width / 2) - 25, 140 + height, 0x4254f5);
                    }
                    if (string.contains("Discord Online member :")) {
                        DrawableHelper.drawStringWithShadow(mat, this.textRenderer, string, (this.width / 2) - 25, 35 + height, 0xFFFFFF);
                    }
                    if (string.contains(listName)) {
                        ItemRenderer itemRenderer = client.getItemRenderer();
                        itemRenderer.zOffset = 100.0F;
                        ItemStack itemStack = ItemStack.EMPTY;
                        for (String i : MEMBER_LIST.keySet()) {
                            if (listName.equals(i) && (MEMBER_LIST.get(i).equals(MEMBER_LIST.get(i).getItem()))) {
                                itemStack = MEMBER_LIST.get(i);
                                itemStack.addEnchantment(Enchantments.MENDING, 1);
                                itemStack.addHideFlag(TooltipSection.ENCHANTMENTS);
                            }
                        }
                        itemRenderer.renderInGui(itemStack, (this.width / 2) - 60, 135 + height);
                        itemRenderer.zOffset = 0.0F;
                    }
                }
            }

            if (err) {
                DrawableHelper.drawStringWithShadow(mat, this.textRenderer, string, (this.width / 2) - 60, 35 + height, 16733525);
            }
            height += 15;
        }
        try {
            InputStream is = (new URL("https://raw.githubusercontent.com/SJKZ1-2565/modJSON-URL/master/glowing_skin_feature.txt")).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            for (String id : rd.lines().toList()) {
                if (id.equals("disable")) {
                    enable = false;
                }
                if (id.equals("enable")) {
                    enable = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!enable) {
            buttonSkin1.active = false;
            buttonSkin2.active = false;
            buttonSkin3.active = false;
            buttonSkin4.active = false;
            buttonSkin5.active = false;
            buttonSkin6.active = false;
            buttonSkin7.active = false;
            buttonSkin8.active = false;
            buttonSkin9.active = false;
            buttonSkin10.active = false;
            buttonSkin11.active = false;
            buttonSkin12.active = false;
        }
        super.render(mat, mouseX, mouseY, partialTicks);
    }

    public void renderBackgroundGG(MatrixStack matrixStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        float shade = 1.0f;
        RenderSystem.setShaderColor(shade, shade, shade, shade);
        RenderSystem.setShaderTexture(0, BG);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        renderBackground(matrixStack);
        this.drawTexture(matrixStack, (this.width / 2) - 128, 15, 0, 0, this.backgroundWidth, this.backgroundHeight);
        renderEntityInInventory((this.width / 2) - 95, 123, 55, playerXRot, 0, new PlayerForRender(this.client.world, Objects.requireNonNull(this.client.player).getGameProfile()));
    }

    //Taken from https://github.com/Intro-Dev/Osmium/blob/fabric/1.17.x/src/main/java/com/intro/client/render/screen/OsmiumCapeOptionsScreen.java
    public static void renderEntityInInventory(int x, int y, int scale, float xRot, float yRot, LivingEntity livingEntity) {
        float xRotClamped = MathHelper.clamp(xRot, -180, 180);
        float yRotClamped = MathHelper.clamp(yRot, -180, 180);
        MatrixStack poseStack = RenderSystem.getModelViewStack();
        poseStack.push();
        poseStack.translate(x, y, 1050.0D);
        poseStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack poseStack2 = new MatrixStack();
        poseStack2.translate(0.0D, 0.0D, 1000);
        poseStack2.scale(scale, scale, scale);
        Quaternion zRotationQuaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion yRotationQuaternion = Vec3f.POSITIVE_X.getDegreesQuaternion(yRotClamped * 20.0F);
        zRotationQuaternion.hamiltonProduct(yRotationQuaternion);
        poseStack2.multiply(zRotationQuaternion);
        float m = livingEntity.bodyYaw;
        float n = livingEntity.getYaw();
        float o = livingEntity.getPitch();
        float p = livingEntity.headYaw;
        float q = livingEntity.headYaw;
        livingEntity.bodyYaw = 180.0F + xRotClamped * 20.0F;
        livingEntity.setYaw(180.0F + xRotClamped * 20.0f);
        livingEntity.setPitch(-yRotClamped * 20.0F);
        livingEntity.headYaw = livingEntity.getYaw();
        livingEntity.headYaw = livingEntity.getYaw();
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        yRotationQuaternion.conjugate();
        entityRenderDispatcher.setRotation(yRotationQuaternion);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack2, immediate, 15728880));
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        livingEntity.bodyYaw = m;
        livingEntity.setYaw(n);
        livingEntity.setPitch(o);
        livingEntity.headYaw = p;
        livingEntity.prevHeadYaw = q;
        poseStack.pop();
        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }

    @Override
    public void close() {
        super.close();
    }
}