package com.sjkz1.minetils.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.gui.screen.SpecialMemberScreen;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.realms.dto.PlayerInfo;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Map;
import java.util.UUID;


public class ClientInit {

    public static boolean dance = false;

    public static void tick(MinecraftClient client) {
        if (client.player != null) {
            SJKZ1Helper.runAsync(() ->
                    new DiscordMemberThread().run());
        }
        if (Minetils.danceKey.wasPressed()) {
            dance = !dance;
            client.getSoundManager().stopSounds(SoundInits.DRAGONBALL_ID, SoundCategory.PLAYERS);
            if (dance) {
                client.player.playSound(SoundInits.DRAGONBALL_SOUND_EVENT, SoundCategory.PLAYERS, 1, 1);
            }
        }
        if (Minetils.openModScreen.wasPressed()) {
            client.setScreen(new SpecialMemberScreen(Text.of("")));
        }

        if (Minetils.showPost.wasPressed()) {
            assert client.player != null;
            int i = (int) client.player.getX();
            int j = (int) client.player.getY();
            int k = (int) client.player.getZ();
            String pos = "X:" + i + " Y:" + j + " Z:" + k;
            String NetherPos = "Nether position X:" + i / 8 + " Y:" + j + " Z:" + k / 8;
            String OverWorldPose = "OverWorld position X:" + i * 8 + " Y:" + j + " Z:" + k * 8;


            if (client.player.world.getDimension().isPiglinSafe()) {
                client.player.sendChatMessage(pos);
                client.player.sendChatMessage(OverWorldPose);
            } else {
                client.player.sendChatMessage(pos);
                client.player.sendChatMessage(NetherPos);
            }
        }
    }


    public static void onLoad(ClientPlayNetworkHandler clientPlayNetworkHandler, PacketSender packetSender, MinecraftClient minecraftClient) {
        if (minecraftClient.player != null) {
            ColorMatching.createGlowingSkinImage();
        }
    }
}
