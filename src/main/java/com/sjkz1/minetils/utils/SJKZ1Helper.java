package com.sjkz1.minetils.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SJKZ1Helper {
    public static final MinecraftClient mc = MinecraftClient.getInstance();

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

    public static void sendChat(String string) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(string));
    }

    public static void sendHotBarText(String string) {
        MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.of(string), false);
    }

    public static void drawKeyStroke(MatrixStack matrixStack) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        GameOptions options = minecraftClient.options;
        TextRenderer textRenderer = minecraftClient.inGameHud.getTextRenderer();

        InGameHud.fill(matrixStack,textRenderer.getWidth("W"), textRenderer.fontHeight + 65,textRenderer.getWidth("W") +30, textRenderer.fontHeight + 40 , minecraftClient.options.getTextBackgroundColor(0.5f));
        textRenderer.drawWithShadow(matrixStack, "W", 20.0f + textRenderer.getWidth("W"), 50.0f + textRenderer.fontHeight, options.forwardKey.isPressed() ? 0xe9f542 : 0xFFFFFF);
        textRenderer.drawWithShadow(matrixStack, "S", 20.0f + textRenderer.getWidth("S"), 70.0f + textRenderer.fontHeight, options.backKey.isPressed() ? 0xe9f542 : 0xFFFFFF);
        textRenderer.drawWithShadow(matrixStack, "D", 30.0f + textRenderer.getWidth("D"), 70.0f + textRenderer.fontHeight, options.rightKey.isPressed() ? 0xe9f542 : 0xFFFFFF);
        textRenderer.drawWithShadow(matrixStack, "A", 10.0f + textRenderer.getWidth("A"), 70.0f + textRenderer.fontHeight, options.leftKey.isPressed() ? 0xe9f542 : 0xFFFFFF);

    }

}

