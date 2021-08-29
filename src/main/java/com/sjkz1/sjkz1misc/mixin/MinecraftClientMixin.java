package com.sjkz1.sjkz1misc.mixin;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sjkz1.sjkz1misc.utils.SpecialMember;

import net.minecraft.client.MinecraftClient;


@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

	@Shadow @Final private static Logger LOGGER;

	@Inject(method = "run",at = @At(value = "INVOKE",target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;",shift = At.Shift.BEFORE))
	public void run(CallbackInfo info)
	{
		for(SpecialMember values : SpecialMember.VALUES)
		{
			try {


				if(!getName(values.getUuid()).equals(values.getName()))
				{
						LOGGER.warn("Found not matched, Replace {} with {} instead",values.getName(),getName(values.getUuid()));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getName(String uuid) throws JsonSyntaxException, IOException
	{
		URL url = new URL("https://api.mojang.com/user/profiles/" + uuid.replace("-", "") + "/names");
		JsonArray array = new JsonParser().parse(IOUtils.toString(url.openConnection().getInputStream(), StandardCharsets.UTF_8)).getAsJsonArray();
		String name = array.get(array.size() - 1).getAsJsonObject().get("name").getAsString();
		return name;
	}
}
