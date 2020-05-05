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
            InetAddress addr = NbtAddress.getByName(params[0]).getInetAddress();
//            byte[] mac = NbtAddress.getByName(params[0]).getMacAddress();
//            if (mac != null) {D
//                /*
//                 * Extract each array of mac address and convert it
//                 * to hexadecimal with the following format
//                 * 08-00-27-DC-4A-9E.
//                 */
//                for (int i = 0; i < mac.length; i++) {
//                    System.out.format("%02X%s",
//                            mac[i], (i < mac.length - 1) ? "-" : "");
//                }
//            } else {
//                Log.e("Address doesn't exist",null);
//            }
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
