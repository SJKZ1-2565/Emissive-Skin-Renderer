package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.config.MinetilsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SJKZ1Helper {
    public static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void renderLabel(LivingEntity entity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        float ticks = (Objects.requireNonNull(mc.player).age % 20 + mc.getTickDelta()) / 20.0F;
        Color color = Color.getHSBColor(ticks, 0.9f, 1);
        float health = entity.getHealth();
        String heart = health + " HP";
        double d = mc.getEntityRenderDispatcher().getSquaredDistanceToCamera(entity);
        if (!(d > 4096.0D)) {
            if (Minetils.CONFIG.main.showTamedHorse && !mc.options.hudHidden && !entity.isInvisible() && !(entity instanceof ArmorStandEntity)) {
                float f = Minetils.CONFIG.main.yPositionHealthStatus + 0.5F;
                matrixStack.push();
                matrixStack.translate(0.0D, f, 0.0D);
                matrixStack.multiply(mc.getEntityRenderDispatcher().camera.getRotation());
                matrixStack.scale(-0.025F, -0.025F, 0.025F);
                Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
                TextRenderer textRenderer = mc.textRenderer;
                float l = -textRenderer.getWidth(heart) / 2;
                textRenderer.draw(heart, l, -15, Minetils.CONFIG.main.healthStatusRainbowColor ? color.getRGB() : Minetils.CONFIG.main.healthStatusColor, true, matrix4f, vertexConsumerProvider, false, 0, i);
                matrixStack.pop();
            }
        }
    }

    public static void renderLabelTamed(HorseBaseEntity entity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        float ticks = (Objects.requireNonNull(mc.player).age % 20 + mc.getTickDelta()) / 20.0F;
        Color color = Color.getHSBColor(ticks, 0.9f, 1);
        boolean tamed = entity.isTame();
        String tamedHorseText = tamed ? Formatting.GREEN + "Tamed" : Formatting.RED + "Untamed";
        double d = mc.getEntityRenderDispatcher().getSquaredDistanceToCamera(entity);
        if (!(d > 4096.0D)) {
            if (Minetils.CONFIG.main.showTamedHorse && !mc.options.hudHidden && !entity.isInvisible()) {
                float f = Minetils.CONFIG.main.yPositionHorseDisplay + 0.5F;
                matrixStack.push();
                matrixStack.translate(0.0D, f, 0.0D);
                matrixStack.multiply(mc.getEntityRenderDispatcher().camera.getRotation());
                matrixStack.scale(-0.025F, -0.025F, 0.025F);
                Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
                TextRenderer textRenderer = mc.textRenderer;
                float l = -textRenderer.getWidth(tamedHorseText) / 2;
                textRenderer.draw(tamedHorseText, l, -15, Minetils.CONFIG.main.healthStatusRainbowColor ? color.getRGB() : Minetils.CONFIG.main.healthStatusColor, true, matrix4f, vertexConsumerProvider, false, 0, i);
                matrixStack.pop();
            }
        }
    }

    public static final ExecutorService POOL = Executors.newFixedThreadPool(100, new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(@NotNull Runnable runnable) {
            return new Thread(runnable, String.format("Thread %s", this.counter.incrementAndGet()));
        }
    });
    private static final ExecutorService POOL2 = Executors.newFixedThreadPool(1000000, new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(@NotNull Runnable runnable) {
            return new Thread(runnable, String.format("Thread %s", this.counter.incrementAndGet()));
        }
    });

    public static void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(runnable, SJKZ1Helper.POOL);
    }

    public static void runSlowAsync(Runnable runnable) {
        CompletableFuture.runAsync(runnable, SJKZ1Helper.POOL2);
    }
}

