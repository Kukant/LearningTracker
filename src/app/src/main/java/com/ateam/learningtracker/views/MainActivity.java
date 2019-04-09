package com.ateam.learningtracker.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.ateam.learningtracker.R;
import com.ateam.learningtracker.data.LearningSessionEntity;
import com.ateam.learningtracker.data.SubjectEntity;
import com.ateam.learningtracker.data.SubsectionEntity;

import java.security.Timestamp;

public class MainActivity extends AppCompatActivity {

    Button btnAddSubject;
    Button btnEditSubject;
    Button btnProgressOverview;
    Button btnTimer;
    Button btnNewSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddSubject = findViewById(R.id.BtnAddSubject);
        btnEditSubject = findViewById(R.id.BtnEditSubject);
        btnProgressOverview = findViewById(R.id.BtnProgressOverview);
        btnTimer = findViewById(R.id.BtnTimer);
        btnNewSession = findViewById(R.id.BtnNewSession);

        assert btnAddSubject != null;
        assert btnEditSubject != null;
        assert btnProgressOverview != null;
        assert btnTimer != null;
        assert btnNewSession != null;

        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TimerActivity.class));
            }
        });

        // DB access example

        SubjectEntity subject = new SubjectEntity("Maths", 30);
        subject.save();

        SubsectionEntity subsection = new SubsectionEntity("logarithms", 0.7f, subject);
        subsection.save();

        long timestamp = System.currentTimeMillis() / 1000;
        LearningSessionEntity session = new LearningSessionEntity(timestamp - 3600, timestamp, "nope,", subsection);
        session.save();


        LearningSessionEntity s = LearningSessionEntity.findById(LearningSessionEntity.class, 1L);
        Log.v("bla", s.note);
        // DB access example end
    }

}