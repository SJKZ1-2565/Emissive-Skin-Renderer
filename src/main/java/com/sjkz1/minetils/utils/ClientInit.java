package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

public class ClientInit {
    public static void tick(Minecraft minecraft) {
        if (minecraft.player != null) {
            SJKZ1Helper.runAsync(() ->
                    new DiscordMemberThread().start());
        }
        if (Minetils.showPost.isDown()) {
            var i = (int) minecraft.player.getX();
            var j = (int) minecraft.player.getY();
            var k = (int) minecraft.player.getZ();
            String pos = "X:" + i + " Y:" + j + " Z:" + k;
            String NetherPos = "Nether position X:" + i / 8 + " Y:" + j + " Z:" + k / 8;
            String OverWorldPose = "OverWorld position X:" + i * 8 + " Y:" + j + " Z:" + k * 8;

            SJKZ1Helper.sendChat(pos);
            if (minecraft.player.level.dimensionType().piglinSafe()) {
                SJKZ1Helper.sendChat(OverWorldPose);
            } else {
                SJKZ1Helper.sendChat(NetherPos);
            }
        }
    }
}

