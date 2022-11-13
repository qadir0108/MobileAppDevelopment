package com.example.a9_broadcastrecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

// https://developer.android.com/guide/components/broadcasts
public class MyCustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Custom Message Received.", Toast.LENGTH_LONG).show();

        String message = intent.getStringExtra("message");
        Toast.makeText(context, "Message:" + message, Toast.LENGTH_LONG).show();

    }

}