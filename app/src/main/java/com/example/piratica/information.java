package com.example.piratica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class information extends AppCompatActivity {
    private TextView InfoText;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        InfoText = findViewById(R.id.InfoText);
        String netAddress = null;
        String link = "dit.ie";
        try
        {
            netAddress = new NetTask().execute(link).get();
            InfoText.setText(netAddress);
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        String url = "https://www.whoisxmlapi.com/whoisserver/DNSService?apiKey=at_nZNsncxr1W3JtbG0qAiYFF1RVtv6I&domainName="+link+"&type=1&outputFormat=JSON";

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
                    final String myResponse = response.body().string();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(myResponse);
                        JSONObject dnsData = jsonObject.optJSONObject("DNSData");
                        JSONArray dnsRecords = dnsData.getJSONArray("dnsRecords");
                            JSONObject objects = dnsRecords.getJSONObject(0);
                            Log.d("IP", objects.getString("address"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
}
