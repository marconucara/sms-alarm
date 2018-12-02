package com.marconucara.smsalarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.media.AudioManager;
import android.content.Context;

public class BackgroundSoundService extends Service {

    private static final String TAG = "BackgroundSoundService";
    MediaPlayer player;
    AudioManager audioManager;
    Integer previousVolume;


    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "onBind()" );
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        player = MediaPlayer.create(this, R.raw.alarm);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);

    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();


        previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        /*for(int i = 0; i < 10; i++) {
            audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 10 * (i + 1),
                    0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
        */

        return Service.START_STICKY;
    }

    public IBinder onUnBind(Intent arg0) {
        Log.i(TAG, "onUnBind()");
        return null;
    }

    public void onStop() {
        audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                previousVolume,
                0);
    }
    public void onPause() {
        Log.i(TAG, "onPause()");
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        Toast.makeText(this, "Service stopped...", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onCreate() , service stopped...");
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG, "onLowMemory()");
    }
}
