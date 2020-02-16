package com.example.piratica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class information extends AppCompatActivity {
    private TextView InfoText;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        InfoText = findViewById(R.id.InfoText);
        NetIpAdd NetIp = new NetIpAdd();
        try {
            text = NetIp.CheckIp("www.dit.ie");
            InfoText.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
