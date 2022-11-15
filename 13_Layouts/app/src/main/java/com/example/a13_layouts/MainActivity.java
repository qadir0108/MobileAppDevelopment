package com.example.a13_layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLinearLayout = (Button) findViewById(R.id.btnLinearLayout);
        btnLinearLayout.setOnClickListener(this);
        Button btnRelativeLayout = (Button) findViewById(R.id.btnRelativeLayout);
        btnRelativeLayout.setOnClickListener(this);
        Button btnTableLayout = (Button) findViewById(R.id.btnTableLayout);
        btnTableLayout.setOnClickListener(this);
        Button btnAbsoluteLayout = (Button) findViewById(R.id.btnAbsoluteLayout);
        btnAbsoluteLayout.setOnClickListener(this);
        Button btnFrameLayout = (Button) findViewById(R.id.btnFrameLayout);
        btnFrameLayout.setOnClickListener(this);
        Button btnListView = (Button) findViewById(R.id.btnListView);
        btnListView.setOnClickListener(this);
        Button btnGridView = (Button) findViewById(R.id.btnGridView);
        btnGridView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = null;
        switch (view.getId())
        {
            case R.id.btnLinearLayout:
                i = new Intent(getApplicationContext(), LinearLayoutActivity.class);
                break;
            case R.id.btnRelativeLayout:
                i = new Intent(getApplicationContext(), RelativeLayoutActivity.class);
                break;
            case R.id.btnTableLayout:
                i = new Intent(getApplicationContext(), TableLayoutActivity.class);
                break;
            case R.id.btnAbsoluteLayout:
                i = new Intent(getApplicationContext(), AbsoluteLayoutActivity.class);
                break;
            case R.id.btnFrameLayout:
                i = new Intent(getApplicationContext(), FrameLayoutActivity.class);
                break;
            case R.id.btnListView:
                i = new Intent(getApplicationContext(), LinearLayoutActivity.class);
                break;
            case R.id.btnGridView:
                i = new Intent(getApplicationContext(), LinearLayoutActivity.class);
                break;
        }
        startActivity(i);
    }
}