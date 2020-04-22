package com.example.piratica;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getIpFromURL {
    private String APIKey = "at_nZNsncxr1W3JtbG0qAiYFF1RVtv6I";
    public ArrayList<String>  getfromURL(String link){
        final ArrayList<String> IPList = new ArrayList<String>();
        OkHttpClient client = new OkHttpClient();
        String url = "https://www.whoisxmlapi.com/whoisserver/DNSService?apiKey=" + APIKey + "&domainName=" + link + "&type=1&outputFormat=JSON";
        try{
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(myResponse);
                                JSONObject dnsData = jsonObject.optJSONObject("DNSData");
                                JSONArray dnsRecords = dnsData.getJSONArray("dnsRecords");
                                JSONObject objects = dnsRecords.getJSONObject(0);
                                Log.e("IP", objects.getString("address"));

                                for (int i = 0; i < dnsRecords.length(); i++) {
                                    JSONObject object = dnsRecords.getJSONObject(i);
                                    if (object != null) {
                                        String ip = object.getString("address");
                                        IPList.add(ip);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

            } catch (Exception e) {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return IPList;

    }

}