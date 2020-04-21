package com.example.piratica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class information extends AppCompatActivity {
    private TextView InfoText;
    private TextView apiText;
    private String APIKey= "at_nZNsncxr1W3JtbG0qAiYFF1RVtv6I";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_information);
        InfoText = findViewById(R.id.InfoText);
        apiText = findViewById(R.id.apitext);
        String LANThumbprint = null;
        String NetThumbprint = null;

        final String link = "dit.ie";
        try {
            LANThumbprint = new NetIpAdd().execute(link).get();
            Log.e("Local Thumbprint ", LANThumbprint);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        try {
//            new pingIP().execute("192.168.0").get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            NetThumbprint = new GetUrlContentTask().execute(link).get();
            Log.e("API Thumbprint ", NetThumbprint);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(LANThumbprint.equalsIgnoreCase(NetThumbprint)){
            InfoText.setText("No Hackers on Network :The Unique Thumbprints match " +
                    "\n Local Network THumbprint "+LANThumbprint+"\n API Thumbprint "+NetThumbprint);
        }
        else{
            InfoText.setText("Changes in Certificate Noticed: Hacker Detected");
        }


        OkHttpClient client = new OkHttpClient();
        String url = "https://www.whoisxmlapi.com/whoisserver/DNSService?apiKey="+APIKey+"&domainName="+link+"&type=1&outputFormat=JSON";
        try{
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try{
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            final String myResponse = response.body().string();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(myResponse);
                                JSONObject dnsData = jsonObject.optJSONObject("DNSData");
                                JSONArray dnsRecords = dnsData.getJSONArray("dnsRecords");
                                JSONObject objects = dnsRecords.getJSONObject(0);
                                Log.e("IP", objects.getString("address"));
                                final ArrayList<String> IPList = new ArrayList<String>();
                                for(int i=0;i<dnsRecords.length();i++){
                                    JSONObject object = dnsRecords.getJSONObject(i);
                                    if(object!=null){
                                        String ip =object.getString("address");
                                        IPList.add(ip);
                                    }
                                }
                                information.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try
                                        {
                                            String netAddress = null;
                                            netAddress = new NetTask().execute(link).get();
                                            Boolean isChanged = false;
                                            for(String item: IPList){
                                                if(item.equals(netAddress)){
                                                    isChanged=true;
                                                    Log.e("IP", item);
                                                }
                                            }
                                            if(!isChanged){
                                                apiText.setText("Hacker Detected");
                                                apiText.append("\n Local IP "+netAddress);
                                            }
                                            else{
                                                apiText.setText("No Hacker Detected");
                                                apiText.append("\n Local IP "+netAddress+"\n API Ip = "+IPList.get(0));
                                            }

                                        }
                                        catch (Exception e1)
                                        {
                                            e1.printStackTrace();
                                        }

                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

            }catch(Exception e){
                apiText.setText("Hacker Detected");
            }

        }catch(Exception e){
            apiText.setText("Hacker Detected");
        }


    }

}
