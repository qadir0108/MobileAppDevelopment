package com.example.a13_layouts;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class GridViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        GridView gvBirds = (GridView) findViewById(R.id.gvBirds);
        gvBirds.setAdapter(new ImageAdapter(this));
    }
}