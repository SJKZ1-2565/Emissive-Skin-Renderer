package com.sjkz1.minetils.utils;

import com.google.gson.annotations.SerializedName;

public class Donate {


    //Taken from https://github.com/SteveKunG/SkyBlockcatia/blob/2b423d2c5c89cfd31df07e66558dd47bf8fe0e0b/src/main/java/com/stevekung/skyblockcatia/utils/skyblock/SBMisc.java
    @SerializedName("donate")
    private final int donate;

    public Donate(int donate)
    {
        this.donate = donate;
    }

    public int getTotalDonate()
    {
        return this.donate;
    }

}
