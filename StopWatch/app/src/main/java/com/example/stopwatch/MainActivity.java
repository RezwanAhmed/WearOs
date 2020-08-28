package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private Button start, stop, reset;

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            seconds = savedInstanceState
                    .getInt("seconds");
            running = savedInstanceState
                    .getBoolean("running");
            wasRunning = savedInstanceState
                    .getBoolean("wasRunning");
        }
        runTimer();

        setAmbientEnabled();
    }

    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState)
    {
        savedInstanceState
                .putInt("seconds", seconds);
        savedInstanceState
                .putBoolean("running", running);
        savedInstanceState
                .putBoolean("wasRunning", wasRunning);
    }
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }
    protected void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }
    public void onClickStart(View view)
    {
        running = true;
    }
    public void onClickStop(View view)
    {
        running = false;
    }
    public void onClickReset(View view)
    {
        running = false;
        seconds = 0;
    }
    private void runTimer() {
        final TextView showTime = (TextView)findViewById(R.id.showTime);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 36000;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours,
                        minutes, secs);
                showTime.setText(time);

                if(running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });

    }
}
