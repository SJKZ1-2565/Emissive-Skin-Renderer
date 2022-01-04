package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.imageio.ImageIO;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import com.sjkz1.minetils.utils.Text;
import io.netty.util.CharsetUtil;
import org.apache.commons.io.IOUtils;

import boon4681.ColorUtils.ColorMixer;
import boon4681.ColorUtils.DeltaE;
import io.netty.util.internal.StringUtil;

public class NameChecker {
	public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

	public static void main(String[] args) throws IOException {
		String s = String.format(readURLToString("https://covid19.ddc.moph.go.th/api/Cases/today-cases-by-provinces"), "/t");

		 try{
	            // Create new file
	            String content = "This is the content to write into create file";
	            String path=System.getProperty("user.home") + "/Desktop/hi.json";
	            File file = new File(path);

	            // If file doesn't exists, then create it
	            if (!file.exists()) {
	                file.createNewFile();
	            }

	            FileWriter fw = new FileWriter(file.getAbsoluteFile());
	            BufferedWriter bw = new BufferedWriter(fw);

	            // Write in file
	            byte[] utf8 = s.getBytes("UTF-8");

	            // Convert from UTF-8 to Unicode
	            s = new String(utf8, "UTF-8");
	            bw.write(s);

	            // Close connection
	            bw.close();
	        }
	        catch(Exception e){
	            System.out.println(e);
	        }


		if(s.contains("\\u0e2a\\u0e07\\u0e02\\u0e25\\u0e32")) {
			URL url = new URL("https://covid19.ddc.moph.go.th/api/Cases/today-cases-by-provinces");
			InputStreamReader reader1 = new InputStreamReader(url.openStream());
			JsonArray obj = JsonParser.parseReader(reader1).getAsJsonArray();
			for (int i = 0; i < obj.size(); ++i) {
				int new_case = obj.get(i).getAsJsonObject().get("new_case").getAsInt();
				String province = obj.get(i).getAsJsonObject().get("province").getAsString();
				byte[] utf8 = province.getBytes("UTF-8");

				// Convert from UTF-8 to Unicode
				String result = new String(utf8, "UTF-8");
				try{
					// Create new file
					String content = "This is the content to write into create file";
					String path=System.getProperty("user.home") + "/Desktop/hidqa.json";
					File file = new File(path);

					// If file doesn't exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}

					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					for(int k =0;k<obj.size();k++)
					{
						bw.write(obj.get(k).getAsJsonObject().get("province").getAsJsonObject().getAsString());
						bw.newLine();
					}
					bw.close();
				}
				catch(Exception e){
					System.out.println(e);
				}


//				byte[] utf8 = s.getBytes("UTF-16");
//
//	            // Convert from UTF-8 to Unicode
//	            s = new String(utf8, "UTF-8");
//				System.out.println(s);
//				if(id.contains("\\u0e2a\\u0e07\\u0e02\\u0e25\\u0e32"))
//				{
//					System.out.println("cow");
//				}
			}
			//           int dad = obj.getAsJsonObject().get("province").getAsJsonObject().get("new_case").getAsInt();

		}

	}
	public static void createGlowingSkinImage() {
		try {
			String url = getSkin();
			BufferedImage image = ImageIO.read(new URL(url).openStream());
			BufferedImage resizedImage = resize(image, 3, 3);
			ArrayList<Color> colors = new ArrayList<>();

			for (int y = 0; y < resizedImage.getHeight(); y++) {
				for (int x = 0; x < resizedImage.getWidth(); x++) {
					colors.add(new Color(resizedImage.getRGB(x, y), false));
				}
			}
			ArrayList<Color> pallets = find(colors);
			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					if (DeltaE.getDelta(new Color(image.getRGB(x, y)), pallets.get(0)) < 96.55) {
						image.setRGB(x, y, Transparency.TRANSLUCENT);
					}
				}
			}


