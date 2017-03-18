package com.example.dk.trial_alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating media player instance
        int resid = getResources().getIdentifier("lp_bs", "raw", getPackageName());
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, resid);


        BroadcastReceiver br = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context c, Intent i)
            {
                try
                {
                    mediaPlayer.start();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

        };


        registerReceiver(br, new IntentFilter("wakeywakey") );

        PendingIntent alarmIntent = PendingIntent.getBroadcast( this, 0, new Intent("wakeywakey"), 0);

        AlarmManager alarms = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);

        alarms.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 20000, alarmIntent);

        Button stop_button = (Button) findViewById(R.id.stop_button);
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
            }
        });




    }
}
