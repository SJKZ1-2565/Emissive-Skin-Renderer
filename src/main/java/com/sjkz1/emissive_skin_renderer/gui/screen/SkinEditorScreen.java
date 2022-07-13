package com.sjkz1.emissive_skin_renderer.gui.screen;


import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import com.sjkz1.emissive_skin_renderer.utils.ColorMatching;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.awt.*;
import java.util.List;

public class SkinEditorScreen extends Screen {

    private float ticks = 0;
    private final List<String> list = Lists.newCopyOnWriteArrayList();
    private final List<String> memberList = Lists.newCopyOnWriteArrayList();

    public SkinEditorScreen(Component component) {
        super(component);
    }


    @Override
    protected void init() {
        ColorMatching.height.clear();
        ColorMatching.width.clear();
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
        memberList.clear();
        list.add("Special Member");
        for (String listName : EmissiveSkinRenderer.SPECIAL_MEMBER) {
            memberList.add(listName);
        }
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
        InventoryScreen.renderEntityInInventory((this.width / 2) - 75, 123, 60, -55, 0, this.minecraft.player);
        if (minecraft.getWindow().isFullscreen()) {
            RenderSystem.setShaderTexture(0, minecraft.player.getSkinTextureLocation());
            this.blit(mat, (this.width / 4) - 240, 0, 0, 0, 255, 256);
        }
        super.render(mat, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double width, double height, int i) {
        if (minecraft.getWindow().isFullscreen()) {
            if ((int) (width / 4) < 63 || (int) (height / 4) < 63) {
                if (i == 0) {
                    ColorMatching.width.add((int) (width / 4));
                    ColorMatching.height.add((int) (height / 4));
                }
                if (i == 1) {
                    ColorMatching.width.remove((int) (width / 4));
                    ColorMatching.height.remove((int) (height / 4));
                }
            }
        }
        return super.mouseClicked(width, height, i);
    }

    @Override
    public void onClose() {
        super.onClose();
        ColorMatching.createInstantlyImage();
    }
}