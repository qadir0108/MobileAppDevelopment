package com.example.a13_layouts;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        String[] countryArray = {"India", "Pakistan", "USA", "UK"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countryArray);
        ListView lvCountries = (ListView) findViewById(R.id.lvCountries);
        lvCountries.setAdapter(adapter);
    }
}