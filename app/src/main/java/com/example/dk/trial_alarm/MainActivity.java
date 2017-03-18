package com.example.dk.trial_alarm;

import java.util.Calendar;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private int input_hour;
    private int input_minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context activity_context = getApplicationContext();
        Log.v("Checking class", (this.getClass().toString()));

        // creating media player instance
        int resid = getResources().getIdentifier("lp_bs", "raw", getPackageName());
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, resid);

        Button stop_button = (Button) findViewById(R.id.stop_button);
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
            }
        });

        Button set_button = (Button) findViewById(R.id.set_button);
        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextHour = (EditText) findViewById(R.id.input_hour);
                input_hour = Integer.parseInt(editTextHour.getText().toString());

                EditText editTextMinute = (EditText) findViewById(R.id.input_minute);
                input_minute = Integer.parseInt(editTextMinute.getText().toString());

                Calendar c_set = Calendar.getInstance();
                c_set.set(Calendar.HOUR_OF_DAY, input_hour);
                c_set.set(Calendar.MINUTE, input_minute);

                Calendar c = Calendar.getInstance();

                long diff = c_set.getTimeInMillis() - c.getTimeInMillis();

                Log.v("input", new Long(input_hour).toString());
                Log.v("input min ", new Long(input_minute).toString());

                //Log.v("currTime", new Long(c_set.getTimeInMillis()).toString());
                ///Log.v("setTime", )
                Log.v("diff", new Long(diff/(1000*60)).toString());

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

                registerReceiver(br, new IntentFilter("alarm_intent_filter") );
                PendingIntent alarmIntent = PendingIntent.getBroadcast( activity_context, 0, new Intent("alarm_intent_filter"), 0);

                AlarmManager alarms = (AlarmManager) activity_context.getSystemService(activity_context.ALARM_SERVICE);

                alarms.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + diff, alarmIntent);
            }



        });






    }
}
