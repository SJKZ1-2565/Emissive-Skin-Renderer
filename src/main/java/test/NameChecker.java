package test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import boon4681.ColorUtils.ColorMixer;
import boon4681.ColorUtils.DeltaE;

public class NameChecker {
    public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws IOException {
        createGlowingSkinImage();
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
        String url = texture1.get("url").getAsString();
        return url;
    }


    public static ArrayList<Color> find(ArrayList<Color> color) {

        ArrayList<Color> colors = new ArrayList<>();
        while (color.size() > 0) {
            ArrayList<Color> copyColor = color;
            copyColor.remove(0);
            double min = 10000;
            Color save = null;
            for (Color element : copyColor) {
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
        String name = array.get(array.size() - 1).getAsJsonObject().get("name").getAsString();
        return name;
    }



    public static String getNamee() throws IOException {
        URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/46448e1b402e42e0ad0e8a51ca5abe6a");
        InputStreamReader reader1 = new InputStreamReader(url1.openStream());
        JsonObject property = new JsonParser().parse(reader1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
        String texture = property.get("value").getAsString();
        return texture;
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
        private ArrayList<String> queuedUsernames;
        private Map<String, BufferedImage> skinImages;
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


