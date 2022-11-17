package com.example.a8_services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStartService;
    Button btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MyService.class);

        btnStartService = (Button)findViewById(R.id.btnStartService);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startService(intent);
            }
        });

        btnStopService = (Button)findViewById(R.id.btnStopService);
        btnStopService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopService(intent);
            }
        });
    }
}