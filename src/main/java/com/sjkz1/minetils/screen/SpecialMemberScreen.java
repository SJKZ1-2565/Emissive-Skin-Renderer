package com.sjkz1.minetils.screen;


import com.google.gson.Gson;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.utils.Donate;
import com.sjkz1.minetils.utils.SJKZ1Helper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

import java.awt.*;

public class SpecialMemberScreen extends Screen {


    public Identifier BG = new Identifier(Minetils.MOD_ID + ":textures/gui/background.png");
    private float playerXRot = 0;
    private float ticks = 0;
    private static final Gson GSON = new Gson();
    public static int DONATE_TOTAL;

    protected int x;
    protected int y;

    protected int backgroundWidth = 256;
    protected int backgroundHeight = 166;


    public SpecialMemberScreen(Text text) {
        super(text);
    }


    @Override
    protected void init() {
        super.init();
        int j = this.height / 4 + 48;

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, j + 72 + 12, 98, 20, Text.of("Switch"), (buttonWidget) -> {
            Minetils.CONFIG.getConfig().IdentifierOrdinal++;
            Minetils.CONFIG.getConfig().IdentifierOrdinal %= 6;
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 20, j + 72 + 12, 98, 20, Text.of("Mode"), (buttonWidget) -> Minetils.CONFIG.getConfig().SpecialCape = !Minetils.CONFIG.getConfig().SpecialCape));
    }

    @Override
    public void render(MatrixStack mat, int mouseX, int mouseY, float partialTicks) {
        ticks += 0.005F * partialTicks;
        playerXRot -= 0.15 * partialTicks;
        if (playerXRot <= -179.85) {
            playerXRot = 180;
        }

        renderBackgroundGG(mat);
        super.render(mat, mouseX, mouseY, partialTicks);
        //renderAxolotlEntityInInventory(this.width / 2 - 165, 40,  35,playerXRot,0,new AxolotlEntity(EntityType.AXOLOTL,client.world));
    }

    public void renderBackgroundGG(MatrixStack matrixStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BG);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        int k = this.x;
        int l = this.y;
        this.drawTexture(matrixStack, k/2+120 , 15, 0, 0, this.backgroundWidth, this.backgroundHeight);
        Color color = Color.getHSBColor(ticks, 0.9f, 1);
        String name = Minetils.SPECIAL_MEMBER.toString();
        String SpecialMemberName = name.replace("[", "");
        SpecialMemberName = SpecialMemberName.replace("]", "");
        SpecialMemberName = SpecialMemberName.replace(",", "");
        if(SpecialMemberName != null)
        {
            matrixStack.push();
            matrixStack.translate(k/2+100,15,100);
            matrixStack.scale(0.75F,0.75F,0.75F);
            drawStringWithShadow(matrixStack, this.textRenderer, Text.of("✔ Special Member Wardrobe").asString(), k / 2 + 135, 15, Formatting.DARK_GREEN.getColorValue());
            drawCenteredText(matrixStack, this.textRenderer, Text.of("☀ Total Donate " + DONATE_TOTAL).asString(), k / 2+200, 25, Formatting.GOLD.getColorValue());
            drawCenteredText(matrixStack, this.textRenderer, Text.of("‼ Feature for this screen, 37%").asString(), k / 2+200, 35, Formatting.RED.getColorValue());
            drawCenteredText(matrixStack, this.textRenderer, Text.of(SpecialMemberName).asString(), k / 2  + 220, 65, color.getRGB());
            matrixStack.pop();
        }
        renderEntityInInventory(k / 2 + 136,67,  25,playerXRot,0,this.client.player);
    }

    //Taken from https://github.com/Intro-Dev/Osmium/blob/fabric/1.17.x/src/main/java/com/intro/client/render/screen/OsmiumCapeOptionsScreen.java
    public static void renderEntityInInventory(int x, int y, int scale, float xRot, float yRot, LivingEntity livingEntity) {
        float xRotClamped = MathHelper.clamp(xRot, -180, 180);
        float yRotClamped = MathHelper.clamp(yRot, -180, 180);
        MatrixStack poseStack = RenderSystem.getModelViewStack();
        poseStack.push();
        // translate far from screen
        poseStack.translate(x, y, 1050.0D);
        poseStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack poseStack2 = new MatrixStack();
        // translate far from screen
        poseStack2.translate(0.0D, 0.0D, 1000);
        poseStack2.scale((float)scale, (float)scale, (float)scale);
        // flip to right side
        Quaternion zRotationQuaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion yRotationQuaternion = Vec3f.POSITIVE_X.getDegreesQuaternion(yRotClamped * 20.0F);
        zRotationQuaternion.hamiltonProduct(yRotationQuaternion);
        poseStack2.multiply(zRotationQuaternion);
        // set entity rotations
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
        // setup for rendering
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        yRotationQuaternion.conjugate();
        entityRenderDispatcher.setRotation(yRotationQuaternion);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack2, immediate, 15728880));
        immediate.draw();
        // undo changes
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

    public static void renderAxolotlEntityInInventory(int x, int y, int scale, float xRot, float yRot, AxolotlEntity axolotlEntity) {
        float xRotClamped = MathHelper.clamp(xRot, -180, 180);
        float yRotClamped = MathHelper.clamp(yRot, -180, 180);
        MatrixStack poseStack = RenderSystem.getModelViewStack();
        poseStack.push();
        // translate far from screen
        poseStack.translate(x, y, 1050.0D);
        poseStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack poseStack2 = new MatrixStack();
        // translate far from screen
        poseStack2.translate(0.0D, 0.0D, 1000.0D);
        poseStack2.scale((float)scale, (float)scale, (float)scale);
        // flip to right side
        Quaternion zRotationQuaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion yRotationQuaternion = Vec3f.POSITIVE_X.getDegreesQuaternion(yRotClamped * 20.0F);
        zRotationQuaternion.hamiltonProduct(yRotationQuaternion);
        poseStack2.multiply(zRotationQuaternion);
        // set entity rotations
        float m = axolotlEntity.bodyYaw;
        float n = axolotlEntity.getYaw();
        float o = axolotlEntity.getPitch();
        float p = axolotlEntity.headYaw;
        float q = axolotlEntity.headYaw;
        axolotlEntity.setLoveTicks(axolotlEntity.age);
        axolotlEntity.bodyYaw = 180.0F + xRotClamped * 20.0F;
        axolotlEntity.setYaw(180.0F + xRotClamped * 20.0f);
        axolotlEntity.setPitch(-yRotClamped * 20.0F);
        axolotlEntity.headYaw = axolotlEntity.getYaw();
        axolotlEntity.headYaw = axolotlEntity.getYaw();
        // setup for rendering
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        yRotationQuaternion.conjugate();
        entityRenderDispatcher.setRotation(yRotationQuaternion);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(axolotlEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack2, immediate, 15728880));
        immediate.draw();
        // undo changes
        entityRenderDispatcher.setRenderShadows(true);
        axolotlEntity.bodyYaw = m;
        axolotlEntity.setYaw(n);
        axolotlEntity.setPitch(o);
        axolotlEntity.headYaw = p;
        axolotlEntity.prevHeadYaw = q;
        poseStack.pop();
        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }


    public static void getMisc()
    {
            Donate donate = GSON.fromJson(SJKZ1Helper.getData("donate.json"), Donate.class);
            DONATE_TOTAL = donate.getTotalDonate();
    }


    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f) {
        return super.mouseScrolled(d, e, f);
    }
}