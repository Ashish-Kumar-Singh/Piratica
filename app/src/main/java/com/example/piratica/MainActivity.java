package com.example.piratica;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jcifs.netbios.NbtAddress;


public class MainActivity extends AppCompatActivity {
    private Button rollButton;
    private TextView ipaddress;
    private  TextView ipstuff;
    private TextView ssid;
    private EditText website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rollButton = findViewById(R.id.rollButton);
        ipaddress = findViewById(R.id.ip);
        ssid = findViewById(R.id.ssid);
        website = (EditText) findViewById(R.id.userInput);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }else{

            // Write you code here if permission already given.
//            getInfo gInfo = new getInfo();
//            String mac = gInfo.getMacAddressFromIP("192.168.0.52");
//            website.setText(mac);
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
                    public void onClick(View v) {
                        String user = website.getText().toString();
                        Log.e("input", user);
                        Intent intent = new Intent(getApplicationContext(), information.class);
                        intent.putExtra("user_input",user);
                        startActivity(intent);
                    }
                });
            }
        }


        }
    public void openActivity(){
        Intent intent = new Intent(this, information.class);
        startActivity(intent);

    }
}
