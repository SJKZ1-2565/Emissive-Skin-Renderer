package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundSource;

public class ClientInit {

    public static boolean dance = false;

    public static void tick(Minecraft client) {
        if (Minetils.danceKey.isDown()) {
            dance = !dance;
            client.getSoundManager().stop(SoundInits.DRAGONBALL_ID, SoundSource.PLAYERS);
            if(dance) {
                assert client.player != null;
                client.player.playNotifySound(SoundInits.DRAGONBALL_SOUND_EVENT, SoundSource.PLAYERS, 1, 1);
            }
        }

        if (Minetils.showPost.isDown())
        {
            assert client.player != null;
            int i = (int) client.player.getX();
            int j = (int) client.player.getY();
            int k = (int) client.player.getZ();
            String pos = "X:" + i + " Y:" + j+ " Z:" + k;
            String NetherPos = "Nether position X:" + i / 8 + " Y:" + j  + " Z:" + k / 8 ;
            String OverWorldPose = "OverWorld position X:" + i * 8 + " Y:" + j  + " Z:" + k * 8 ;

            if(client.player.level.dimensionType().piglinSafe()) {
                client.player.chat(pos);
                client.player.chat(OverWorldPose);
            }
            else
            {
                client.player.chat(pos);
                client.player.chat(NetherPos);
            }
        }
        if(client != null && client.player != null && client.level != null)
        {
            if(client.player.isDeadOrDying() && client.level.isClientSide)
            {
                int i = (int) client.player.getX();
                int j = (int) client.player.getY();
                int k = (int) client.player.getZ();
                String pos = "X:" + i + " Y:" + j+ " Z:" + k;
                String NetherPos = "Nether position X:" + i / 8 + " Y:" + j  + " Z:" + k / 8 ;
                String OverWorldPose = "OverWorld position X:" + i * 8 + " Y:" + j  + " Z:" + k * 8 ;

                if(client.player.level.dimensionType().piglinSafe()) {
                    client.player.chat(pos);
                    client.player.chat(OverWorldPose);
                }
                else
                {
                    client.player.chat(pos);
                    client.player.chat(NetherPos);
                }
                client.player.respawn();
                client.getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.NARRATOR_TOGGLE,new TextComponent("Dead position"),new TextComponent(pos)));
            }
        }
    }
}
