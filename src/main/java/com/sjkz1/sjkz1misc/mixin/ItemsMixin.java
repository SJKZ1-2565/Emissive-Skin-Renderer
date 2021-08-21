package com.sjkz1.sjkz1misc.mixin;

import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.windows.POINT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Items.class)
public class ItemsMixin {
//    @Inject(method = "register(Lnet/minecraft/util/Identifier;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", at = @At(value = "INVOKE",target = "Lnet/minecraft/util/registry/Registry;register(Lnet/minecraft/util/registry/Registry;Lnet/minecraft/util/Identifier;Ljava/lang/Object;)Ljava/lang/Object;"), cancellable = true)
//    private static void da(Identifier identifier, Item item, CallbackInfoReturnable<Item> cir) {
//        if(item instanceof ToolItem) {
//            cir.setReturnValue(Registry.register(Registry.ITEM, identifier, new Item((new Item.Settings()).group(item.getGroup()).food(FoodComponents.APPLE))));
//        }
//    }
}
