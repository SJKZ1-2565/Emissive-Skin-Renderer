package com.sjkz1.emissive_skin_renderer.mixin;

import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>>
        extends EntityRenderer<T>
        implements RenderLayerParent<T, M> {
    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getCameraEntity()Lnet/minecraft/world/entity/Entity;"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void modifyShouldShowName(T livingEntity, CallbackInfoReturnable<Boolean> cir, double d, float f, Minecraft minecraft, LocalPlayer localPlayer, boolean bl) {
        if (EmissiveSkinRenderer.CONFIG.main.renderPlayerNameInThirdPerson)
            cir.setReturnValue(Minecraft.renderNames() && bl && !((Entity) livingEntity).isVehicle());
    }
}
