package com.sjkz1.minetils.utils;

import boon4681.ColorUtils.ColorMixer;
import boon4681.ColorUtils.DeltaE;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sjkz1.minetils.Minetils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

public class ColorMatching {

    private static final MinecraftClient client = MinecraftClient.getInstance();
    public static ArrayList<Integer> blackList = new ArrayList<>();

    public static final File GLOWSKIN_DIR = new File(MinecraftClient.getInstance().runDirectory, "glow");
    public static Identifier identifier = new Identifier(Minetils.MOD_ID + ":textures/entity/skin/");

    public static void createGlowingSkinImage() {
        try {
            String url = getSkin();
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
                    if (DeltaE.getDelta(new Color(image.getRGB(x, y)), pallets.get(0)) < Minetils.CONFIG.main.palletsRate) {
                        image.setRGB(x, y, Transparency.TRANSLUCENT);
                    }
                }
            }

            if (!GLOWSKIN_DIR.exists()) {
                GLOWSKIN_DIR.mkdirs();
            }

            ImageIO.write(image, "png", new File(GLOWSKIN_DIR, "glow_layer.png"));
            SJKZ1Helper.runAsync(ColorMatching::MoveToResourceLoc);
            Minetils.LOGGER.info("Created Skin Already!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createGlowingSkinImageWithCustomUV(int minX, int minY, int maxX, int maxY) {
        try {
            String url = getSkin();
            BufferedImage image = ImageIO.read(new URL(url).openStream());//TODO not coming soon
            BufferedImage resizedImage = resize(image, 2, 2);
            ArrayList<Color> colors = new ArrayList<>();

            for (int y = 0; y < resizedImage.getHeight(); y++) {
                for (int x = 0; x < resizedImage.getWidth(); x++) {
                    colors.add(new Color(resizedImage.getRGB(x, y), false));
                }
            }
            ArrayList<Color> pallets = find(colors);
            for (int y = 0; y <= image.getHeight(); y++) {
                for (int x = 16; x <= image.getWidth(); x++) {

                    if (x > 64 || y > 16) {
                        image.setRGB(x, y, Transparency.TRANSLUCENT);
                    }

                    for (int newX = 16; newX < 64; newX++) {
                        for (int newY = 0; newY < 16; newY++) {
                            if (DeltaE.getDelta(new Color(image.getRGB(newX, newY)), pallets.get(0)) < Minetils.CONFIG.main.palletsRate || image.getRGB(newX, newY) == Color.WHITE.getRGB()) {
                                image.setRGB(newX, newY, Transparency.TRANSLUCENT);
                            }
                        }
                    }
                }
            }

            if (!GLOWSKIN_DIR.exists()) {
                GLOWSKIN_DIR.mkdirs();
            }

            ImageIO.write(image, "png", new File(GLOWSKIN_DIR, "glow_layer.png"));
            SJKZ1Helper.runAsync(ColorMatching::MoveToResourceLoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBlackList(Color element) {
        blackList.add(Color.WHITE.getRGB());
        blackList.add(Color.BLACK.getRGB());
    }

    @SuppressWarnings("resource")
    public static void MoveToResourceLoc() {
        try {
            final NativeImage nativeImage;
            File imageFile = new File(GLOWSKIN_DIR, "glow_layer.png");
            InputStream in = new FileInputStream(imageFile);
            nativeImage = NativeImage.read(in);
            final NativeImageBackedTexture dynamicTexture = new NativeImageBackedTexture(nativeImage);
            NativeImageBackedTexture icon = dynamicTexture;
            if (icon != null) {
                MinecraftClient.getInstance().getTextureManager().registerTexture(identifier, icon);
            }
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
        URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + client.player.getUuidAsString().replace("-", ""));
        InputStreamReader reader1 = new InputStreamReader(url1.openStream());
        JsonObject property = JsonParser.parseReader(reader1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
        String texture = property.get("value").getAsString();
        byte[] decodedBytes = Base64.getMimeDecoder().decode(texture);
        String decodedMime = new String(decodedBytes);
        JsonObject property1 = JsonParser.parseString(decodedMime).getAsJsonObject().get("textures").getAsJsonObject();
        JsonObject texture1 = property1.get("SKIN").getAsJsonObject();
        return texture1.get("url").getAsString();
    }
}
