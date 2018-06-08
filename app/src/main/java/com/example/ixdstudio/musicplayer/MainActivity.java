package com.example.ixdstudio.musicplayer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView defaultTextView;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    int[] songs;
    MediaPlayer mp;
    int currentID = 0;
    ASyncTime aSyncTimeBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songs = new int[] {R.raw.sang2, R.raw.sang3, R.raw.ghostsnstuff};

        musicPlayer();
        Log.e("Array size", Integer.toString(songs.length));
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        updateDisplay();
    }

    private void updateDisplay() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                defaultTextView = (TextView) findViewById(R.id.textView);
                timeSetter(defaultTextView);

            }

        },0,1000);//Update text every second
    }

    public void timeSetter(View view) {
        int i = 0;

//        while (i < 10) {
            aSyncTimeBackground = new ASyncTime();
            aSyncTimeBackground.execute(defaultTextView);
//            while(!aSyncTimeBackground.isCancelled()) {
//                }
//            aSyncTimeBackground.cancel(true);
        //           i++;
        //}
    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
        if(mp.isPlaying()) {mp.stop();}
    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void musicPlayer() {
        mp = MediaPlayer.create(this, songs[0]);
        mp.start();
    }

    public void startTime (View view) {
        defaultTextView = (TextView) findViewById(R.id.textView);
        timeSetter(defaultTextView);
    }

    public void playPause(View view) {
        Button button = (Button) findViewById(R.id.button2);
        if(mp.isPlaying()) {
            mp.pause();
            button.setText("Paused");
            return;
        }
        if(!mp.isPlaying()) {
            mp.start();
            button.setText("Playing");
            return;
        }
    }

    public void nextSong(View view) {
        if(currentID < (songs.length - 1)) {
            currentID++;
            mp.stop();
            mp = MediaPlayer.create(this, songs[currentID]);
            mp.start();
        }
    }

    public void lastSong(View view) {
        if(currentID > 0 ) {
            currentID--;
            mp.stop();
            mp = MediaPlayer.create(this, songs[currentID]);
            mp.start();
        }
    }

    public void nextingSong() {
        if(currentID < (songs.length - 1)) {
            currentID++;
            mp.stop();
            mp = MediaPlayer.create(this, songs[currentID]);
            mp.start();
        }
        else {
            currentID = 0;
            mp.stop();
            mp = MediaPlayer.create(this, songs[currentID]);
            mp.start();
        }
    }

    long lastUpdate = 0;

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float NOISE = (float) 2.0;

        float xAxis = event.values[0];
        float yAxis = event.values[1];
        float zAxis = event.values[2];
        long actualTime = System.currentTimeMillis();

        if ((actualTime - lastUpdate) > 400) {

            if (xAxis > 6) { nextingSong();}

            Log.e("Movement on X", Float.toString(xAxis));
            Log.e("Movement on Y", Float.toString(yAxis));
            Log.e("Movement on Z", Float.toString(zAxis));

            lastUpdate = actualTime;
        }
    }
}


