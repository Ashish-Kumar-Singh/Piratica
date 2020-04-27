package com.example.piratica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SplashScreen extends AppCompatActivity {
    public  Map<String, String> check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try {
            check = new getAllIp().execute("192.168.86").get();
            for (Map.Entry<String, String> entry : check.entrySet()) {
                Log.e("key",entry.getKey() + " = " + entry.getValue());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
