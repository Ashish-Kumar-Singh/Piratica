package com.example.piratica;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class NetIpAdd {
    public void CheckIp(String DomainName){
        String host = DomainName;
        OkHttpClient client = new OkHttpClient();
        String url = "https://www.whoisxmlapi.com/whoisserver/DNSService?apiKey=at_nZNsncxr1W3JtbG0qAiYFF1RVtv6I&domainName="+DomainName+"&type=1";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful()){
                        String myResponse = response.body().string();
                    }
                }
            });
        }
    }


