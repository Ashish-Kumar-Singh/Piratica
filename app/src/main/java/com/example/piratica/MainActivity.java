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
    private  TextView ipstuff;
    private TextView ssid;
    private EditText website;
    public  Map<String, String> check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        deleteCache(getApplicationContext());
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
                ipaddress.setText("IP: " +FormatedIpAddress2);
//                try {
//                   check = new getAllIp().execute("192.168.86").get();
//                    for (Map.Entry<String, String> entry : check.entrySet()) {
//                        Log.e("key",entry.getKey() + " = " + entry.getValue());
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
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
                            startActivity(intent);
                        }
                    }
                });
            }
        }


        }
        public void deleteCache(Context context) {
            try {
                File dir = context.getCacheDir();
                deleteDir(dir);
            } catch (Exception e) { e.printStackTrace();}
        }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

}
