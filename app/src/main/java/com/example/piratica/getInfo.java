package com.example.piratica;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

class getInfo extends AsyncTask<String, Integer, ArrayList<String>> {
    private Activity activity; //activity is defined as a global variable in your AsyncTask

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        URL url;
        String link = params[0];
        ArrayList<String>IPList = new ArrayList<>();
        {
            try {
                //    private String APIKey= "at_nZNsncxr1W3JtbG0qAiYFF1RVtv6I";
                String APIKey = "at_WazJoJ9CHAE3lLWho1qpD2RNOt5ta";
                url = new URL("https://www.whoisxmlapi.com/whoisserver/DNSService?apiKey="+ APIKey +"&domainName="+link+"&type=1&outputFormat=JSON");
                HttpsURLConnection httpConn = (HttpsURLConnection)url.openConnection();
                httpConn.setRequestMethod("GET");
                httpConn.setConnectTimeout(3000);
                int responseCode = httpConn.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK){
                    InputStream in = new BufferedInputStream(httpConn.getInputStream());
                    BufferedReader bin = new BufferedReader(new InputStreamReader(in));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = bin.readLine()) != null) {
                        response.append(inputLine);
                    }
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(String.valueOf(response));
                        JSONObject dnsData = jsonObject.optJSONObject("DNSData");
                        JSONArray dnsRecords = dnsData.getJSONArray("dnsRecords");
                        JSONObject objects = dnsRecords.getJSONObject(0);
                        Log.e("IP", objects.getString("address"));
                        for(int i=0;i<dnsRecords.length();i++){
                            JSONObject object = dnsRecords.getJSONObject(i);
                            if(object!=null){
                                String ip =object.getString("address");
                                IPList.add(ip);
                            }
                        }
                    } catch (JSONException e) {
                        Log.e("API IP :", "Unable to Fetch");
                    }
                }else{
                    Log.e("API IP :", "Unable to Fetch");
                }


            } catch (MalformedURLException e) {
                Log.e("API IP :", "Unable to Fetch");
            } catch (IOException e) {
                Log.e("API IP :", "Unable to Fetch");
                e.printStackTrace();
                Log.e("API IP :", "Unable to Fetch");
            }
        }
        return  IPList;
    }

}
