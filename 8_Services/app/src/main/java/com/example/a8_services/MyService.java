package com.example.a8_services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

public class MyService extends Service {
    MediaPlayer myPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone
        myPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        Toast.makeText(this, "Ringtone playing", Toast.LENGTH_LONG).show();
        //setting loop play to true
        //this will make the ringtone continuously playing
        myPlayer.setLooping(false);
        //staring the player
        myPlayer.start();
        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Ringtone playing Stopped", Toast.LENGTH_LONG).show();
        myPlayer.stop();
    }
}