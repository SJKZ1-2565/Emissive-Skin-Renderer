package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.crash.CrashReport;

public class ClientInit {

    public static boolean dance = false;

    public static void tick(MinecraftClient client) {
        long l = Runtime.getRuntime().maxMemory();
        long m = Runtime.getRuntime().totalMemory();
        long n = Runtime.getRuntime().freeMemory();
        long o = m - n;
        if (o * 100L / l >= 99) {
            CrashReport crashReport = new CrashReport("Minetils shutdown your minecraft cause your memory is above 99%", new Throwable());
            MinecraftClient.printCrashReport(crashReport);
            MinecraftClient.getInstance().close();
        }
        if (client.player != null) {
            SJKZ1Helper.runAsync(() ->
                    new DiscordMemberThread().start());
        }
        if (Minetils.showPost.wasPressed()) {
            var i = (int) client.player.getX();
            var j = (int) client.player.getY();
            var k = (int) client.player.getZ();
            String pos = "X:" + i + " Y:" + j + " Z:" + k;
            String NetherPos = "Nether position X:" + i / 8 + " Y:" + j + " Z:" + k / 8;
            String OverWorldPose = "OverWorld position X:" + i * 8 + " Y:" + j + " Z:" + k * 8;

            SJKZ1Helper.sendChat(pos);
            if (client.player.world.getDimension().isPiglinSafe()) {
                SJKZ1Helper.sendChat(OverWorldPose);
            } else {
                SJKZ1Helper.sendChat(NetherPos);
            }
        }
    }

    public static void login(ClientPlayNetworkHandler clientPlayNetworkHandler, PacketSender packetSender, MinecraftClient minecraftClient) {
        if (minecraftClient.world != null && !Minetils.CONFIG.main.manualSkinEditor) {
            SJKZ1Helper.runAsync(ColorMatching::createGlowingSkinImage);
        }
    }
}

