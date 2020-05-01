package com.example.piratica;

public class storeScan {
    public String website;
    public String result;
    public String HackerName;
    public String HackerIp;
    public String network;
    public int id;

    public storeScan() {

    }

    public storeScan(Integer id,String website, String result, String hackerName, String hackerIp, String network) {
        this.id =id;
        this.website = website;
        this.result = result;
        HackerName = hackerName;
        HackerIp = hackerIp;
        this.network = network;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getHackerName() {
        return HackerName;
    }

    public void setHackerName(String hackerName) {
        HackerName = hackerName;
    }

    public String getHackerIp() {
        return HackerIp;
    }

    public void setHackerIp(String hackerIp) {
        HackerIp = hackerIp;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getStatus(){
        return "Id :"+id+" Network Name: "+network+" Website Name: "+website+" Scan Result: "+result;
    }
    public String getHackerInfo(){
        return "Id :"+id+"System Name: "+HackerName+" Hacker IP: "+HackerIp;
    }
}
