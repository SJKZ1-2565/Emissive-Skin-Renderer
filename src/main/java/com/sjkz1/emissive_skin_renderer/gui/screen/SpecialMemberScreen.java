package com.sjkz1.emissive_skin_renderer.gui.screen;


import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import com.sjkz1.emissive_skin_renderer.render.PlayerForRender;
import com.sjkz1.emissive_skin_renderer.utils.ColorMatching;
import com.sjkz1.emissive_skin_renderer.utils.SJKZ1Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class SpecialMemberScreen extends Screen {


    public final ResourceLocation BG = new ResourceLocation(EmissiveSkinRenderer.MOD_ID + ":textures/gui/background.png");

    private float playerXRot = 0;
    private float ticks = 0;
    public static int ONLINE_USER;

    protected int imageWidth = 256;
    protected int imageHeight = 166;

    private final List<String> list = Lists.newCopyOnWriteArrayList();
    private boolean err = false;
    private boolean enable = true;
    private Button buttonSkin1;
    private Button buttonSkin2;
    private Button buttonSkin3;
    private Button buttonSkin4;
    private Button buttonSkin5;
    private Button buttonSkin6;
    private Button buttonSkin7;
    private Button buttonSkin8;
    private Button buttonSkin9;
    private Button buttonSkin10;
    private Button buttonSkin11;
    private Button buttonSkin12;

    public SpecialMemberScreen(Component component) {
        super(component);
    }


    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new Button((this.width / 2) + 20, 130, 98, 20, Component.literal("Create Skin"), Button -> ColorMatching.createGlowingSkinImage()));
        this.addRenderableWidget(new AbstractSliderButton((this.width / 2) - 120, 130, 98, 20, Component.literal("Delete Rates: " + EmissiveSkinRenderer.CONFIG.main.palletsRate), EmissiveSkinRenderer.CONFIG.main.palletsRate) {
            @Override
            protected void updateMessage() {
                setMessage(Component.literal("Delete Rate: " + EmissiveSkinRenderer.CONFIG.main.palletsRate));
            }

            @Override
            protected void applyValue() {
                EmissiveSkinRenderer.CONFIG.main.palletsRate = Mth.floor(Mth.clampedLerp(50.0D, 100.0D, this.value));
            }
        });

        buttonSkin1 = this.addRenderableWidget(new Button((this.width / 2) - 211, 20, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            Button.active = false;
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
        }));
        buttonSkin2 = this.addRenderableWidget(new Button((this.width / 2) - 211, 50, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HAT.getMinUvX(),SkinPart.Part.HAT.getMinUvY(),SkinPart.Part.HAT.getMaxUvX(),SkinPart.Part.HAT.getMaxUvY()));
            Button.active = true;
        }));
        buttonSkin3 = this.addRenderableWidget(new Button((this.width / 2) - 211, 80, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            Button.active = true;
        }));
        buttonSkin4 = this.addRenderableWidget(new Button((this.width / 2) - 211, 110, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            Button.active = true;
        }));
        buttonSkin5 = this.addRenderableWidget(new Button((this.width / 2) - 211, 140, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            //		SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            Button.active = true;
        }));
        buttonSkin6 = this.addRenderableWidget(new Button((this.width / 2) - 211, 170, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            Button.active = true;
        }));
        buttonSkin7 = this.addRenderableWidget(new Button((this.width / 2) + 132, 20, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            Button.active = true;
        }));
        buttonSkin8 = this.addRenderableWidget(new Button((this.width / 2) + 132, 50, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            Button.active = true;
        }));
        buttonSkin9 = this.addRenderableWidget(new Button((this.width / 2) + 132, 80, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            Button.active = true;
        }));
        buttonSkin10 = this.addRenderableWidget(new Button((this.width / 2) + 132, 110, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            Button.active = true;
        }));
        buttonSkin11 = this.addRenderableWidget(new Button((this.width / 2) + 132, 140, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            //			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            Button.active = true;
        }));
        buttonSkin12 = this.addRenderableWidget(new Button((this.width / 2) + 132, 170, 80, 20, Component.literal("Coming soon..."), (Button) -> {
            //		SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMinUvX(),SkinPart.Part.HEAD.getMinUvY(),SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
            Button.active = true;
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
        for (String listName : EmissiveSkinRenderer.SPECIAL_MEMBER) {
            list.add(listName);
        }
    }

    @Override
    public void render(PoseStack mat, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(mat);
        ticks += 0.01F * partialTicks;
        playerXRot -= 0.15 * partialTicks;
        if (playerXRot <= -179.85) {
            playerXRot = 180;
        }
        Color color = Color.getHSBColor(ticks, 0.9f, 1);
        renderBackgroundGG(mat);
        var height = 0;
        for (String string : list) {
            if (string.equals("Special Member")) {
                GuiComponent.drawString(mat, this.font, string, (this.width / 2), 35 + height, color.getRGB());
            } else {
                for (String listName : EmissiveSkinRenderer.SPECIAL_MEMBER) {
                    if (string.equals(listName)) {
                        GuiComponent.drawString(mat, this.font, string, (this.width / 2) - 25, 140 + height, color.getRGB());
                    }
                    if (string.contains("Discord Online member :")) {
                        GuiComponent.drawString(mat, this.font, string, (this.width / 2) - 25, 35 + height, 0xFFFFFF);
                    }
                }
            }

            if (err) {
                GuiComponent.drawString(mat, this.font, string, (this.width / 2) - 60, 35 + height, 16733525);
            }
            height += 15;
        }
        super.render(mat, mouseX, mouseY, partialTicks);
    }

    public void renderBackgroundGG(PoseStack poseStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BG);
        this.blit(poseStack, (this.width / 2) - 128, 15, 0, 0, this.imageWidth, this.imageHeight);
        renderEntityInInventory((this.width / 2) - 95, 123, 55, playerXRot, 0, new PlayerForRender(this.minecraft.level, Objects.requireNonNull(this.minecraft.player).getGameProfile(), null));
    }

    //Taken from https://github.com/Intro-Dev/Osmium/blob/fabric/1.17.x/src/main/java/com/intro/client/render/screen/OsmiumCapeOptionsScreen.java
    public static void renderEntityInInventory(int x, int y, int scale, float xRot, float yRot, LivingEntity livingEntity) {
        float xRotClamped = Mth.clamp(xRot, -180, 180);
        float yRotClamped = Mth.clamp(yRot, -180, 180);
        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.translate(x, y, 1050.0D);
        poseStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack poseStack2 = new PoseStack();
        poseStack2.translate(0.0D, 0.0D, 1000);
        poseStack2.scale(scale, scale, scale);
        Quaternion zRotationQuaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion yRotationQuaternion = Vector3f.XP.rotationDegrees(yRotClamped * 20.0F);
        zRotationQuaternion.mul(yRotationQuaternion);
        poseStack2.mulPose(zRotationQuaternion);
        float m = livingEntity.yBodyRot;
        float n = livingEntity.getYRot();
        float o = livingEntity.getXRot();
        float p = livingEntity.yHeadRot;
        float q = livingEntity.yHeadRot;
        livingEntity.yBodyRot = 180.0F + xRotClamped * 20.0F;
        livingEntity.setYRot(180.0F + xRotClamped * 20.0f);
        livingEntity.setXRot(-yRotClamped * 20.0F);
        livingEntity.yHeadRot = livingEntity.getXRot();
        livingEntity.yHeadRot = livingEntity.getYRot();
        Lighting.setupForEntityInInventory();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        yRotationQuaternion.conj();
        entityRenderDispatcher.overrideCameraOrientation(yRotationQuaternion);
        entityRenderDispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(livingEntity, 0.0, 0.0, 0.0, 0.0F, 1.0F, poseStack2, bufferSource, 15728880));
        bufferSource.endBatch();
        entityRenderDispatcher.setRenderShadow(true);
        livingEntity.yBodyRot = m;
        livingEntity.setYRot(n);
        livingEntity.setXRot(o);
        livingEntity.yHeadRotO = p;
        livingEntity.yHeadRot = q;
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public void tick() {
        super.tick();
        SJKZ1Helper.runAsync(() -> {
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
            }
        });
        buttonSkin1.active = enable;
        buttonSkin2.active = enable;
        buttonSkin3.active = enable;
        buttonSkin4.active = enable;
        buttonSkin5.active = enable;
        buttonSkin6.active = enable;
        buttonSkin7.active = enable;
        buttonSkin8.active = enable;
        buttonSkin9.active = enable;
        buttonSkin10.active = enable;
        buttonSkin11.active = enable;
        buttonSkin12.active = enable;
    }
}