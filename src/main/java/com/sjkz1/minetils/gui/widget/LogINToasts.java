package com.sjkz1.minetils.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sjkz1.minetils.Minetils;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;


public class LogINToasts implements Toast {

    public LogINToasts()
    {

    }
    @Override
    public Visibility draw(MatrixStack matrixStack, ToastManager toastManager, long l) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        toastManager.drawTexture(matrixStack, 0, 0, 0, 0, this.getWidth(), this.getHeight());
        toastManager.getClient().getItemRenderer().renderInGui(new ItemStack(Items.COOKIE), 8, 8);
        toastManager.getClient().textRenderer.draw(matrixStack,"Response code", 35f,5f,0xfcba03);
        toastManager.getClient().textRenderer.draw(matrixStack, String.valueOf(Minetils.RESPONSE_CODE), 35f,15f,0xfcba03);
            return l >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }


}
