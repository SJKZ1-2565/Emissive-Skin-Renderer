package com.sjkz1.minetils.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Donate {

     public static int getDonateAmount() throws JsonSyntaxException, IOException
    {
        URL url = new URL("https://raw.githubusercontent.com/SJKZ1-2565/modJSON-URL/master/donate.json");
        JsonArray array = new JsonParser().parse(IOUtils.toString(url.openConnection().getInputStream(), StandardCharsets.UTF_8)).getAsJsonArray();;
        int DonateAmount = array.get(array.size() - 1).getAsJsonObject().get(MinecraftClient.getInstance().player.getName().getString().toLowerCase()).getAsInt();
        return DonateAmount;
    }


    public static int getDiscordMemberAmount() throws JsonSyntaxException, IOException
    {
        URL url = new URL("https://discordapp.com/api/guilds/675288690658115588/widget.json");
        JsonObject array = (JsonObject) new JsonParser().parse(IOUtils.toString(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
        int user_cout = array.get("presence_count").getAsInt();
        return  user_cout;
    }

}
