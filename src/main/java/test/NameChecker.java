package test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.sjkz1.sjkz1misc.utils.SpecialMember;
import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class NameChecker {

    public static void main(String[] arg)
    {
            for(SpecialMember values : SpecialMember.VALUES)
            {
                try {


                    if(!getName(values.getUuid()).equals(values.getName()))
                    {
                        System.out.println("Found not matched, Replace " + values.getName() + " with " + getName(values.getUuid()) +" instead");
                    }
                    else
                    {
                        System.out.println("Not found :D");

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    private static String getName(String uuid) throws JsonSyntaxException, IOException
    {
        URL url = new URL("https://api.mojang.com/user/profiles/" + uuid.replace("-", "") + "/names");
        JsonArray array = new JsonParser().parse(IOUtils.toString(url.openConnection().getInputStream(), StandardCharsets.UTF_8)).getAsJsonArray();
        String name = array.get(array.size() - 1).getAsJsonObject().get("name").getAsString();
        return name;
    }


}
