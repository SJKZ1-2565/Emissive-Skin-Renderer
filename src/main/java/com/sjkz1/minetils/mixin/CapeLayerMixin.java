package com.sjkz1.minetils.mixin;

import com.sjkz1.minetils.Minetils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;
import java.util.Objects;

@Mixin(CapeLayer.class)
public class CapeLayerMixin {


    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entitySolid(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    public RenderType render(ResourceLocation identifier) {
        RenderType renderLayer = null;
        if (Minetils.CONFIG.main.SpecialCape) {
            Iterator var3 = Minetils.SPECIAL_MEMBER.iterator();
            if (var3.hasNext()) {
                String name = (String) var3.next();
                if (Objects.requireNonNull(Minecraft.getInstance().player).getName().getString().contains(name)) {
                    renderLayer = RenderType.armorCutoutNoCull(identifier);
                } else {
                    renderLayer = RenderType.entitySolid(identifier);
                }
            }
        } else {
            renderLayer = RenderType.entitySolid(identifier);
        }
        return renderLayer;
    }
}
