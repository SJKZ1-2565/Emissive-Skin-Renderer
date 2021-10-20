package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Matrix4f;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SJKZ1Helper
{
	public static final MinecraftClient mc = MinecraftClient.getInstance();

	public static void renderLabel(LivingEntity entity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		float ticks = (mc.player.age % 20 + mc.getTickDelta()) / 20.0F;
		Color color = Color.getHSBColor(ticks,0.9f,1);
		float health = entity.getHealth();
		String heart = String.valueOf(health) + " HP";
		double d = mc.getEntityRenderDispatcher().getSquaredDistanceToCamera(entity);
		if (!(d > 4096.0D)) {
			if (Minetils.CONFIG.getConfig().showHealthStatus && !mc.options.hudHidden && !entity.isInvisible() && !(entity instanceof ArmorStandEntity)) {
				float f = Minetils.CONFIG.getConfig().yPositionHealthStatus + 0.5F;
				matrixStack.push();
				matrixStack.translate(0.0D, f, 0.0D);
				matrixStack.multiply(mc.getEntityRenderDispatcher().camera.getRotation());
				matrixStack.scale(-0.025F, -0.025F, 0.025F);
				Matrix4f matrix4f = matrixStack.peek().getModel();
				float g = mc.options.getTextBackgroundOpacity(0.25F);
				int k = (int) (g * 255.0F) << 24;
				TextRenderer textRenderer = mc.textRenderer;
				float l = -textRenderer.getWidth(heart) / 2;
				textRenderer.draw(heart, l, -15, Minetils.CONFIG.getConfig().healthStatusRainbowColor ? color.getRGB() : Minetils.CONFIG.getConfig().healthStatusColor, true, matrix4f, vertexConsumerProvider, false, 0, i);
				matrixStack.pop();
			}
		}
	}
	public static void renderLabelTamed(HorseBaseEntity entity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		float ticks = (mc.player.age % 20 + mc.getTickDelta()) / 20.0F;
		Color color = Color.getHSBColor(ticks,0.9f,1);
		boolean tamed = entity.isTame();
		String tamedHorseText = tamed ? Formatting.GREEN + "Tamed" :  Formatting.RED + "Untamed";
		double d = mc.getEntityRenderDispatcher().getSquaredDistanceToCamera(entity);
		if (!(d > 4096.0D)) {
			if (Minetils.CONFIG.getConfig().showTamedHorse && !mc.options.hudHidden && !entity.isInvisible()) {
				float f = Minetils.CONFIG.getConfig().yPositionHorseDisplay + 0.5F;
				matrixStack.push();
				matrixStack.translate(0.0D, f, 0.0D);
				matrixStack.multiply(mc.getEntityRenderDispatcher().camera.getRotation());
				matrixStack.scale(-0.025F, -0.025F, 0.025F);
				Matrix4f matrix4f = matrixStack.peek().getModel();
				float g = mc.options.getTextBackgroundOpacity(0.25F);
				int k = (int) (g * 255.0F) << 24;
				TextRenderer textRenderer = mc.textRenderer;
				float l = -textRenderer.getWidth(tamedHorseText) / 2;
				textRenderer.draw(tamedHorseText, l, -15, Minetils.CONFIG.getConfig().healthStatusRainbowColor ? color.getRGB() : Minetils.CONFIG.getConfig().healthStatusColor, true, matrix4f, vertexConsumerProvider, false, 0, i);
				matrixStack.pop();
			}
		}
	}

	private static final ExecutorService POOL = Executors.newFixedThreadPool(100, new ThreadFactory()
	{
		private final AtomicInteger counter = new AtomicInteger(0);

		@Override
		public Thread newThread(Runnable runnable)
		{
			return new Thread(runnable, String.format("Thread %s", this.counter.incrementAndGet()));
		}
	});

	public static void runAsync(Runnable runnable)
	{
		CompletableFuture.runAsync(runnable, SJKZ1Helper.POOL);
	}



	@Deprecated
	public static BufferedReader get(String path) throws Exception
	{
		URL url = new URL("https://raw.githubusercontent.com/SJKZ1-2565/modJSON-URL/master/" + path);

		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
			return in;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedReader getData(String fileName)
	{
		try
		{
			//https://raw.githubusercontent.com/SteveKunG/SkyBlockcatia/skyblock_data/
			URL url = new URL("https://raw.githubusercontent.com/SJKZ1-2565/modJSON-URL/master/" + fileName);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
			return in;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Minetils.LOGGER.error("Couldn't get {} from remote, using local data", fileName);
			BufferedReader in = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("assets/skyblockcatia/api/" + fileName)));
			return in;
		}
	}
}

