package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.crash.CrashReport;

public abstract class ClientInit {

    public static boolean dance = false;

    public static void tick(MinecraftClient client) {
        long l = Runtime.getRuntime().maxMemory();
        long m = Runtime.getRuntime().totalMemory();
        long n = Runtime.getRuntime().freeMemory();
        long o = m - n;
        if (o * 100L / l >= 99) {
            MinecraftClient.getInstance().close();
            CrashReport crashReport = new CrashReport("Minetils shutdown your minecraft cause your memory is above 99%", new Throwable());
            MinecraftClient.printCrashReport(crashReport);
        }
        if (client.player != null) {
            SJKZ1Helper.runAsync(() ->
                    new DiscordMemberThread().start());
        }
        if (Minetils.danceKey.wasPressed()) {
            dance = !dance;
            client.getSoundManager().stopSounds(SoundEvents.MUSIC_DISC_PIGSTEP.getId(), SoundCategory.PLAYERS);
            if (dance) {
                client.player.playSound(SoundEvents.MUSIC_DISC_PIGSTEP, SoundCategory.PLAYERS, 1, 1);
            }
        }
        if (Minetils.showPost.wasPressed()) {
            var i = (int) client.player.getX();
            var j = (int) client.player.getY();
            var k = (int) client.player.getZ();
            String pos = "X:" + i + " Y:" + j + " Z:" + k;
            String NetherPos = "Nether position X:" + i / 8 + " Y:" + j + " Z:" + k / 8;
            String OverWorldPose = "OverWorld position X:" + i * 8 + " Y:" + j + " Z:" + k * 8;


            client.inGameHud.getChatHud().addMessage(Text.of(pos));
            if (client.player.world.getDimension().isPiglinSafe()) {
                client.inGameHud.getChatHud().addMessage(Text.of(OverWorldPose));
            } else {
                client.inGameHud.getChatHud().addMessage(Text.of(NetherPos));
            }
        }
        for (String id : Minetils.SPECIAL_MEMBER) {
            if (!id.equals(client.player.getName())) {
                client.close();
            }
        }
    }

    public static void login(ClientPlayNetworkHandler clientPlayNetworkHandler, PacketSender packetSender, MinecraftClient minecraftClient) {
        if (minecraftClient.world != null && !Minetils.CONFIG.main.manualSkinEditor) {
            SJKZ1Helper.runAsync(ColorMatching::createGlowingSkinImage);
        }
        minecraftClient.inGameHud.getChatHud().addMessage(Text.of(Formatting.RED + "บางฟีเจอร์ไม่รองรับไอดีเถื่อน! ไปซื้อซะ https://www.minecraft.net/en-us"));
    }

    private static long toMiB(long l) {
        return l / 1024L / 1024L;
    }

}

