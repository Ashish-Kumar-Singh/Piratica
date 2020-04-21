package com.example.piratica;

public class LocalDeviceInfo {
    String Ip;
    String MacAddress;

    public LocalDeviceInfo(String ip, String macAddress) {
        Ip = ip;
        MacAddress = macAddress;
    }

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getMacAddress() {
        return MacAddress;
    }

    public void setMacAddress(String macAddress) {
        MacAddress = macAddress;
    }
}
