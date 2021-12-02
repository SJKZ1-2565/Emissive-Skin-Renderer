package com.sjkz1.minetils;


import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.sjkz1.minetils.command.EntityDetector;
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
import net.minecraft.client.option.KeyBinding;


public class Minetils implements ModInitializer
{
	public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
	public static KeyBinding danceKey;
	public static KeyBinding showPost;
	public static KeyBinding openModScreen;

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
		ClientPlayConnectionEvents.JOIN.register(ClientInit::onLoad);

		SoundInits.init();
		new OpenFolderCommand(ClientCommandManager.DISPATCHER);
		new EntityDetector(ClientCommandManager.DISPATCHER);
	}


}