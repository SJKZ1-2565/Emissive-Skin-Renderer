package com.sjkz1.sjkz1misc.utils;

import com.sjkz1.sjkz1misc.SJKZ1Misc;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.UUID;

public class CommonEvent extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        if(MinecraftClient.getInstance().player != null && SJKZ1Misc.CONFIG.getConfig().enableDiscordMessage)
        {
            String message = ">"+ Formatting.BLUE + event.getMessage().getAuthor().getName() +" "+Formatting.WHITE+ event.getMessage().getContentRaw();
            String channel = Formatting.AQUA + "(" + event.getMessage().getChannel().getName() + ")";
            MinecraftClient.getInstance().player.sendSystemMessage(Text.of(message + channel), UUID.randomUUID());
        }
    }
}
