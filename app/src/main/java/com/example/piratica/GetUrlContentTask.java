package com.example.piratica;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


class GetUrlContentTask extends AsyncTask<String, Integer, String> {
    //Gets the Thumbprint of website from the GRC Website
    @Override
    protected String doInBackground(String... params) {
        Element words = null;
        String fingerprint = null;
        try {
            Document doc = Jsoup.connect("https://www.grc.com/fingerprints.htm?domain="+params[0]).timeout(3000).get();
            words = doc.select("tr[class=dark]").first();
            Elements cols = words.select("td");
            fingerprint = cols.get(3).text();
            fingerprint = fingerprint.replaceAll(":", "");
        }
        catch (Exception e){
//            e.printStackTrace();
            Log.e("Thumprint from API :", "Unable to Fetch");
        }

        return fingerprint;
    }
}