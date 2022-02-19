package com.sjkz1.minetils.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

@Mixin(EntityRenderDispatcher.class)
public class EntityRendererDispatcherMixin {

	@Shadow
	public Camera camera;

	@Overwrite
	private void renderFire(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, Entity entity) {
		if(entity.getBlockStateAtPos().isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS))
		{
			Sprite sprite = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/soul_fire_0")).getSprite();
			Sprite sprite2 =new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/soul_fire_1")).getSprite();
			matrixStack.push();
			float f = entity.getWidth() * 1.4f;
			matrixStack.scale(f, f, f);
			float g = 0.5f;
			float h = 0.0f;
			float i = entity.getHeight() / f;
			float j = 0.0f;
			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-this.camera.getYaw()));
			matrixStack.translate(0.0, 0.0, -0.3f + ((int)i) * 0.02f);
			float k = 0.0f;
			int l = 0;
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(TexturedRenderLayers.getEntityCutout());
			MatrixStack.Entry entry = matrixStack.peek();
			while (i > 0.0f) {
				Sprite sprite3 = l % 2 == 0 ? sprite : sprite2;
				float m = sprite3.getMinU();
				float n = sprite3.getMinV();
				float o = sprite3.getMaxU();
				float p = sprite3.getMaxV();
				if (l / 2 % 2 == 0) {
					float q = o;
					o = m;
					m = q;
				}
				EntityRendererDispatcherMixin.drawFireVertex(entry, vertexConsumer, g - 0.0f, 0.0f - j, k, o, p);
				EntityRendererDispatcherMixin.drawFireVertex(entry, vertexConsumer, -g - 0.0f, 0.0f - j, k, m, p);
				EntityRendererDispatcherMixin.drawFireVertex(entry, vertexConsumer, -g - 0.0f, 1.4f - j, k, m, n);
				EntityRendererDispatcherMixin.drawFireVertex(entry, vertexConsumer, g - 0.0f, 1.4f - j, k, o, n);
				i -= 0.45f;
				j -= 0.45f;
				g *= 0.9f;
				k += 0.03f;
				++l;
			}
			matrixStack.pop();
		}
		else
		{
	        Sprite sprite = ModelLoader.FIRE_0.getSprite();
	        Sprite sprite2 = ModelLoader.FIRE_1.getSprite();
	        matrixStack.push();
	        float f = entity.getWidth() * 1.4f;
	        matrixStack.scale(f, f, f);
	        float g = 0.5f;
	        float h = 0.0f;
	        float i = entity.getHeight() / f;
	        float j = 0.0f;
	        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-this.camera.getYaw()));
	        matrixStack.translate(0.0, 0.0, -0.3f + ((int)i) * 0.02f);
	        float k = 0.0f;
	        int l = 0;
	        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(TexturedRenderLayers.getEntityCutout());
	        MatrixStack.Entry entry = matrixStack.peek();
	        while (i > 0.0f) {
	            Sprite sprite3 = l % 2 == 0 ? sprite : sprite2;
	            float m = sprite3.getMinU();
	            float n = sprite3.getMinV();
	            float o = sprite3.getMaxU();
	            float p = sprite3.getMaxV();
	            if (l / 2 % 2 == 0) {
	                float q = o;
	                o = m;
	                m = q;
	            }
	            EntityRendererDispatcherMixin.drawFireVertex(entry, vertexConsumer, g - 0.0f, 0.0f - j, k, o, p);
	            EntityRendererDispatcherMixin.drawFireVertex(entry, vertexConsumer, -g - 0.0f, 0.0f - j, k, m, p);
	            EntityRendererDispatcherMixin.drawFireVertex(entry, vertexConsumer, -g - 0.0f, 1.4f - j, k, m, n);
	            EntityRendererDispatcherMixin.drawFireVertex(entry, vertexConsumer, g - 0.0f, 1.4f - j, k, o, n);
	            i -= 0.45f;
	            j -= 0.45f;
	            g *= 0.9f;
	            k += 0.03f;
	            ++l;
	        }
	        matrixStack.pop();
		}
	}

	private static void drawFireVertex(MatrixStack.Entry entry, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j) {
		vertexConsumer.vertex(entry.getPositionMatrix(), f, g, h).color(255, 255, 255, 255).texture(i, j).overlay(0, 10).light(240).normal(entry.getNormalMatrix(), 0.0f, 1.0f, 0.0f).next();
	}

}
