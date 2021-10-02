package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.screen.SpecialMemberScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;


public class ClientInit {

    public static boolean dance = false;

    public static void tick(MinecraftClient client) {
        if (Minetils.danceKey.isPressed()) {
            dance = !dance;
            client.getSoundManager().stopSounds(SoundInits.DRAGONBALL_ID, SoundCategory.PLAYERS);
            if(dance) {
                assert client.player != null;
                client.player.playSound(SoundInits.DRAGONBALL_SOUND_EVENT, SoundCategory.PLAYERS, 1, 1);
            }
        }
    if (Minetils.openModScreen.isPressed()) {
        client.openScreen(new SpecialMemberScreen(Text.of("Special Screen")));
        }

        if (Minetils.showPost.isPressed())
        {
            assert client.player != null;
            int i = (int) client.player.getX();
            int j = (int) client.player.getY();
            int k = (int) client.player.getZ();
            String pos = "X:" + i + " Y:" + j+ " Z:" + k;
            String NetherPos = "Nether position X:" + i / 8 + " Y:" + j  + " Z:" + k / 8 ;
            String OverWorldPose = "OverWorld position X:" + i * 8 + " Y:" + j  + " Z:" + k * 8 ;

            if(client.player.world.getDimension().isPiglinSafe()) {
                client.player.sendChatMessage(pos);
                client.player.sendChatMessage(OverWorldPose);
            }
            else
            {
                client.player.sendChatMessage(pos);
                client.player.sendChatMessage(NetherPos);
            }
        }
        if(client != null && client.player != null && client.world != null)
        {
            if(client.player.isDead() && client.world.isClient)
            {
                int i = (int) client.player.getX();
                int j = (int) client.player.getY();
                int k = (int) client.player.getZ();
                String pos = "X:" + i + " Y:" + j+ " Z:" + k;
                String NetherPos = "Nether position X:" + i / 8 + " Y:" + j  + " Z:" + k / 8 ;
                String OverWorldPose = "OverWorld position X:" + i * 8 + " Y:" + j  + " Z:" + k * 8 ;

                if(client.player.world.getDimension().isPiglinSafe()) {
                    client.player.sendChatMessage(pos);
                    client.player.sendChatMessage(OverWorldPose);
                }
                else
                {
                    client.player.sendChatMessage(pos);
                    client.player.sendChatMessage(NetherPos);
                }
                client.player.requestRespawn();
                client.getToastManager().add(new SystemToast(SystemToast.Type.NARRATOR_TOGGLE,Text.of("Dead position"),Text.of(pos)));
            }
        }
    }
}
