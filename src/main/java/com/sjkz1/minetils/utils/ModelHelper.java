package com.sjkz1.minetils.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;

public class ModelHelper {

    public static void playerDance(ModelPart head,ModelPart rightArm,ModelPart leftArm,ModelPart body,ModelPart hat,ModelPart rightSleeve,ModelPart leftSleeve,ModelPart jacket,int tick)
    {
        if(ClientInit.dance) {
            float delta = (tick + MinecraftClient.getInstance().getTickDelta()) / 60;
            head.pivotX = MathHelper.sin(delta * 10.0F);
            head.pivotY = MathHelper.sin(delta * 40.0F) + 0.4F;
            rightArm.roll = 0.017453292F * (70.0F + MathHelper.cos(delta * 40.0F) * 10.0F);
            leftArm.roll = rightArm.roll * -1.0F;
            rightArm.pivotY = MathHelper.sin(delta * 40.0F) * 0.5F + 1.5F;
            leftArm.pivotY = MathHelper.sin(delta * 40.0F) * 0.5F + 1.5F;
            body.pivotY = MathHelper.sin(delta * 40.0F) * 0.35F;
            hat.copyTransform(head);
            rightSleeve.copyTransform(rightArm);
            leftSleeve.copyTransform(leftArm);
            jacket.copyTransform(body);
        }
    }
}
