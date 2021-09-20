package com.sjkz1.sjkz1misc;


import com.google.common.collect.Lists;
import com.sjkz1.sjkz1misc.command.EntityDetector;
import com.sjkz1.sjkz1misc.command.OpenFolderCommand;
import com.sjkz1.sjkz1misc.config.ConFigIN;
import com.sjkz1.sjkz1misc.utils.SoundInits;
import com.sjkz1.sjkz1misc.utils.SpecialMember;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.TutorialToast;
import net.minecraft.client.util.InputUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.UUID;


public class SJKZ1Misc implements ModInitializer
{
	private KeyBinding danceKey;
	private KeyBinding showPost;

	public static String MOD_ID = "sjkz1misc";
	public static Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static boolean dance = false;

	public static final ConFigIN CONFIG = new ConFigIN();

	public static final List<String> SPECIAL_MEMBER = Lists.newCopyOnWriteArrayList();
	static {
		for(SpecialMember values : SpecialMember.VALUES) {
			SPECIAL_MEMBER.add(values.getName());
		}
	}

	@Override
	public void onInitialize()
	{
		danceKey = KeyBindingHelper.registerKeyBinding(new KeyBinding ("key.sjkz1misc.start_dance", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "sjkz1misc"));
		showPost = KeyBindingHelper.registerKeyBinding(new KeyBinding ("key.sjkz1misc.showPost", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "sjkz1misc"));

		ClientTickEvents.END_CLIENT_TICK.register(this::tick);
		SoundInits.init();
		new OpenFolderCommand(ClientCommandManager.DISPATCHER);
		new EntityDetector(ClientCommandManager.DISPATCHER);
	}


	public void tick(MinecraftClient client) {
		if (danceKey.wasPressed()) {
			dance = !dance;
			client.getSoundManager().stopSounds(SoundInits.DRAGONBALL_ID, SoundCategory.PLAYERS);
			if(dance) {
				assert client.player != null;
				client.player.playSound(SoundInits.DRAGONBALL_SOUND_EVENT, SoundCategory.PLAYERS, 1, 1);
			}
		}

		if (showPost.wasPressed())
		{
			assert client.player != null;
			int i = (int) client.player.getX();
			int j = (int) client.player.getY();
			int k = (int) client.player.getZ();
			String pos = "X:" + i + " Y:" + j+ " Z:" + k;
			String NetherPos = "Nether position X:" + i / 8 + " Y:" + j  + " Z:" + k / 8 ;
			String OverWorldPose = "OverWorld position X:" + i * 8 + " Y:" + j  + " Z:" + k * 8 ;

			if(client.player.getEntityWorld().getDimension().isPiglinSafe()) {
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

				if(client.player.getEntityWorld().getDimension().isPiglinSafe()) {
					client.player.sendChatMessage(pos);
					client.player.sendChatMessage(OverWorldPose);
				}
				else
				{
					client.player.sendChatMessage(pos);
					client.player.sendChatMessage(NetherPos);
				}
				client.player.requestRespawn();
				client.getToastManager().add(new SystemToast(SystemToast.Type.NARRATOR_TOGGLE, Text.of("Dead position"),Text.of(pos)));
			}
		}
	}
}