			ImageIO.write(image,"png", new File("C:\\Users\\USER\\Desktop\\Modding\\glow.png"));



		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getSkin() throws IOException {
		URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/46448e1b402e42e0ad0e8a51ca5abe6a");
		InputStreamReader reader1 = new InputStreamReader(url1.openStream());
		JsonObject property = new JsonParser().parse(reader1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
		String texture = property.get("value").getAsString();
		byte[] decodedBytes = Base64.getMimeDecoder().decode(texture);
		String decodedMime = new String(decodedBytes);
		JsonObject property1 = new JsonParser().parse(decodedMime).getAsJsonObject().get("textures").getAsJsonObject();
		JsonObject texture1 = property1.get("SKIN").getAsJsonObject();
		return texture1.get("url").getAsString();
	}

	public static String readURLToString(String url) throws IOException
	{
		try (InputStream inputStream = new URL(url).openStream())
		{
			return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
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

	private static String getName(String uuid) throws JsonSyntaxException, IOException {
		URL url = new URL("https://api.mojang.com/user/profiles/" + uuid.replace("-", "") + "/names");
		JsonArray array = new JsonParser().parse(IOUtils.toString(url.openConnection().getInputStream(), StandardCharsets.UTF_8)).getAsJsonArray();
		return array.get(array.size() - 1).getAsJsonObject().get("name").getAsString();
	}



	public static String getNamee() throws IOException {
		URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/46448e1b402e42e0ad0e8a51ca5abe6a");
		InputStreamReader reader1 = new InputStreamReader(url1.openStream());
		JsonObject property = new JsonParser().parse(reader1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
		return property.get("value").getAsString();
	}


	public static void getDiscordMemberAmount() throws JsonSyntaxException, IOException {
		URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/46448e1b402e42e0ad0e8a51ca5abe6a");
		JsonArray array = new JsonParser().parse(IOUtils.toString(url.openConnection().getInputStream(), StandardCharsets.UTF_8)).getAsJsonArray();
		String value = array.get(array.size() - 1).getAsJsonObject().get("properties").getAsString();
		System.out.println(value);
	}

	public static UUID getOfflinePlayerUuid(String string) {
		return UUID.nameUUIDFromBytes(("OfflinePlayer:" + string).getBytes(StandardCharsets.UTF_8));
	}
	static class ThreadDownloadPlayerSkin extends Thread
	{
		private static ThreadDownloadPlayerSkin instance;
		private static final String SKIN_LOCATION = "http://skins.minecraft.net/MinecraftSkins/%s.png";
		private final ArrayList<String> queuedUsernames;
		private final Map<String, BufferedImage> skinImages;
		public final Object obj;

		private ThreadDownloadPlayerSkin() {
			this.skinImages = new HashMap<>();
			this.obj = new Object();
			this.queuedUsernames = new ArrayList<>();
		}

		public static ThreadDownloadPlayerSkin getInstance() {
			if (ThreadDownloadPlayerSkin.instance == null) {
				(ThreadDownloadPlayerSkin.instance = new ThreadDownloadPlayerSkin()).setName("Energetic Player Skin Downloader");
				ThreadDownloadPlayerSkin.instance.setPriority(3);
				ThreadDownloadPlayerSkin.instance.start();
			}
			return ThreadDownloadPlayerSkin.instance;
		}

		public ArrayList<String> getQueuedUsernames() {
			return this.queuedUsernames;
		}

		public Map<String, BufferedImage> getSkinImages() {
			return this.skinImages;
		}

		@Override
		public void run() {
			while (true) {
				for (final String username : this.queuedUsernames) {
					try {
						final BufferedImage skinImage = ImageIO.read(new URL(String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", username)));
						this.skinImages.put(username, skinImage);
						ImageIO.write(skinImage, "png", new File("C:\\Users\\USER\\Desktop\\Modding\\SJKZ1misc\\SJKZ1Misc 1.17.1\\src\\main\\resources\\assets\\minetils\\textures\\entitydownloaded_skin.png"));
					}
					catch (Exception ex) {}
					synchronized (this.obj) {
						this.queuedUsernames.remove(username);
					}
				}
			}
		}
	}
}


