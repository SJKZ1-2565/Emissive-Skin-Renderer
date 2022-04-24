package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.gui.screen.SpecialMemberScreen;
import com.sjkz1.minetils.utils.SJKZ1Helper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler>
        implements RecipeBookProvider {

    @Unique
    private static String COLOR_KEY = "color";
    @Unique
    private static String DISPLAY_KEY = "display";

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    public int newX;

    @Inject(method = "init()V", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        this.newX = this.getRecipeBookWidget().findLeftEdge(this.width, this.backgroundWidth);
        this.addDrawableChild(new ButtonWidget(newX + 140, (this.height / 2) - 24, 21, 21, Text.of(""), (buttonWidget) -> {
            if (!Minetils.CONFIG.main.manualSkinEditor) {
                client.setScreen(new SpecialMemberScreen(Text.of("")));
            } else {
                SJKZ1Helper.sendChat(Formatting.RED + "You must have disable manual skin editor!");
            }
        }));
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(CallbackInfo ci) {
        this.newX = this.getRecipeBookWidget().findLeftEdge(this.width, this.backgroundWidth);
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack itemStack = Items.LEATHER_CHESTPLATE.getDefaultStack();
        Color color = Color.getHSBColor(client.getTickDelta(), 0.9f, 1);
        itemStack.getOrCreateSubNbt(DISPLAY_KEY).putInt(COLOR_KEY, color.getRGB());
        itemRenderer.zOffset = 300;
        itemRenderer.renderInGui(itemStack, newX + 142, (this.height / 2) - 22);
        itemRenderer.zOffset = 0;
    }

}
