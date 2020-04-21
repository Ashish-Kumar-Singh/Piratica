package com.example.piratica;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class hacker extends AppCompatActivity {
private Context context;
private TextView information;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacker);
        context = this;
        information = findViewById(R.id.information);
        try {
            checkHosts("192.168.0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<LocalDeviceInfo> deviceInfoList  = new ArrayList<>();
        try {

            WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
            String subnet = getSubnetAddress(mWifiManager.getDhcpInfo().gateway);


            for (int i=1;i<255;i++){

                String host = subnet + "." + i;

                if (InetAddress.getByName(host).isReachable(3000)){

                    String strMacAddress = getMacAddressFromIP(host);

                    Log.w("DeviceDiscovery", "Reachable Host: " + String.valueOf(host) +" and Mac : "+strMacAddress+" is reachable!");

                    LocalDeviceInfo localDeviceInfo = new LocalDeviceInfo(host,strMacAddress);
                    deviceInfoList.add(localDeviceInfo);
                }
                else
                {
                    Log.e("DeviceDiscovery", "❌ Not Reachable Host: " + String.valueOf(host));

                }
            }

            // Print Description
            for(LocalDeviceInfo devicesInfo :deviceInfoList)
            {
                information.setText("IP : "+devicesInfo.getIp());
                information.append("Mac : "+devicesInfo.getMacAddress());
            }


        }
        catch(Exception e){
            //System.out.println(e);
        }

    }


    private String getSubnetAddress(int address)
    {
        String ipString = String.format(
                "%d.%d.%d",
                (address & 0xff),
                (address >> 8 & 0xff),
                (address >> 16 & 0xff));

        return ipString;
    }
    public String getMacAddressFromIP(@NonNull String ipFinding)
    {

        Log.i("IPScanning","Scan was started!");
        List<LocalDeviceInfo> antarDevicesInfos = new ArrayList<>();


        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ip = splitted[0];
                    String mac = splitted[3];
                    if (mac.matches("..:..:..:..:..:..")) {

                        if (ip.equalsIgnoreCase(ipFinding))
                        {
                            return mac;
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "00:00:00:00";
    }
    public void checkHosts(String subnet) throws IOException {
        int timeout=1000;
        for (int i=1;i<255;i++){
            String host=subnet + "." + i;
            if (InetAddress.getByName(host).isReachable(timeout)){
                Log.d("IPs ",host + " is reachable");
            }
        }
    }


}
