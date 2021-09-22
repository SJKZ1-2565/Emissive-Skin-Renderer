package com.sjkz1.sjkz1misc;


import com.google.common.collect.Lists;
import com.sjkz1.sjkz1misc.command.EntityDetector;
import com.sjkz1.sjkz1misc.command.OpenFolderCommand;
import com.sjkz1.sjkz1misc.config.ConFigIN;
import com.sjkz1.sjkz1misc.utils.ClientInit;
import com.sjkz1.sjkz1misc.utils.KeyBindInit;
import com.sjkz1.sjkz1misc.utils.SoundInits;
import com.sjkz1.sjkz1misc.utils.SpecialMember;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class SJKZ1Misc implements ModInitializer
{
	public static KeyMapping danceKey;
	public static KeyMapping showPost;

	public static String MOD_ID = "sjkz1misc";
	public static Logger LOGGER = LogManager.getLogger(MOD_ID);


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
		new EntityDetector(ClientCommandManager.DISPATCHER);
	}
}