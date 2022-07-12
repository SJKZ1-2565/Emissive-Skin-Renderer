package com.sjkz1.emissive_skin_renderer.gui.screen;


import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import com.sjkz1.emissive_skin_renderer.utils.ColorMatching;
import com.sjkz1.emissive_skin_renderer.utils.DiscordMemberNameThread;
import com.sjkz1.emissive_skin_renderer.utils.SJKZ1Helper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SpecialMemberScreen extends Screen {

    private float ticks = 0;
    public static int ONLINE_USER;
    public static List<String> USERNAME = new CopyOnWriteArrayList<>();
    public static ChatFormatting chatFormatting;

    private final List<String> list = Lists.newCopyOnWriteArrayList();
    private final List<String> memberList = Lists.newCopyOnWriteArrayList();
    private boolean err = false;

    public SpecialMemberScreen(Component component) {
        super(component);
    }


    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new Button((this.width / 2) + 20, 130, 98, 20, Component.translatable("create_skin"), Button -> ColorMatching.createGlowingSkinImage()));
        this.addRenderableWidget(new AbstractSliderButton((this.width / 2) - 120, 130, 100, 20, Component.translatable("delete_rate").append(String.valueOf(EmissiveSkinRenderer.CONFIG.main.palletsRate)), 0.0) {
            {
                this.updateMessage();
            }

            @Override
            protected void updateMessage() {
                this.setMessage(Component.translatable("delete_rate").append(String.valueOf(EmissiveSkinRenderer.CONFIG.main.palletsRate)));
            }

            @Override
            protected void applyValue() {
                EmissiveSkinRenderer.CONFIG.main.palletsRate = Mth.floor(Mth.clampedLerp(50.0D, 100.0D, this.value));
            }
        });

        list.clear();
        USERNAME.clear();
        memberList.clear();
        list.add("Special Member");
        try {
            int online = ONLINE_USER;
            list.add("Discord Online member :" + online);
        } catch (Exception e) {
            err = true;
            list.add("Couldn't get Discord Member!");
        }
        for (String listName : EmissiveSkinRenderer.SPECIAL_MEMBER) {
            memberList.add(listName);
        }
        SJKZ1Helper.runAsync(() -> {
            DiscordMemberNameThread discordMemberThread = new DiscordMemberNameThread();
            discordMemberThread.start();
        });
    }

    @Override
    public void render(PoseStack mat, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(mat);
        ticks -= 0.01F * partialTicks;
        Color color = Color.getHSBColor(ticks, 0.9f, 1);
        var height = 0;
        for (String string : list) {
            if (string.equals("Special Member")) {
                GuiComponent.drawString(mat, this.font, string, (this.width / 2) - 45, 160 + height, color.getRGB());
            }
            if (string.contains("Discord Online member :")) {
                GuiComponent.drawString(mat, this.font, string, (this.width / 2) - 25, 35 + height, 0xFFFFFF);
            }
            if (err) {
                GuiComponent.drawString(mat, this.font, string, (this.width / 2) - 60, 35 + height, 16733525);
            }
            height += 15;
        }
        for (String string : memberList) {
            for (String listName : EmissiveSkinRenderer.SPECIAL_MEMBER) {
                if (string.equals(listName)) {
                    GuiComponent.drawString(mat, this.font, string, (this.width / 2) - 25, 160 + height, color.getRGB());
                }
            }
            height += 15;
        }
        for (String name : USERNAME) {
            if (!name.contains("bot") || !name.equals("nSys 2") || !name.equals("MEE6") || !name.equals("Linkie")) {
                GuiComponent.drawString(mat, this.font, name, (this.width / 4) - 230, -90 + height, SpecialMemberScreen.chatFormatting.GOLD.getColor());
                height += 15;
            }
        }

        InventoryScreen.renderEntityInInventory((this.width / 2) - 75, 123, 60, -55, 0, this.minecraft.player);
        super.render(mat, mouseX, mouseY, partialTicks);
    }
}