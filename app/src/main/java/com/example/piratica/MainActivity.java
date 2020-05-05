package com.example.piratica;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import jcifs.netbios.NbtAddress;


public class MainActivity extends AppCompatActivity {
    private Button rollButton;
    private TextView ipaddress;
    private Button scanButton;
    private  TextView ipstuff;
    private TextView ssid;
    private EditText website;
    public  Map<String, String> check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return;
        }else{
            rollButton = findViewById(R.id.rollButton);
            scanButton = findViewById(R.id.scanButton);
            ipaddress = findViewById(R.id.ip);
            ssid = findViewById(R.id.ssid);
            website = (EditText) findViewById(R.id.userInput);
            WifiManager wifiManager =(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            final WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            if (wifiInfo == null) {

            } else {
                if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
                    ssid.setText("SSID: " + wifiInfo.getSSID());
                }
                int ipAddress = wifiInfo.getIpAddress();
                String FormatedIpAddress2 = String.format("%d.%d.%d.%d",
                        (ipAddress & 0xff),
                        (ipAddress >> 8 & 0xff),
                        (ipAddress >> 16 & 0xff),
                        (ipAddress >> 24 & 0xff));
                ipaddress.setText("IP: " +FormatedIpAddress2);
                rollButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String user = website.getText().toString();
                        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
                        if(user.isEmpty() || !user.matches(EMAIL_PATTERN)){
                            Toast.makeText(getApplicationContext(),"User Input Required in website format",Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e("input", user);
                            Intent intent = new Intent(getApplicationContext(), information.class);
                            intent.putExtra("user_input",user);
                            intent.putExtra("ssid", wifiInfo.getSSID());
                            startActivity(intent);
                        }
                    }
                });
                scanButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), savedScans.class);
                        startActivity(intent);
                    }
                });
            }
        }


        }


}
