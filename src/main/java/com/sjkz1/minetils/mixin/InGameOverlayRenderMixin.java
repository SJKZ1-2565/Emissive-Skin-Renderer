package com.sjkz1.minetils.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;


@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRenderMixin {


	/**
	 * @author
	 */
	@Overwrite
	public static void renderFireOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack)
	{


			if(minecraftClient.player.getBlockStateAtPos().isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS) && minecraftClient.player.getUuid().equals(minecraftClient.player.getUuid()))
			{
				BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
				RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
				RenderSystem.depthFunc(519);
				RenderSystem.depthMask(false);
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.enableTexture();
				Sprite sprite =  new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/soul_fire_1")).getSprite();
				RenderSystem.setShaderTexture(0, sprite.getAtlas().getId());
				float f = sprite.getMinU();
				float g = sprite.getMaxU();
				float h = (f + g) / 2.0f;
				float i = sprite.getMinV();
				float j = sprite.getMaxV();
				float k = (i + j) / 2.0f;
				float l = sprite.getAnimationFrameDelta();
				float m = MathHelper.lerp(l, f, h);
				float n = MathHelper.lerp(l, g, h);
				float o = MathHelper.lerp(l, i, k);
				float p = MathHelper.lerp(l, j, k);
				float q = 1.0f;
				for (int r = 0; r < 2; ++r) {
					matrixStack.push();
					float s = -0.5f;
					float t = 0.5f;
					float u = -0.5f;
					float v = 0.5f;
					float w = -0.5f;
					matrixStack.translate((-(r * 2 - 1)) * 0.24f, -0.3f, 0.0);
					matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((r * 2 - 1) * 10.0f));
					Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
					bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
					bufferBuilder.vertex(matrix4f, -0.5f, -0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(n, p).next();
					bufferBuilder.vertex(matrix4f, 0.5f, -0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(m, p).next();
					bufferBuilder.vertex(matrix4f, 0.5f, 0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(m, o).next();
					bufferBuilder.vertex(matrix4f, -0.5f, 0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(n, o).next();
					bufferBuilder.end();
					BufferRenderer.draw(bufferBuilder);
					matrixStack.pop();
				}
				RenderSystem.disableBlend();
				RenderSystem.depthMask(true);
				RenderSystem.depthFunc(515);
			}
			else {
				BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
				RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
				RenderSystem.depthFunc(519);
				RenderSystem.depthMask(false);
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.enableTexture();
				Sprite sprite = ModelLoader.FIRE_1.getSprite();
				RenderSystem.setShaderTexture(0, sprite.getAtlas().getId());
				float f = sprite.getMinU();
				float g = sprite.getMaxU();
				float h = (f + g) / 2.0f;
				float i = sprite.getMinV();
				float j = sprite.getMaxV();
				float k = (i + j) / 2.0f;
				float l = sprite.getAnimationFrameDelta();
				float m = MathHelper.lerp(l, f, h);
				float n = MathHelper.lerp(l, g, h);
				float o = MathHelper.lerp(l, i, k);
				float p = MathHelper.lerp(l, j, k);
				float q = 1.0f;
				for (int r = 0; r < 2; ++r) {
					matrixStack.push();
					float s = -0.5f;
					float t = 0.5f;
					float u = -0.5f;
					float v = 0.5f;
					float w = -0.5f;
					matrixStack.translate((-(r * 2 - 1)) * 0.24f, -0.3f, 0.0);
					matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((r * 2 - 1) * 10.0f));
					Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
					bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
					bufferBuilder.vertex(matrix4f, -0.5f, -0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(n, p).next();
					bufferBuilder.vertex(matrix4f, 0.5f, -0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(m, p).next();
					bufferBuilder.vertex(matrix4f, 0.5f, 0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(m, o).next();
					bufferBuilder.vertex(matrix4f, -0.5f, 0.5f, -0.5f).color(1.0f, 1.0f, 1.0f, 0.9f).texture(n, o).next();
					bufferBuilder.end();
					BufferRenderer.draw(bufferBuilder);
					matrixStack.pop();
				}
				RenderSystem.disableBlend();
				RenderSystem.depthMask(true);
				RenderSystem.depthFunc(515);
			}
	}
}
