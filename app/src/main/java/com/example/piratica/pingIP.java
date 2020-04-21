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
        try {
            InetAddress addr = NbtAddress.getByName("192.168.86.21").getInetAddress();
            String hostname = addr.getHostName();
//            String hostmane = addr.getHostName();
            Log.e("ip net address", hostname);
//            Log.e("MAC address", new String(mac));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

}
