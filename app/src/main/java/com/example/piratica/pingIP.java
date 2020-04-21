package com.example.piratica;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class pingIP extends AsyncTask<String, Integer, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            int timeout=1000;
            for (int i=1;i<255;i++){
                try{
                    String host=params[0] + "." + i;
                    if (InetAddress.getByName(host).isReachable(timeout)){
                        Log.e("IP ",host + " is reachable");
                    }
                }catch(Exception e){

                }

            }

            return null;
        }
    }
