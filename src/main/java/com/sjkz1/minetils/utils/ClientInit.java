package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.gui.screen.SpecialMemberScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;


public class ClientInit {

    public static boolean dance = false;

    public static void tick(MinecraftClient client) {
        if(client.player != null)
        {
                SJKZ1Helper.runAsync(() ->
                new DiscordMemberThread().run());
        }
        if (Minetils.danceKey.isPressed()) {
            dance = !dance;
            client.getSoundManager().stopSounds(SoundInits.DRAGONBALL_ID, SoundCategory.PLAYERS);
            if(dance) {
                assert client.player != null;
                client.player.playSound(SoundInits.DRAGONBALL_SOUND_EVENT, SoundCategory.PLAYERS, 1, 1);
            }
        }
    if (Minetils.openModScreen.isPressed()) {
        client.openScreen(new SpecialMemberScreen(Text.of(""),254));
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
    }
}
