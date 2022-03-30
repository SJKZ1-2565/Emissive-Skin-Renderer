package com.sjkz1.minetils.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.minetils.Minetils;
import com.sjkz1.minetils.gui.screen.SpecialMemberScreen;

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

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler>
implements RecipeBookProvider {

	public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}
	public int newX;

	@Inject(method = "init()V",at = @At("TAIL"))
	public void init(CallbackInfo ci)
	{
		ItemStack itemStack = new ItemStack(Items.LEATHER_CHESTPLATE);
		ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
		itemRenderer.zOffset = 100.0f;
		itemRenderer.renderInGuiWithOverrides(itemStack,(this.width / 2) + 20, (this.height / 2)+30);
		itemRenderer.zOffset = 0.0f;
		this.newX = this.getRecipeBookWidget().findLeftEdge(this.width, this.backgroundWidth);
		this.addDrawableChild(new ButtonWidget(newX+140, (this.height / 2)-24, 20, 20, Text.of(""), (buttonWidget) -> {
			if (!Minetils.CONFIG.getConfig().manualSkinEditor)
			{
				client.setScreen(new SpecialMemberScreen(Text.of("")));
			}
			else
			{
				client.inGameHud.getChatHud().addMessage(Text.of(Formatting.RED + "You must have to disable manual skin editor!"));
			}
		}));
	}
}
