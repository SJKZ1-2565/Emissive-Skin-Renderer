package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import net.minecraft.client.Minecraft;

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
            String NetherPos = "Nether position X:" + i / 8 + " Y:" + j + " Z:" + k / 8;
            String OverWorldPose = "OverWorld position X:" + i * 8 + " Y:" + j + " Z:" + k * 8;
            if (minecraft.player.level.dimensionType().piglinSafe()) {
                SJKZ1Helper.sendChat(OverWorldPose);
            } else {
                SJKZ1Helper.sendChat(NetherPos);
            }
        }
    }
}

