package com.sjkz1.emissive_skin_renderer.mixin;

import com.sjkz1.emissive_skin_renderer.gui.screen.SkinEditorScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends EffectRenderingInventoryScreen<InventoryMenu> implements RecipeUpdateListener {

    @Shadow
    public abstract RecipeBookComponent getRecipeBookComponent();

    @Shadow
    @Final
    private RecipeBookComponent recipeBookComponent;
    @Unique
    private static final String COLOR_KEY = "color";
    @Unique
    private static final String DISPLAY_KEY = "display";

    private static Color COLOR = null;

    public int newX;

    public InventoryScreenMixin(InventoryMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        Random random = new Random();
        COLOR = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        this.newX = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        this.addRenderableWidget(new Button(newX + 140, (this.height / 2) - 24, 20, 20, Component.empty(), (buttonWidget) -> {
            this.minecraft.setScreen(new SkinEditorScreen());
        }));
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(CallbackInfo ci) {
        this.newX = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = Items.LEATHER_CHESTPLATE.getDefaultInstance();
        itemStack.getOrCreateTagElement(DISPLAY_KEY).putInt(COLOR_KEY, COLOR.getRGB());
        itemStack.enchant(Enchantments.UNBREAKING,1);
        itemRenderer.blitOffset = 100;
        itemRenderer.renderGuiItem(itemStack, newX + 142, (this.height / 2) - 22);
        itemRenderer.blitOffset = 0;
    }

}
