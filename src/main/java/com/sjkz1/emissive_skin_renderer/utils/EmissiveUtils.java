package com.sjkz1.emissive_skin_renderer.utils;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class EmissiveUtils {

    public static ResourceLocation getBlockResourceLocation(Block block)
    {
        return new ResourceLocation(Registry.BLOCK.getKey(block).getNamespace() , "textures/block/" + Registry.BLOCK.getKey(block).getPath() + ".png");
    }
    public static ResourceLocation getItemResourceLocation(Item item)
    {
        return new ResourceLocation(Registry.ITEM.getKey(item).getNamespace() , "textures/item/" + Registry.ITEM.getKey(item).getPath() + ".png");
    }

    public static void getImageFromBlock(Block block)
    {
        Resource resource;
        try {
            resource = Minecraft.getInstance().getResourceManager().getResourceOrThrow(EmissiveUtils.getBlockResourceLocation(block));
            try (InputStream inputStream = resource.open();){
                NativeImage nativeImage = NativeImage.read(inputStream);
                nativeImage.writeToFile("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\" + Registry.BLOCK.getKey(block).getPath() + ".png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getImageFromItem(Item item)
    {
        Resource resource;
        try {
            resource = Minecraft.getInstance().getResourceManager().getResourceOrThrow(EmissiveUtils.getItemResourceLocation(item));
            try (InputStream inputStream = resource.open();){
                NativeImage nativeImage = NativeImage.read(inputStream);
                nativeImage.writeToFile("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\" + Registry.ITEM.getKey(item).getPath() + ".png");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
