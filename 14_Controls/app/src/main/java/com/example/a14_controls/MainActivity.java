package com.example.a14_controls;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTextView = (Button) findViewById(R.id.btnTextView);
        btnTextView.setOnClickListener(this);
        Button btnEditText = (Button) findViewById(R.id.btnEditText);
        btnEditText.setOnClickListener(this);
        Button btnCheckBox = (Button) findViewById(R.id.btnCheckBox);
        btnCheckBox.setOnClickListener(this);
        Button btnRadioButton = (Button) findViewById(R.id.btnRadioButton);
        btnRadioButton.setOnClickListener(this);
        Button btnSwitch = (Button) findViewById(R.id.btnSwitch);
        btnSwitch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = null;
        switch (view.getId())
        {
            case R.id.btnTextView:
                i = new Intent(getApplicationContext(), TextViewActivity.class);
                break;
            case R.id.btnEditText:
                i = new Intent(getApplicationContext(), EditTextActivity.class);
                break;
            case R.id.btnCheckBox:
                i = new Intent(getApplicationContext(), CheckBoxActivity.class);
                break;
            case R.id.btnRadioButton:
                i = new Intent(getApplicationContext(), RadioButtonActivity.class);
                break;
            case R.id.btnSwitch:
                i = new Intent(getApplicationContext(), SwitchActivity.class);
                break;
        }
        startActivity(i);
    }
}