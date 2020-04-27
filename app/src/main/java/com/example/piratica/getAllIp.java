package com.example.piratica;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jcifs.netbios.NbtAddress;

public class getAllIp extends AsyncTask<String, Integer, Map<String, String>> {
     public Map<String, String> map = new HashMap<String, String>();
    @Override
    protected HashMap<String, String> doInBackground(String... params) {
        int timeout = 30;
        for (int i = 1; i < 255; i++) {
            String host = params[0] + "." + i;
            try {
                if (InetAddress.getByName(host).isReachable(timeout)) {
                    InetAddress addr = NbtAddress.getByName(host).getInetAddress();
                    String hostname = addr.getHostName();
                    map.put(host,hostname);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (HashMap<String, String>) map;
    }
}