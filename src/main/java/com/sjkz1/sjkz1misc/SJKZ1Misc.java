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
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.List;


public class SJKZ1Misc implements ModInitializer
{
	private KeyMapping danceKey;
	private KeyMapping showPost;

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
		danceKey = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.sjkz1misc.start_dance", GLFW.GLFW_KEY_R, "sjkz1misc"));
		showPost = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.sjkz1misc.showPost", GLFW.GLFW_KEY_G, "sjkz1misc"));

		ClientTickEvents.END_CLIENT_TICK.register(this::tick);
		SoundInits.init();
		new OpenFolderCommand(ClientCommandManager.DISPATCHER);
		new EntityDetector(ClientCommandManager.DISPATCHER);
	}


	public void tick(Minecraft client) {
		if (danceKey.isDown()) {
			dance = !dance;
			client.getSoundManager().stop(SoundInits.DRAGONBALL_ID, SoundSource.PLAYERS);
			if(dance) {
				assert client.player != null;
				client.player.playNotifySound(SoundInits.DRAGONBALL_SOUND_EVENT, SoundSource.PLAYERS, 1, 1);
			}
		}

		if (showPost.isDown())
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