package com.sjkz1.minetils.gui.screen;


import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.gui.widget.ColorSliderWidget;
import com.sjkz1.minetils.render.Player;
import com.sjkz1.minetils.utils.ColorMatching;
import com.sjkz1.minetils.utils.SJKZ1Helper;
import com.sjkz1.minetils.utils.enums.SkinPart;

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
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
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
		this.addDrawableChild(new ColorSliderWidget((this.width / 2) - 120, 130, 98, 20, Text.of("Delete Rate: " + Minetils.CONFIG.getConfig().palletsRate), Minetils.CONFIG.getConfig().palletsRate) {
			@Override
			protected void updateMessage() {
				setMessage(Text.of("Delete Rate: " + Minetils.CONFIG.getConfig().palletsRate));
			}

			@Override
			protected void applyValue() {
				Minetils.CONFIG.getConfig().palletsRate = MathHelper.floor(MathHelper.clampedLerp(100.0D, 150.0D, this.value));
				try {
					Minetils.CONFIG.saveConfig();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void renderButton(MatrixStack matrixStack, int i, int j, float f) {
				super.renderButton(matrixStack, i, j, f);
			}
		});
		this.addDrawableChild(new ButtonWidget((this.width / 2) + 20, 130, 98, 20, Text.of("Create Skin"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImage());
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 20, 80, 20, Text.of("Head"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 50, 80, 20, Text.of("Hat"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 80, 80, 20, Text.of("Body"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 110, 80, 20, Text.of("Jacket"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 140, 80, 20, Text.of("Right-Arm"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) - 211, 170, 80, 20, Text.of("Right-Sleeve"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) + 127, 20, 80, 20, Text.of("Left-Arm"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) + 127, 50, 80, 20, Text.of("Left-Sleeve"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) + 127, 80, 80, 20, Text.of("Left-Leg"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) + 127, 110, 80, 20, Text.of("Left-Pant"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) + 127, 140, 80, 20, Text.of("Right-Leg"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
		this.addDrawableChild(new ButtonWidget((this.width / 2) + 127, 170, 80, 20, Text.of("Right-Pant"), (buttonWidget) -> {
			SJKZ1Helper.runAsync(() -> ColorMatching.createGlowingSkinImageWithCustomUV(SkinPart.Part.HEAD.getMaxUvX(),SkinPart.Part.HEAD.getMaxUvY()));
		}));
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
		for(String listName : Minetils.SPECIAL_MEMBER)
		{
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
		Color color = Color.getHSBColor(ticks, 0.9f, 1);
		renderBackgroundGG(mat);
		var height = 0;
		for(String string : list)
		{
			if(string.equals("Special Member Wardrobe"))
			{
				DrawableHelper.drawStringWithShadow(mat, this.textRenderer, string, (this.width / 2) - 60, 35 + height, color.getRGB());
			}
			else
			{
				for(String listName : Minetils.SPECIAL_MEMBER)
				{
					if(string.equals(listName))
					{
						DrawableHelper.drawStringWithShadow(mat, this.textRenderer, "Member: " + string, (this.width / 2) - 60, 140 + height,  0x4254f5);
					}
					if(string.contains("Discord Online member :"))
					{
						DrawableHelper.drawStringWithShadow(mat, this.textRenderer, string, (this.width / 2) - 60, 35 + height,  0xFFFFFF);
					}
					if(string.contains(listName))
					{

						ItemRenderer itemRenderer = client.getItemRenderer();
						itemRenderer.zOffset = 100.0F;
						itemRenderer.renderInGui(new ItemStack(Items.NETHERITE_PICKAXE),(this.width / 2) + 60, 135 + height);
						itemRenderer.zOffset = 0.0F;
					}
				}
			}

			if(err)
			{
				DrawableHelper.drawStringWithShadow(mat, this.textRenderer, string, (this.width / 2) - 60, 35 + height, 16733525);
			}
			height += 15;
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
		this.drawTexture(matrixStack, (this.width/2)-130, 15, 0, 0, this.backgroundWidth, this.backgroundHeight);
		renderEntityInInventory((this.width / 2)-114,67,  25,playerXRot,0,new Player(this.client.world, Objects.requireNonNull(this.client.player).getGameProfile()));
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
	public void onClose() {
		super.onClose();
		try {
			Minetils.CONFIG.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}








}