package com.ateam.learningtracker.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.ateam.learningtracker.R;

public class TimerActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    private int seconds;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_main);

        saveButton = findViewById(R.id.logAcivityButton);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 1000) {
                    seconds++;
                }
            }
        });
    }

    public void startChronomter(View v) {
        if(!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronomter(View v) {
        if(running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronomter(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = seconds = 0;
    }

    public void logActivity(View v) {
        //prevents multiple AddLearningSessionActivity sessions being started from the TimerActivity
        saveButton.setEnabled(false);
        saveButton.setClickable(false);
        startActivity(new Intent(TimerActivity.this, AddLearningSessionActivity.class).putExtra("secondsElapsed", seconds));
        finish();
    }
}