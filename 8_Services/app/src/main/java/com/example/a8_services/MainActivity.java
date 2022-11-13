package com.example.a8_services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartService;
    Button btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = (Button)findViewById(R.id.btnStartService);
        btnStopService = (Button)findViewById(R.id.btnStopService);

        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view == btnStartService) {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        }
        if (view == btnStopService) {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        }
    }
}