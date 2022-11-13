package com.example.a9_broadcastrecievers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MyReceiver receiver  = new MyReceiver();
    MyCustomReceiver customReceiver = new MyCustomReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Register Receiver for Built-in Event
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(receiver, filter);

        // Register Receiver for Custom Event
        IntentFilter customFilter = new IntentFilter();
        customFilter.addAction("com.example.broadcast.MY_NOTIFICATION");
        registerReceiver(customReceiver, customFilter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    public void SendMessage(View view) {

        // Broadcast Custom Event
        Intent intent = new Intent();
        intent.setAction("com.example.broadcast.MY_NOTIFICATION");
        intent.putExtra("message", "BS(IT)");
        sendBroadcast(intent);
    }
}