package com.example.a19_notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowNotification = (Button)findViewById(R.id.btnShowNotification);
        btnShowNotification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        Button btnCancelNotification = (Button)findViewById(R.id.btnCancelNotification);
        btnShowNotification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        Button btnUpdateNotification = (Button)findViewById(R.id.btnUpdateNotification);
        btnShowNotification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });
    }
}