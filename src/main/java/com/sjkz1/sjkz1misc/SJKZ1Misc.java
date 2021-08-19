package com.sjkz1.sjkz1misc;


import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.sjkz1.sjkz1misc.utils.SoundInits;
import com.sjkz1.sjkz1misc.utils.SpecialMember;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.toast.AdvancementToast;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import com.sjkz1.sjkz1misc.config.ConFigIN;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;


public class SJKZ1Misc implements ModInitializer
{

	private KeyBinding danceKey;
	private KeyBinding showPost;
	private KeyBinding openFolder;

	private static SJKZ1Misc instance;
	public static String MOD_ID = "sjkz1misc";
	public static Logger LOGGER = LogManager.getLogger(MOD_ID);
	private static final Text ENTER_NAME_TEXT = new TranslatableText("selectWorld.enterName");
	public static boolean dance = false;

	public static final ConFigIN CONFIG = new ConFigIN();

	public static final List<String> SPECIAL_MEMBER = Lists.newCopyOnWriteArrayList();
	{
		for(SpecialMember values : SpecialMember.VALUES) {
			SPECIAL_MEMBER.add(values.getName());
		}
	}

	@Override
	public void onInitialize()
	{
		danceKey = KeyBindingHelper.registerKeyBinding(new KeyBinding ("key.sjkz1misc.start_dance", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "sjkz1misc"));
		showPost = KeyBindingHelper.registerKeyBinding(new KeyBinding ("key.sjkz1misc.showPost", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "sjkz1misc"));
		openFolder = KeyBindingHelper.registerKeyBinding(new KeyBinding ("key.sjkz1misc.openFolder", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_O, "sjkz1misc"));

		ClientTickEvents.END_CLIENT_TICK.register(this::tick);
		SoundInits.init();

	}


	public void tick(MinecraftClient client) {
		if (danceKey.wasPressed()) {
			dance = !dance;
			client.getSoundManager().stopSounds(SoundInits.DRAGONBALL_ID, SoundCategory.PLAYERS);
			if(dance) {
				client.player.playSound(SoundInits.DRAGONBALL_SOUND_EVENT, SoundCategory.PLAYERS, 1, 1);
			}
		}
		if (openFolder.wasPressed() && Screen.hasControlDown()) {
			Util.getOperatingSystem().open(new File(FabricLoader.getInstance().getGameDirectory(), "saves"));
			LOGGER.info(client.player.getEntityWorld().getServer().getName());
		}

		if (showPost.wasPressed())
		{
			int i = (int) client.player.getX();
			int j = (int) client.player.getY();
			int k = (int) client.player.getZ();
			String pos = "X:" + i + " Y:" + j+ " Z:" + k;

			client.player.sendChatMessage(pos);
		}
		if(client != null && client.player != null && client.world != null)
		{
			if(client.player.isDead() && client.world.isClient)
			{
				int i = (int) client.player.getX();
				int j = (int) client.player.getY();
				int k = (int) client.player.getZ();
				String pos = "X:" + i + " Y:" + j+ " Z:" + k;
				client.player.sendSystemMessage(Text.of(pos), UUID.randomUUID());
				client.player.requestRespawn();
				client.getToastManager().add(new SystemToast(SystemToast.Type.TUTORIAL_HINT,Text.of("Dead position"),Text.of(pos)));
			}
		}

	}


	public static SJKZ1Misc getInstance() {
		return instance;
	}

	public static void openFolder(File folder) {
	}
}
