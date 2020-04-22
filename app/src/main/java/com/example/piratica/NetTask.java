package com.example.piratica;

import android.os.AsyncTask;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetTask extends AsyncTask<String, Integer, String>
    //Gets IP address of the website on the current LAN Networks
{
    @Override
    protected String doInBackground(String... params)
    {
        InetAddress addr = null;
        try
        {
            addr = InetAddress.getByName(params[0]);
            Log.e("LAN IP", addr.getHostAddress());
        }

        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return addr.getHostAddress();
    }
}
