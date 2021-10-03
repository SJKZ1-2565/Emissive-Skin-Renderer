package com.sjkz1.minetils.screen;


import com.google.common.base.Function;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sjkz1.minetils.mixin.PlayerEntityMixin;
import com.sjkz1.minetils.utils.IdentifierUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameModeSelectionScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

public class SpecialMemberScreen extends Screen {

    private int leftPos;
    private int topPos;
    private float playerXRot = 0;

    private double guiScale;

    public SpecialMemberScreen(Text text) {
        super(text);
    }


    @Override
    protected void init()
    {

        guiScale = 2f / client.options.guiScale;
        super.init();
        int j = this.height / 4 + 48;

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, j + 72 + 12, 98, 20, Text.of("Switch"), (buttonWidget) -> {
            IdentifierUtils.IDENTIFIER_ORDINAL++;
            IdentifierUtils.IDENTIFIER_ORDINAL %= 6;
        }));
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 20, j + 72 + 12, 98, 20, Text.of("Mode"), (buttonWidget) -> {
            IdentifierUtils.SPECIAL_IDENTIFIER = !IdentifierUtils.SPECIAL_IDENTIFIER;;
        }));
    }

    @Override
    public void render(MatrixStack mat, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(mat);
        drawCenteredText(mat, this.textRenderer,Text.of("Special Member Wardrobe"), this.width / 2, 15, Formatting.GOLD.getColorValue());
        drawCenteredText(mat, this.textRenderer, Text.of(Formatting.BOLD + "Feature for this screen, Not Soon..."), this.width / 2 , 25, Formatting.RED.getColorValue());
        super.render(mat, mouseX, mouseY, partialTicks);
        int k = leftPos;
        int l = topPos;
        playerXRot -= 0.15 * partialTicks;
        if(playerXRot <= -179.85) {
            playerXRot = 180;
        }
        renderEntityInInventory(this.width / 2 - 100,145,  100,playerXRot,0,client.player);
    }

    public static void renderEntityInInventory(int x, int y, int scale, float xRot, float yRot, LivingEntity livingEntity) {
        float xRotClamped = MathHelper.clamp(xRot, -180, 180);
        float yRotClamped = MathHelper.clamp(yRot, -180, 180);
        MatrixStack poseStack = RenderSystem.getModelViewStack();
        poseStack.push();
        // translate far from screen
        poseStack.translate(x, y, 1050.0D);
        poseStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack poseStack2 = new MatrixStack();
        // translate far from screen
        poseStack2.translate(0.0D, 0.0D, 1000.0D);
        poseStack2.scale((float)scale, (float)scale, (float)scale);
        // flip to right side
        Quaternion zRotationQuaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion yRotationQuaternion = Vec3f.POSITIVE_X.getDegreesQuaternion(yRotClamped * 20.0F);
        zRotationQuaternion.hamiltonProduct(yRotationQuaternion);
        poseStack2.multiply(zRotationQuaternion);
        // set entity rotations
        float m = livingEntity.bodyYaw;
        float n = livingEntity.getYaw();
        float o = livingEntity.getPitch();
        float p = livingEntity.headYaw;
        float q = livingEntity.headYaw;
        livingEntity.bodyYaw = 180.0F + xRotClamped * 20.0F;
        livingEntity.setYaw(180.0F + xRotClamped * 20.0f);
        livingEntity.setPitch(-yRotClamped * 20.0F);
        livingEntity.headYaw = livingEntity.getYaw();
        livingEntity.headYaw = livingEntity.getYaw();
        // setup for rendering
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        yRotationQuaternion.conjugate();
        entityRenderDispatcher.setRotation(yRotationQuaternion);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack2, immediate, 15728880));
        immediate.draw();
        // undo changes
        entityRenderDispatcher.setRenderShadows(true);
        livingEntity.bodyYaw = m;
        livingEntity.setYaw(n);
        livingEntity.setPitch(o);
        livingEntity.headYaw = p;
        livingEntity.prevHeadYaw = q;
        poseStack.pop();
        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }
}
