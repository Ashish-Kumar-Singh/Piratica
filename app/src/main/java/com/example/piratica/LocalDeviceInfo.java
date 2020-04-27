package com.example.piratica;

public class LocalDeviceInfo {
    String Ip;
    String HostName;

    public LocalDeviceInfo(String ip, String hostname) {
        Ip = ip;
        HostName = hostname;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String hostName) {
        HostName = hostName;
    }
}
