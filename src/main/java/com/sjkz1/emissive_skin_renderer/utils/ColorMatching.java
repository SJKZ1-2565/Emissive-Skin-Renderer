package com.sjkz1.emissive_skin_renderer.utils;

import boon4681.ColorUtils.ColorMixer;
import boon4681.ColorUtils.DeltaE;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.NativeImage;
import com.sjkz1.emissive_skin_renderer.EmissiveSkinRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

public class ColorMatching {
    private static final Minecraft client = Minecraft.getInstance();
    public static final File GLOWSKIN_DIR = new File(Minecraft.getInstance().gameDirectory, "glow");
    public static ResourceLocation identifier = new ResourceLocation(EmissiveSkinRenderer.MOD_ID + ":textures/entity/skin/");

    public static void createGlowingSkinImageLessThan() {
        try {
            String url = getSkin();
            if (url.isBlank() || url.isEmpty()) return;
            BufferedImage image = ImageIO.read(new URL(url).openStream());
            BufferedImage resizedImage = resize(image, 2, 2);
            ArrayList<Color> colors = new ArrayList<>();

            for (int y = 0; y < resizedImage.getHeight(); y++) {
                for (int x = 0; x < resizedImage.getWidth(); x++) {
                    colors.add(new Color(resizedImage.getRGB(x, y), false));
                }
            }
            ArrayList<Color> pallets = find(colors);
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (DeltaE.getDelta(new Color(image.getRGB(x, y)), pallets.get(0)) < EmissiveSkinRenderer.CONFIG.main.palletsRate) {
                        image.setRGB(x, y, Transparency.TRANSLUCENT);
                    }
                }
            }

            if (!GLOWSKIN_DIR.exists()) {
                GLOWSKIN_DIR.mkdirs();
            }

            ImageIO.write(image, "png", new File(GLOWSKIN_DIR, "glow_layer.png"));
            SJKZ1Helper.runAsync(ColorMatching::MoveToResourceLoc);
            EmissiveSkinRenderer.LOGGER.info("Created Skin Already!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createGlowingSkinImageLGreaterThan() {
        try {
            String url = getSkin();
            if (url.isBlank() || url.isEmpty()) return;
            BufferedImage image = ImageIO.read(new URL(url).openStream());
            BufferedImage resizedImage = resize(image, 2, 2);
            ArrayList<Color> colors = new ArrayList<>();

            for (int y = 0; y < resizedImage.getHeight(); y++) {
                for (int x = 0; x < resizedImage.getWidth(); x++) {
                    colors.add(new Color(resizedImage.getRGB(x, y), false));
                }
            }
            ArrayList<Color> pallets = find(colors);
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    if (DeltaE.getDelta(new Color(image.getRGB(x, y)), pallets.get(0)) < EmissiveSkinRenderer.CONFIG.main.palletsRate) {
                        image.setRGB(x, y, Transparency.TRANSLUCENT);
                    }
                }
            }

            if (!GLOWSKIN_DIR.exists()) {
                GLOWSKIN_DIR.mkdirs();
            }

            ImageIO.write(image, "png", new File(GLOWSKIN_DIR, "glow_layer.png"));
            SJKZ1Helper.runAsync(ColorMatching::MoveToResourceLoc);
            EmissiveSkinRenderer.LOGGER.info("Created Skin Already!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void MoveToResourceLoc() {
        try {
            File imageFile = new File(GLOWSKIN_DIR, "glow_layer.png");
            InputStream in = new FileInputStream(imageFile);
            NativeImage nativeImage = NativeImage.read(in);
            TextureManager textureManager = client.getTextureManager();
            textureManager.register(identifier, new DynamicTexture(nativeImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Color> find(ArrayList<Color> color) {

        ArrayList<Color> colors = new ArrayList<>();
        while (color.size() > 0) {
            color.remove(0);
            double min = 10000;
            Color save = null;
            for (Color element : color) {
                double calculate = DeltaE.getDelta(element, color.get(0));
                if (calculate < min) {
                    save = element;
                    min = calculate;
                }
            }
            if (save == null) {
                break;
            }
            colors.add(ColorMixer.mix(color.get(0), save));
            color.remove(save);
            color.remove(0);
        }
        return colors;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dim = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dim.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dim;
    }

    public static String getSkin() throws IOException {
        assert client.player != null;
        URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + client.player.getStringUUID().replace("-", ""));
        InputStreamReader reader1 = new InputStreamReader(url1.openStream());
        JsonObject property = JsonParser.parseReader(reader1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
        property.isJsonNull();
        String texture = property.get("value").getAsString();
        byte[] decodedBytes = Base64.getMimeDecoder().decode(texture);
        String decodedMime = new String(decodedBytes);
        JsonObject property1 = JsonParser.parseString(decodedMime).getAsJsonObject().get("textures").getAsJsonObject();
        JsonObject texture1 = property1.get("SKIN").getAsJsonObject();
        return texture1.get("url").getAsString();
    }
}
