package com.example.a25_alertdialogs;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String parameterName = getIntent().getStringExtra("name");

        TextView txtName = (TextView) findViewById(R.id.txtName);
        txtName.setText("Welcome " + parameterName);
    }
}