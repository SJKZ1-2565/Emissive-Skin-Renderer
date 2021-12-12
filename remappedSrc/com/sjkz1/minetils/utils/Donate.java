package com.sjkz1.minetils.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.minecraft.client.MinecraftClient;

public class Donate {

     public static int getDonateAmount() throws JsonSyntaxException, IOException
    {
        URL url = new URL("https://raw.githubusercontent.com/SJKZ1-2565/modJSON-URL/master/donate.json");
        JsonArray array = JsonParser.parseString(IOUtils.toString(url.openConnection().getInputStream(), StandardCharsets.UTF_8)).getAsJsonArray();
        return array.get(array.size() - 1).getAsJsonObject().get(Objects.requireNonNull(MinecraftClient.getInstance().player).getName().getString().toLowerCase()).getAsInt();
    }


    public static int getDiscordMemberAmount() throws JsonSyntaxException, IOException
    {
        URL url = new URL("https://discordapp.com/api/guilds/675288690658115588/widget.json");
        JsonObject array = (JsonObject) JsonParser.parseString(IOUtils.toString(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
        return array.get("presence_count").getAsInt();
    }

}
