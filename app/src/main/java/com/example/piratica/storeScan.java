package com.example.piratica;

@SuppressWarnings("NullableProblems")
class storeScan {
    private String website;
    private String result;
    private String HackerName;
    private String HackerIp;
    private String network;
    private int id;

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

    private String getStatus(){
        return "Id :"+id+" Network Name: "+network+" Website Name: "+website+" Scan Result: "+result;
    }
    public String getHackerInfo(){
        return "System Name: "+HackerName+" Hacker IP: "+HackerIp;
    }
    @Override
    public String toString(){
       return this.getStatus(); //Just an example ;)
    }
}
