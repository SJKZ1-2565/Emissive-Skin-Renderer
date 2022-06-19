package com.sjkz1.emissive_skin_renderer.utils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SJKZ1Helper {
    public static final ExecutorService POOL = Executors.newFixedThreadPool(100, new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(@NotNull Runnable runnable) {
            return new Thread(runnable, String.format("Thread %s", this.counter.incrementAndGet()));
        }
    });
    public static void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(runnable, SJKZ1Helper.POOL);
    }
}

