package com.example.piratica;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    private Button rollButton;
    private TextView ipaddress;
    private TextView ssid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rollButton = findViewById(R.id.rollButton);
        ipaddress = findViewById(R.id.ip);
        ssid = findViewById(R.id.ssid);
        WifiManager wifiManager =(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
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
            ipaddress.setText(FormatedIpAddress2);
            rollButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    openActivity();
                }
            });

        }

        }
    public void openActivity(){
        Intent intent = new Intent(this, information.class);
        startActivity(intent);
    }
}
