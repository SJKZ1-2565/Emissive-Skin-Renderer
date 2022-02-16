package com.sjkz1.minetils.utils;

import com.sjkz1.minetils.Minetils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPSCodeThread extends Thread{
    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL("https://covid19.ddc.moph.go.th/api/Cases/today-cases-by-provinces");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection huc = null;

        try {
            huc = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Minetils.RESPONSE_CODE = huc.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
