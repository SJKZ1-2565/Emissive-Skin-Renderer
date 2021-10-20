package test;

import boon4681.ColorUtils.ColorMixer;
import boon4681.ColorUtils.DeltaE;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class NameChecker {
    public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] arg) {
        try {

            URL url = new URL(String.format("http://skins.minecraft.net/MinecraftSkins/SJKZ1.png"));
            InputStream is = url.openStream();
            //BufferedImage image = ImageIO.read(new URL(String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", ChatUtil.stripTextFormat("SteveKunG"))).openStream());
            File file = new File(url.getFile());
            BufferedImage image = ImageIO.read(file);
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
                    if (DeltaE.getDelta(new Color(image.getRGB(x, y)), pallets.get(0)) < 96F) {
                        image.setRGB(x, y, 0x0000ffff);
                    }
                }
            }
            ImageIO.write(image, "png", new File("C:\\Users\\USER\\Desktop\\Modding\\SJKZ1misc\\SJKZ1Misc 1.17.1\\src\\main\\resources\\assets\\minetils\\textures\\downloaded_skin.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Color> find(ArrayList<Color> color) {

        ArrayList<Color> colors = new ArrayList<>();
        while (color.size() > 0) {
            ArrayList<Color> copyColor = color;
            copyColor.remove(0);
            double min = 10000;
            Color save = null;
            for (int i = 0; i < copyColor.size(); i++) {
                double calculate = DeltaE.getDelta(copyColor.get(i), color.get(0));
                if (calculate < min) {
                    save = copyColor.get(i);
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

    public static void getDiscordMemberAmount() throws JsonSyntaxException, IOException {
        URL url = new URL("https://discordapp.com/api/guilds/675288690658115588/widget.json");
        JsonObject array = (JsonObject) new JsonParser().parse(IOUtils.toString(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
        int DonateAmount = array.get("presence_count").getAsInt();
    }

    public static UUID getOfflinePlayerUuid(String string) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + string).getBytes(StandardCharsets.UTF_8));
    }


}


