package com.sjkz1.sjkz1misc;


import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.sjkz1.sjkz1misc.command.EntityDetector;
import com.sjkz1.sjkz1misc.command.OpenFolderCommand;
import com.sjkz1.sjkz1misc.utils.CommonEvent;
import com.sjkz1.sjkz1misc.utils.SoundInits;
import com.sjkz1.sjkz1misc.utils.SpecialMember;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.toast.AdvancementToast;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;
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

import javax.security.auth.login.LoginException;


public class SJKZ1Misc implements ModInitializer
{

	private KeyBinding danceKey;
	private KeyBinding showPost;
	public static JDABuilder builder;

	private static SJKZ1Misc instance;
	public static String MOD_ID = "sjkz1misc";
	public static Logger LOGGER = LogManager.getLogger(MOD_ID);
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

		ClientTickEvents.END_CLIENT_TICK.register(this::tick);
		SoundInits.init();
		new OpenFolderCommand(ClientCommandManager.DISPATCHER);
		new EntityDetector(ClientCommandManager.DISPATCHER);

		String token = "ODYwMDc1NTYxODk2NjQwNTEz.YN19yQ._iLl4UYho3fohjaP2ewGYOCHu8k";
		builder = JDABuilder.createDefault(token);
		builder.setActivity(Activity.playing("Minecraft"));
		builder.disableCache(CacheFlag.ROLE_TAGS, CacheFlag.VOICE_STATE);
		registerEvent();
		try {
			builder.build();
		} catch (LoginException e) {
		}
	}

	public static void registerEvent()
	{
		builder.addEventListeners(new CommonEvent());
	}

	public void tick(MinecraftClient client) {
		if (danceKey.wasPressed()) {
			dance = !dance;
			client.getSoundManager().stopSounds(SoundInits.DRAGONBALL_ID, SoundCategory.PLAYERS);
			if(dance) {
				client.player.playSound(SoundInits.DRAGONBALL_SOUND_EVENT, SoundCategory.PLAYERS, 1, 1);
			}
		}

		if (showPost.wasPressed())
		{
			int i = (int) client.player.getX();
			int j = (int) client.player.getY();
			int k = (int) client.player.getZ();
			String pos = "X:" + i + " Y:" + j+ " Z:" + k;
			String Netherpos = "Nether position X:" + i / 8 + " Y:" + j  + " Z:" + k / 8 ;
			String OverWorldPose = "Overworld position X:" + i * 8 + " Y:" + j  + " Z:" + k * 8 ;

			if(client.player.getEntityWorld().getDimension().isPiglinSafe()) {
				client.player.sendChatMessage(pos);
				client.player.sendChatMessage(OverWorldPose);
			}
			else
			{
				client.player.sendChatMessage(pos);
				client.player.sendChatMessage(Netherpos);
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
				client.player.sendSystemMessage(Text.of(pos), UUID.randomUUID());
				client.player.requestRespawn();
				client.getToastManager().add(new SystemToast(SystemToast.Type.TUTORIAL_HINT,Text.of("Dead position"),Text.of(pos)));
			}
		}

	}
}
