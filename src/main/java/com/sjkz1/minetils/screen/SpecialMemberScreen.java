package com.sjkz1.minetils.screen;


import com.google.common.base.Function;
import com.sjkz1.minetils.mixin.PlayerEntityMixin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SpecialMemberScreen extends Screen {

    private int leftPos;
    private int topPos;

    public SpecialMemberScreen(Text text) {
        super(text);
    }


    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(MatrixStack mat, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(mat);
        drawCenteredText(mat, this.textRenderer,Text.of("Special Member Wardrobe"), this.width / 2, 15, Formatting.GOLD.getColorValue());
        drawCenteredText(mat, this.textRenderer, Text.of(Formatting.BOLD + "Feature for this screen, Not Soon..."), this.width / 2 , 25, Formatting.RED.getColorValue());
        super.render(mat, mouseX, mouseY, partialTicks);
        int k = leftPos;
        int l = topPos;
        InventoryScreen.drawEntity(k + 51, l + 75, 30, (float)(k + 51) - mouseX, (float)(l + 75 - 50) - mouseY, this.client.player);
    }
}
