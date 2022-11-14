package com.sjkz1.emissive_skin_renderer.gui.screen;


import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SkinEditorScreen extends Screen {
    public SkinEditorScreen() {
        super(Component.translatable("not_final_gui"));
    }

    public static final File GLOW_SKIN_PATH = new File(Minecraft.getInstance().gameDirectory, "glow");


    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new Button(this.width / 2 + 20, 130, 100, 20, Component.translatable("open_folder"), Button -> {
            Util.getPlatform().openFile(GLOW_SKIN_PATH);
        }));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        SkinEditorScreen.drawCenteredString(poseStack, this.font, this.title, this.width / 2, 300, 0xFF5555);
        InventoryScreen.renderEntityInInventory(this.width / 2, 123, 60, 55, 0, this.minecraft.player);
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        super.onClose();
        SkinEditorScreen.MoveToResourceLoc();
    }

    public static void MoveToResourceLoc() {
        try {
            File imageFile = new File(GLOW_SKIN_PATH, "glow.png");
            InputStream in = new FileInputStream(imageFile);
            NativeImage nativeImage = NativeImage.read(in);
            TextureManager textureManager = Minecraft.getInstance().getTextureManager();
            textureManager.register(new ResourceLocation(EmissiveSkinRenderer.MOD_ID, "textures/skin/glow.png"), new DynamicTexture(nativeImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}