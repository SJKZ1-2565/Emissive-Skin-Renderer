package com.sjkz1.minetils;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.sjkz1.minetils.command.CovidThailandCommand;
import com.sjkz1.minetils.command.OpenFolderCommand;
import com.sjkz1.minetils.config.ConFigIN;
import com.sjkz1.minetils.utils.ClientInit;
import com.sjkz1.minetils.utils.KeyBindInit;
import com.sjkz1.minetils.utils.SoundInits;
import com.sjkz1.minetils.utils.SpecialMember;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Formatting;


public class Minetils implements ModInitializer
{
	public static KeyBinding danceKey;
	public static KeyBinding showPost;
	public static KeyBinding openModScreen;
	public static int RESPONSE_CODE;

	public static final String MOD_ID = "minetils";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

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
		KeyBindInit.init();
		ClientTickEvents.END_CLIENT_TICK.register(ClientInit::tick);
		SoundInits.init();
		new OpenFolderCommand(ClientCommandManager.DISPATCHER);
		new CovidThailandCommand(ClientCommandManager.DISPATCHER);
	}

	public static void getRightText(List<String> list) {
		list.add(Formatting.GREEN + "Response code from the Covid API: " + RESPONSE_CODE);
	}



}