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
        //Setting of Variables
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
        final String link = intent.getStringExtra("user_input");//Getting the input from user

        try {
            Log.e("1 :", "Getting Local IP");
            netAddress = new NetTask().execute(link).get();//Getting the local IP of the website from the local network.
            Log.e("2 :", "Got Local IP");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Log.e("3 :", "Getting API IP");
             IPList = new getInfo().execute(link).get();//Getting the official List of IPs of the website from the API
            Log.e("4 :", "Got API IP");
            } catch (ExecutionException e) {
                Log.e("API IP :", "Unable to Fetch");
            } catch (InterruptedException e) {
                Log.e("API IP :", "Unable to Fetch");
            }


            if(IPList!=null){//If the IP List is not empty compare the IPlist to the IP we retrieved from the local network
                Log.e("5 :", "IPList is not null");
                for(String item: IPList){
                    if(item.equals(netAddress)){
                        //Boolean is false by default
                        //If the IPs are same the boolean is set to true
                        isChanged=true;
                        Log.e("IP", item);
                    }else{
                        isChanged=false;
                        //If we notice any changes in IP we set the boolean to false
                        Log.e("5-1:", "IP does not match");
                    }
                }
            }else{
                Log.e("5 :", "IPList is null");
            }

            if(netAddress!= null){//If the Local IP is not empty we check the IPs for any attackers
                Log.e("6:", "Local Ip not null");
                if(!isChanged){
                    //If the boolean is false we know that the IPs do not match
                    Log.e("6-1 :", "Change in IPs noticed");
                    if(netAddress.startsWith("192.168")){//If the IPs that do not match start with the regex, the private IP. A hacker may be on the network.
                        Log.e("6-1-1 :", "Hacker Detected");
                        header.setText("Hacker Detected");
                        header.setTextColor(Color.parseColor("#EC4C33"));//Set the Color of Header to Warning Red
                        try {
                            networkState.setImageResource(R.drawable.alert);//Change the image to alert
                            Log.e("6-1-2 :", "Getting hacker info");
                            String data = new pingIP().execute(netAddress).get();//Get the hackers system name if possible
                            Log.e("6-1-2 :", "Got hacker info");
                            if(data!= null){//If the hacker data is not empty display their data to the user
                                Log.e("6-1-2-1 :", "Displaying hacker info");
                                apiText.append("\n Hacker System Name "+data);
                                apiText.append("\n Hacker IP: "+netAddress);
                            }
                        } catch (ExecutionException e) {
                            Log.e("6-1-2-2 :", "Displaying hacker info Error");
                        } catch (InterruptedException e) {
                            Log.e("6-1-2-2 :", "Displaying hacker info Error");
                        }
                    }
                    else {//WE use this because some websites like google and facebook have multiple IPs which may not match but they also do not match the regex so these IPs are safe
                        Log.e("6-2-1 :", "No Hacker Detected");
                        header.setText("No Hacker Detected");
                        apiText.setText("It is however useful to connect to a VPN in order to hide your Internet activity");
                    }

                }
                else{//Comparison showed IPs are same
                    Log.e("6-2 :", "No Hacker Detected");
                    header.setText("No Hacker Detected");
                    apiText.setText("It is however useful to connect to a VPN in order to hide your Internet activity");
                }
            }else{//If the local IP is empty then the website is invalid.
                Log.e("6 :", "Local Ip is empty");
                networkState.setImageResource(R.drawable.alert);
                apiText.setText("Please input a Valid Website");
            }



        try {
            Log.e("7 :", "Getting Local Thumbprint");
            LANThumbprint = new NetIpAdd().execute(link).get();//Retrieve the local thumbprint from the server
            Log.e("7 :", "Got Local Thumbprint");
        } catch (InterruptedException e) {
            Log.e("Local Thumbprint :", "Unable to Fetch");
        } catch (ExecutionException e) {
            Log.e("Local Thumbprint :", "Unable to Fetch");
        }

        try {
            Log.e("8 :", "Getting API Thumbprint");
            NetThumbprint = new GetUrlContentTask().execute(link).get();// Retrieve the server thumbprint through an API
            Log.e("8 :", "Got API Thumbprint");
        } catch (Exception e) {
            Log.e("API Thumbprint :", "Unable to Fetch");
        }
        if(netAddress!= null){// If the local ip is not empty which means it is a valid website and we have an ip to work with
            if(LANThumbprint!=null && NetThumbprint != null){//If we are able to retrieve both the thumbprints
                Log.e("9 :", "Thumbprints are not null");
                if(LANThumbprint.equalsIgnoreCase(NetThumbprint)){//Compare the thumbprints to see if they match
                    Log.e("9-1 :", "Thumbprints matches");
                    InfoText.setText(String.format("No Hackers on Network :The Unique Thumbprints match\n Local Network Thumbprint %s\n API Thumbprint %s", LANThumbprint, NetThumbprint));
                }
                else {
                    Log.e("9-1 :", "Thumbprints do not match");
                    if(netAddress.startsWith("192.168")){//If thumbprints do not match check if the local IP begins with the regex as some websites have different thumbprints.
                        Log.e("9-1-1 :", "Thumbprints do not match and Hacker IP");
                        header.setText("Hacker Detected");
                        header.setTextColor(Color.parseColor("#EC4C33"));
                        InfoText.setText(String.format("Changes in Certificate Noticed: Hacker Detected\n Local Network Thumbprint %s\n API Thumbprint %s", LANThumbprint, NetThumbprint));
                    }
                    else {//Thumbprints do not match and the IP doesn't as well so no hacker present.
                        Log.e("9-1-2 :", "Thumbprints do not match but no Hacker");
                        InfoText.setText(String.format("No Hackers on Network :The Thumbprints are however different\n Local Network Thumbprint %s\n API Thumbprint %s", LANThumbprint, NetThumbprint));
                    }
                }

            }
            else {//If app fails to retrieve thumbprint, which means it is unable to make a secure connection. Hacker may be on network.
                    Log.e("9-2 :", "Network may be compromised or may not have Secure connection enabled");
                    InfoText.setText(String.format("Network Maybe Compromised\n Local Network Thumbprint %s\n API Thumbprint %s", LANThumbprint, NetThumbprint));
                }
        }
        else{//Local IP doe not exist means invalid website was inputted
                Log.e("9-1 :", "Thumbprints are empty due to  Invalid Website Or may not have HTTPS");
            }




    }



}
