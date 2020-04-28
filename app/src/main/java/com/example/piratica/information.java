package com.example.piratica;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

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
    private String responseData;
    private ImageView networkState;
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
        networkState = (ImageView) findViewById(R.id.networkState);
        String LANThumbprint = null;
        String NetThumbprint = null;
        String netAddress = null;
        ArrayList<String>IPList = new ArrayList<>();
        Boolean isChanged = false;
        final String link = intent.getStringExtra("user_input");

//        OkHttpClient client = new OkHttpClient();
//        String url = "https://www.whoisxmlapi.com/whoisserver/DNSService?apiKey="+APIKey+"&domainName="+link+"&type=1&outputFormat=JSON";
//        try{
//            Request request = new Request.Builder()
//                    .url(url)
//                    .build();
//            try{
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        if(response.isSuccessful()){
//                            final String myResponse = response.body().string();
//                            JSONObject jsonObject = null;
//                            try {
//                                jsonObject = new JSONObject(myResponse);
//                                JSONObject dnsData = jsonObject.optJSONObject("DNSData");
//                                JSONArray dnsRecords = dnsData.getJSONArray("dnsRecords");
//                                JSONObject objects = dnsRecords.getJSONObject(0);
//                                Log.e("IP", objects.getString("address"));
//                                final ArrayList<String> IPList = new ArrayList<String>();
//                                for(int i=0;i<dnsRecords.length();i++){
//                                    JSONObject object = dnsRecords.getJSONObject(i);
//                                    if(object!=null){
//                                        String ip =object.getString("address");
//                                        IPList.add(ip);
//                                    }
//                                }
//                                information.this.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try
//                                        {
//                                            String netAddress = null;
//                                            netAddress = new NetTask().execute(link).get();
//                                            Boolean isChanged = false;
//                                            for(String item: IPList){
//                                                if(item.equals(netAddress)){
//                                                    isChanged=true;
//                                                    Log.e("IP", item);
//                                                }
//                                            }
//                                            if(netAddress!= null){
//                                                if(!isChanged){
//                                                    if(netAddress.startsWith("192.168")){
//                                                        header.setText("Hacker Detected");
//                                                        header.setTextColor(Color.parseColor("#EC4C33"));
//                                                        apiText.setText("\n IP address of website" + netAddress + "\n Actual Ip = " + IPList.get(0));
//                                                        networkState.setImageResource(R.drawable.alert);
//                                                        try {
//                                                            String data = new pingIP().execute(netAddress).get();
//                                                            if(data!= null){
//                                                                Hacker.setText("Hacker System Name "+data);
//                                                                Hacker.append("\n Hacker IP: "+netAddress);
//                                                            }
//                                                        } catch (ExecutionException e) {
//                                                        } catch (InterruptedException e) {
//                                                        }
//                                                    }
//                                                    else {
//                                                        header.setText("No Hacker Detected");
//                                                        header.setTextColor(Color.parseColor("#5CCB1C"));
//                                                        apiText.setText("\n DNS Record IP " + netAddress + "\n API Ip = " + IPList.get(0));
//                                                    }
//
//                                                }
//                                                else{
//                                                    header.setText("No Hacker Detected");
//                                                    header.setTextColor(Color.parseColor("#5CCB1C"));
//                                                    apiText.setText("\n Local IP "+netAddress+"\n API Ip = "+IPList.get(0));
//                                                }
//                                            }else{
//                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                                startActivity(intent);
//                                            }
//
//
//
//                                        }
//                                        catch (Exception e1)
//                                        {
//                                            e1.printStackTrace();
//                                        }
//
//                                    }
//                                });
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }
//                });
//
//            }catch(Exception e){
//                Toast.makeText(getApplicationContext(),"Unable to Access Website",Toast.LENGTH_SHORT).show();
//            }
//
//        }catch(Exception e){
//            Toast.makeText(getApplicationContext(),"Unable to Access Website",Toast.LENGTH_SHORT).show();
//        }
        try {
            Log.e("1 :", "Getting Local IP");
            netAddress = new NetTask().execute(link).get();
            Log.e("2 :", "Got Local IP");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Log.e("3 :", "Getting API IP");
             IPList = new getInfo().execute(link).get();
            Log.e("4 :", "Got API IP");
            } catch (ExecutionException e) {
                Log.e("API IP :", "Unable to Fetch");
            } catch (InterruptedException e) {
                Log.e("API IP :", "Unable to Fetch");
            }


            if(IPList!=null){
                Log.e("5 :", "IPList is not null");
                for(String item: IPList){
                    if(item.equals(netAddress)){
                        isChanged=true;
                        Log.e("IP", item);
                    }else{
                        isChanged=false;
                        Log.e("5-1:", "IP does not match");
                    }
                }
            }else{
                Log.e("5 :", "IPList is null");
            }

            if(netAddress!= null && IPList!=null){
                Log.e("6:", "Loacl Ip not null");
                if(!isChanged){
                    Log.e("6-1 :", "Change in IPs noticed");
                    if(netAddress.startsWith("192.168")){
                        Log.e("6-1-1 :", "Hacker Detected");
                        header.setText("Hacker Detected");
                        header.setTextColor(Color.parseColor("#EC4C33"));
                        apiText.setText("\n IP address of website" + netAddress);
                        try {
                            networkState.setImageResource(R.drawable.alert);
                            Log.e("6-1-2 :", "Getting hacker info");
                            String data = new pingIP().execute(netAddress).get();
                            Log.e("6-1-2 :", "Got hacker info");
                            if(data!= null){
                                Log.e("6-1-2-1 :", "Displaying hacker info");
                                Hacker.setText("Hacker System Name "+data);
                                Hacker.append("\n Hacker IP: "+netAddress);
                            }
                        } catch (ExecutionException e) {
                            Log.e("6-1-2-2 :", "Displaying hacker info Error");
                        } catch (InterruptedException e) {
                            Log.e("6-1-2-2 :", "Displaying hacker info Error");
                        }
                    }
                    else {
                        Log.e("6-2-1 :", "No Hacker Detected");
                        header.setText("No Hacker Detected");
                        apiText.setText("\n Local IP "+netAddress+"\n API Ip = "+IPList.get(0));
                    }

                }
                else{
                    Log.e("6-2 :", "No Hacker Detected");
                    header.setText("No Hacker Detected");
                    apiText.setText("\n Local IP "+netAddress+"\n API Ip = "+IPList.get(0));
                }
            }else{
                Log.e("6 :", "Local Ip is empty");
                networkState.setImageResource(R.drawable.alert);
                apiText.setText("Please input a Valid Website");
            }



        try {
            Log.e("7 :", "Getting Local Thumbprint");
            LANThumbprint = new NetIpAdd().execute(link).get();
            Log.e("7 :", "Got Local Thumbprint");
        } catch (InterruptedException e) {
            Log.e("Local Thumbprint :", "Unable to Fetch");
        } catch (ExecutionException e) {
            Log.e("Local Thumbprint :", "Unable to Fetch");
        }

        try {
            Log.e("8 :", "Getting API Thumbprint");
            NetThumbprint = new GetUrlContentTask().execute(link).get();
            Log.e("8 :", "Got API Thumbprint");
        } catch (Exception e) {
            Log.e("API Thumbprint :", "Unable to Fetch");
        }
               if(LANThumbprint!=null && NetThumbprint != null){
                   Log.e("9 :", "Thumbprints are not null");
                        if(LANThumbprint.equalsIgnoreCase(NetThumbprint)){
                            Log.e("9-1 :", "Thumbprints matches");
                            InfoText.setText(String.format("No Hackers on Network :The Unique Thumbprints match\n Local Network Thumbprint %s\n API Thumbprint %s", LANThumbprint, NetThumbprint));
                        }
                        else {
                            Log.e("9-1 :", "Thumbprints do not match");
                            if(netAddress.startsWith("192.168")){
                                Log.e("9-1-1 :", "Thumbprints do not match and Hacker IP");
                                header.setText("Hacker Detected");
                                header.setTextColor(Color.parseColor("#EC4C33"));
                                InfoText.setText(String.format("Changes in Certificate Noticed: Hacker Detected\n Local Network Thumbprint %s\n API Thumbprint %s", LANThumbprint, NetThumbprint));
                        }
                        else {
                                Log.e("9-1-2 :", "Thumbprints do not match but no Hacker");
                            InfoText.setText(String.format("No Hackers on Network :The Thumbprints are however different\n Local Network Thumbprint %s\n API Thumbprint %s", LANThumbprint, NetThumbprint));
                        }
                        }

                }
                else {
                   if(netAddress !=null && netAddress.startsWith("192.168")){
                       Log.e("9-2 :", "Thumbprints are empty and Ip matches HAcker");
                       header.setText("Hacker Detected");
                       header.setTextColor(Color.parseColor("#EC4C33"));
                       InfoText.setText(String.format("Network Maybe Compromised\n Local Network Thumbprint %s\n API Thumbprint %s", LANThumbprint, NetThumbprint));
                   }
                   else{
                       Log.e("9-2 :", "Thumbprints are empty due to  Invalid Website");
                   }

                }


    }



}
