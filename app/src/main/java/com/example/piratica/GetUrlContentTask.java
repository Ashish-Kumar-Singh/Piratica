package com.example.piratica;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class GetUrlContentTask extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... params) {
        Element words = null;
        String fingerprint = null;
        try {
            Document doc = Jsoup.connect("https://www.grc.com/fingerprints.htm?domain="+params[0]).get();
            words = doc.select("tr[class=dark]").first();
            Elements cols = words.select("td");
            fingerprint = cols.get(3).text();
            fingerprint = fingerprint.replaceAll(":", "");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return fingerprint;
    }
}