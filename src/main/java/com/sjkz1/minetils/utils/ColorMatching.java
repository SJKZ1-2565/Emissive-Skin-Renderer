package com.sjkz1.minetils.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sjkz1.minetils.Minetils;

import boon4681.ColorUtils.ColorMixer;
import boon4681.ColorUtils.DeltaE;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class ColorMatching {

	private static MinecraftClient client = MinecraftClient.getInstance();

	public static final File GLOWSKIN_DIR = new File(MinecraftClient.getInstance().runDirectory, "glow");
	public static Identifier identifier = new Identifier(Minetils.MOD_ID + ":textures/entity/skin/");
	public static void createGlowingSkinImage() {
		try {
			String url = getSkin();
			BufferedImage image = ImageIO.read(new URL(url).openStream());
			BufferedImage resizedImage = resize(image,  2,2);
			ArrayList<Color> colors = new ArrayList<>();

			for (int y = 0; y < resizedImage.getHeight(); y++) {
				for (int x = 0; x < resizedImage.getWidth(); x++) {
					colors.add(new Color(resizedImage.getRGB(x, y), false));
				}
			}
			ArrayList<Color> pallets = find(colors);
			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					if (DeltaE.getDelta(new Color(image.getRGB(x, y)), pallets.get(0)) < Minetils.CONFIG.getConfig().palletsRate) {
						image.setRGB(x, y, Transparency.TRANSLUCENT);
					}
				}
			}

			if(!GLOWSKIN_DIR.exists())
			{
				GLOWSKIN_DIR.mkdirs();
			}

			ImageIO.write(image,"png", new File(GLOWSKIN_DIR,"glow_layer.png"));
			SJKZ1Helper.runAsync(ColorMatching::MoveToResourceLoc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings("resource")
	public static void MoveToResourceLoc()
	{
		try {
			final NativeImage nativeImage;
			File imageFile = new File(GLOWSKIN_DIR,"glow_layer.png");
			InputStream in = new FileInputStream(imageFile);
			nativeImage = NativeImage.read(in);
			final NativeImageBackedTexture dynamicTexture = new NativeImageBackedTexture(nativeImage);
			NativeImageBackedTexture icon = dynamicTexture;
			if (icon != null) {
				MinecraftClient.getInstance().getTextureManager().registerTexture(identifier, icon);
			}
			MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(Formatting.YELLOW.toString() + Formatting.BOLD + "[GLOWING_SKIN]: " + Formatting.RESET + "Successfully create image to path " + identifier + imageFile.getName()));
		}
		catch (IOException e) {
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
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return dimg;
	}

	public static String getSkin() throws IOException {
		URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + Objects.requireNonNull(client.player).getUuidAsString().replace("-",""));
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
