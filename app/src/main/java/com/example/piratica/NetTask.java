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
        String netaddress=null;
        InetAddress addr = null;
        try
        {
            addr = InetAddress.getByName(params[0]);
            Log.e("LAN IP", addr.getHostAddress());
        }

        catch (UnknownHostException e)
        {
            Log.e("Local IP:", "Unable to fetch");
        }
        if(addr!=null){
            netaddress = addr.getHostAddress();
        }

        return netaddress;
    }
}
