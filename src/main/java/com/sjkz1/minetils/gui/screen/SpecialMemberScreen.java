package com.sjkz1.minetils.gui.screen;


import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.render.Player;

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
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

public class SpecialMemberScreen extends Screen {


	public final Identifier BG = new Identifier(Minetils.MOD_ID + ":textures/gui/background.png");

	private float playerXRot = 0;
	private float ticks = 0;
	public static int ONLINE_USER;

	protected int x;

	private final List<String> list = new CopyOnWriteArrayList<>();
	protected final int backgroundWidth = 256;
	protected final int backgroundHeight = 166;
	private boolean err = false;

	public SpecialMemberScreen(Text text) {
		super(text);
	}

	@Override
	protected void init() {
		super.init();
		int j = this.height / 4 + 48;
		String OnOf = isDarkTheme() ? Formatting.field_1060 + "ON" : Formatting.field_1061+"OFF";
		this.addDrawableChild(new ButtonWidget(this.width/2+140,j-94,20,20,Text.of(OnOf),buttonWidget -> {
			Minetils.CONFIG.getConfig().darkTheme = !Minetils.CONFIG.getConfig().darkTheme;
			try {
				Minetils.CONFIG.saveConfig();
			} catch (IOException ignored) {}
		}));
		this.addDrawableChild(new ButtonWidget(this.width / 2-50, j - 15, 98, 20, Text.of("Skin Editor"), button -> client.setScreen(new SkinEditorScreen(Text.of("")))
				));
		list.clear();
		list.add("Special Member Wardrobe");
		try {
			int online = ONLINE_USER;
			list.add("Discord Online member :" + online);
		}
		catch (Exception e) {
			err = true;
			list.add("Couldn't get Discord Member!");
		}
	}

	@Override
	public void render(MatrixStack mat, int mouseX, int mouseY, float partialTicks) {
		ticks += 0.01F * partialTicks;
		playerXRot -= 0.15 * partialTicks;
		if (playerXRot <= -179.85) {
			playerXRot = 180;
		}

		Color color = Color.getHSBColor(ticks, 0.9f, 1);
		renderBackgroundGG(mat);
		var height = 0;
		for(String string : list)
		{
			if(string.contains("Special Member Wardrobe"))
			{
				DrawableHelper.drawStringWithShadow(mat, this.textRenderer, string, this.width / 2 - 60, 35 + height, color.getRGB());
			}
			else
			{
				DrawableHelper.drawStringWithShadow(mat, this.textRenderer, string, this.width / 2 - 60, 35 + height,16777215);
			}

			if(err)
			{
				DrawableHelper.drawStringWithShadow(mat, this.textRenderer, string, this.width / 2 - 60, 35 + height, 16733525);
			}
			height += 12;
		}
		super.render(mat, mouseX, mouseY, partialTicks);
	}

	public void renderBackgroundGG(MatrixStack matrixStack) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		float shade = isDarkTheme() ? 0.5f : 1.0f;
		RenderSystem.setShaderColor(shade, shade, shade, shade);
		RenderSystem.setShaderTexture(0, BG);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
		renderBackground(matrixStack);
		this.drawTexture(matrixStack, (this.width/2)-130, 15, 0, 0, this.backgroundWidth, this.backgroundHeight);
		matrixStack.push();
		matrixStack.translate(this.width/2-127,15,100);
		matrixStack.scale(0.75F,0.75F,0.75F);
		matrixStack.pop();
		assert this.client != null;
		renderEntityInInventory(this.width / 2 - 114,67,  25,playerXRot,0,new Player(this.client.world,this.client.player.getGameProfile()));
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

	public boolean isDarkTheme() {
		return Minetils.CONFIG.getConfig().darkTheme;
	}

	@Override
	public void onClose() {
		super.onClose();
		try {
			Minetils.CONFIG.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




}