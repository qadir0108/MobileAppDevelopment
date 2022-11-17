package com.example.a14_controls;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class SwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        ToggleButton tglLight = (ToggleButton) findViewById(R.id.tglLight);
        Switch swLight = (Switch) findViewById(R.id.swLight);
        Button btnShow = (Button) findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "";
                message += " Toggle " + (tglLight.isChecked() ? "ON" : "OFF");
                message += " Switch " + (swLight.isChecked() ? "ON" : "OFF");
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}