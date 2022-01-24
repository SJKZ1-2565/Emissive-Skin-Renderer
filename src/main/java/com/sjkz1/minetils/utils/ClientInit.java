package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.gui.screen.SpecialMemberScreen;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ClientInit {

    public static boolean dance = false;

    public static void tick(MinecraftClient client) {
        if (client.player != null) {
            SJKZ1Helper.runAsync(() ->
                    new DiscordMemberThread().start());
        }
        if (Minetils.danceKey.wasPressed()) {
            dance = !dance;
            client.getSoundManager().stopSounds(SoundInits.DRAGONBALL_ID, SoundCategory.PLAYERS);
            if (dance) {
                client.player.playSound(SoundInits.DRAGONBALL_SOUND_EVENT, SoundCategory.PLAYERS, 1, 1);
            }
        }
        if (Minetils.openModScreen.wasPressed()) {
            if (!Minetils.CONFIG.getConfig().manualSkinEditor) {
                client.setScreen(new SpecialMemberScreen(Text.of("")));
            } else {
                client.inGameHud.getChatHud().addMessage(Text.of(Formatting.RED + "You must have to disable manual skin editor!"));
            }

            if (Minetils.showPost.wasPressed()) {
                assert client.player != null;
                int i = (int) client.player.getX();
                int j = (int) client.player.getY();
                int k = (int) client.player.getZ();
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
        }
    }
    public static void join(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer minecraftServer) {
        if (!Minetils.CONFIG.getConfig().manualSkinEditor && minecraftServer != null) {
            ColorMatching.createGlowingSkinImage();
        }
    }
}

