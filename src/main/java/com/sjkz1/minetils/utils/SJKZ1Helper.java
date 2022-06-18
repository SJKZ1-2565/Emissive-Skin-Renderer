package com.sjkz1.minetils.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SJKZ1Helper {
    public static final Minecraft mc = Minecraft.getInstance();

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
        mc.gui.getChat().addMessage(Component.literal(string));
    }

    public static void sendHotBarText(String string) {
        mc.gui.setOverlayMessage(Component.literal(string), false);
    }

}

