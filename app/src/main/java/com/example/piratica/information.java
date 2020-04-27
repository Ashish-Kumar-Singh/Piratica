package com.example.piratica;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import jcifs.netbios.NbtAddress;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class information extends AppCompatActivity {
    private TextView InfoText;
    private TextView apiText;
    private TextView header;
    private TextView Hacker;
    private String APIKey= "at_nZNsncxr1W3JtbG0qAiYFF1RVtv6I";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_information);
        InfoText = findViewById(R.id.InfoText);
        apiText = findViewById(R.id.AlertText);
        header = findViewById(R.id.HeaderText);
        Hacker = findViewById(R.id.hacker);
        String LANThumbprint = null;
        String NetThumbprint = null;

        final String link = intent.getStringExtra("user_input");

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
                                            if(netAddress!= null){
                                                if(!isChanged){
                                                    if(netAddress.startsWith("192.168")){
                                                        header.setText("Hacker Detected");
                                                        header.setTextColor(Color.parseColor("#EC4C33"));
                                                        apiText.setText("\n IP address of website" + netAddress + "\n Actual Ip = " + IPList.get(0));
                                                        try {
                                                            String data = new pingIP().execute(netAddress).get();
                                                            if(data!= null){
                                                                Hacker.setText("Hacker System Name "+data);
                                                            }
                                                        } catch (ExecutionException e) {
                                                        } catch (InterruptedException e) {
                                                        }
                                                    }
                                                    else {
                                                        header.setText("No Hacker Detected");
                                                        header.setTextColor(Color.parseColor("#5CCB1C"));
                                                        apiText.setText("\n DNS Record IP " + netAddress + "\n API Ip = " + IPList.get(0));
                                                    }

                                                }
                                                else{
                                                    header.setText("No Hacker Detected");
                                                    header.setTextColor(Color.parseColor("#5CCB1C"));
                                                    apiText.setText("\n Local IP "+netAddress+"\n API Ip = "+IPList.get(0));
                                                }
                                            }else{
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
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
                Toast.makeText(getApplicationContext(),"Unable to Access Website",Toast.LENGTH_SHORT).show();
            }

        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"Unable to Access Website",Toast.LENGTH_SHORT).show();
        }
        try {
            LANThumbprint = new NetIpAdd().execute(link).get();
//            Log.e("Local Thumbprint ", LANThumbprint);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        try {
            NetThumbprint = new GetUrlContentTask().execute(link).get();
//            Log.e("API Thumbprint ", NetThumbprint);
        } catch (Exception e) {

        }

        if(LANThumbprint!=null && NetThumbprint != null){
            if(LANThumbprint.equalsIgnoreCase(NetThumbprint)){
                InfoText.setText(String.format("No Hackers on Network :The Unique Thumbprints match \n Local Network Thumbprint %s\n API Thumbprint %s", LANThumbprint, NetThumbprint));
            }
            else {
                InfoText.setText(String.format("Changes in Certificate Noticed: Hacker Detected\n Local Network Thumbprint %s\n API Thumbprint %s", LANThumbprint, NetThumbprint));
            }
        }
        else {
            InfoText.setText(String.format("Unable to Establish Secure Connection: Network Maybe Compromised\n Local Network Thumbprint %s\n API Thumbprint %s", LANThumbprint, NetThumbprint));
        }

    }



}
