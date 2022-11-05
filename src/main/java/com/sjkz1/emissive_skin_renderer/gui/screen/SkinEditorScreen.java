package com.sjkz1.emissive_skin_renderer.gui.screen;


import com.mojang.blaze3d.vertex.PoseStack;
import com.sjkz1.emissive_skin_renderer.utils.ColorMatching;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.network.chat.Component;

public class SkinEditorScreen extends Screen {
    public SkinEditorScreen() {
        super(Component.translatable("not_final_gui"));
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new Button(this.width / 2 + 20, 130, 100, 20, Component.translatable("create_skin_less_than"), Button -> {
            if (this.minecraft.player != null) {
                var tets = this.minecraft.player.level.getEntitiesOfClass(RemotePlayer.class, this.minecraft.player.getBoundingBox().inflate(1000));
                tets.forEach(abstractClientPlayer -> {
                    ColorMatching.createGlowingSkinImageLessThan(abstractClientPlayer.getStringUUID());
                });
            }
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 120, 130, 100, 20, Component.translatable("create_skin_greater_than"), Button -> {
            if (this.minecraft.player != null) {
                var tets = this.minecraft.player.level.getEntitiesOfClass(RemotePlayer.class, this.minecraft.player.getBoundingBox().inflate(1000));
                tets.forEach(abstractClientPlayer -> {
                    ColorMatching.createGlowingSkinImageGreaterThan(abstractClientPlayer.getStringUUID());
                    ColorMatching.downloadSkinNew(abstractClientPlayer);
                });
            }
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        SkinEditorScreen.drawCenteredString(poseStack, this.font, this.title, this.width / 2, 300, 0xFF5555);
        GuiComponent.drawString(poseStack, this.font, Component.literal("Tips").withStyle(style -> style.withBold(true).withUnderlined(true)), this.width / 2 - 225, 150, 0xFFFF55);
        GuiComponent.drawString(poseStack, this.font, Component.translatable("pallets_desc_l1"), this.width / 2 - 225, 170, 0xFFFF55);
        GuiComponent.drawString(poseStack, this.font, Component.translatable("pallets_desc_l2"), this.width / 2 - 225, 185, 0xFFFF55);
        GuiComponent.drawString(poseStack, this.font, Component.translatable("pallets_desc_l3"), this.width / 2 - 225, 200, 0xFFFF55);
        GuiComponent.drawString(poseStack, this.font, Component.translatable("pallets_desc_l4"), this.width / 2 - 225, 215, 0xFFFF55);
        GuiComponent.drawString(poseStack, this.font, Component.translatable("pallets_desc_l5"), this.width / 2 - 225, 230, 0xFFFF55);
        GuiComponent.drawString(poseStack, this.font, Component.translatable("pallets_desc_l6"), this.width / 2 - 225, 245, 0xFFFF55);
        assert this.minecraft != null;
        assert this.minecraft.player != null;
        InventoryScreen.renderEntityInInventory(this.width / 2, 123, 60, 55, 0, this.minecraft.player);
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }
}