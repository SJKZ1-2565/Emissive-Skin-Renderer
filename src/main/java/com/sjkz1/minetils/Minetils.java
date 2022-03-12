package com.sjkz1.minetils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
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

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.option.KeyBinding;


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
		try {
			InputStream is = (new URL("https://raw.githubusercontent.com/SJKZ1-2565/modJSON-URL/master/donate.txt")).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			for (String id : rd.lines().toList()) {
				SPECIAL_MEMBER.add(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onInitialize()
	{
		KeyBindInit.init();
		ClientTickEvents.END_CLIENT_TICK.register(ClientInit::tick);
		ClientPlayConnectionEvents.JOIN.register(ClientInit::login);
		SoundInits.init();
		new OpenFolderCommand(ClientCommandManager.DISPATCHER);
		new CovidThailandCommand(ClientCommandManager.DISPATCHER);
	}
}