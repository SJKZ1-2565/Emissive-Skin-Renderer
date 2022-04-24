package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
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

    public static void drawBox(ItemStack itemStack,int i,int j)
    {
        MinecraftClient.getInstance().getItemRenderer().renderInGuiWithOverrides(itemStack, i + 1, j + 1);
    }

}

