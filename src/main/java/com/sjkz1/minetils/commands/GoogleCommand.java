package com.sjkz1.minetils.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.net.URI;
import java.net.URISyntaxException;

public class GoogleCommand {
    public GoogleCommand(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("google").then(CommandManager.argument("message", MessageArgumentType.message()).executes(context -> {
            Text text = MessageArgumentType.getMessage(context, "message");
            String url = "google.com/search?q=" + text.getString().replace(" ", "+");
            System.out.println(url);
            try {
                Util.getOperatingSystem().open(new URI(url));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return 1;

        })));
    }
}
