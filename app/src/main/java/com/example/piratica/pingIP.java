package com.example.piratica;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import jcifs.netbios.NbtAddress;

public class pingIP extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... params) {
        String hostname=null;
        try {
            InetAddress addr = NbtAddress.getByName("192.168.86.63").getInetAddress();
             hostname = addr.getHostName();
//            String hostmane = addr.getHostName();
            Log.e("ip net address", hostname);
            Log.e("MAC address", String.valueOf(addr));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostname;
    }

}
