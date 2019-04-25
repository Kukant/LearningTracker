package com.ateam.learningtracker.views;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
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
import com.ateam.learningtracker.data.NotifierJob;
import com.ateam.learningtracker.data.SubjectEntity;
import com.ateam.learningtracker.data.SubjectProgress;
import com.ateam.learningtracker.data.SubsectionEntity;

import java.security.Timestamp;
import java.util.List;

import com.ateam.learningtracker.data.DataConnector;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Page";

    Button btnAddSubject;
    Button btnCreateSubject;
    Button btnEditSubject;
    Button btnProgressOverview;
    Button btnTimer;
    Button btnNewSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddSubject = findViewById(R.id.BtnAddSubject);
        btnCreateSubject = findViewById(R.id.BtnCreateSubject);
        btnEditSubject = findViewById(R.id.BtnEditSubject);
        btnProgressOverview = findViewById(R.id.BtnProgressOverview);
        btnTimer = findViewById(R.id.BtnTimer);
        btnNewSession = findViewById(R.id.BtnNewSession);

        assert btnAddSubject != null;
        assert btnEditSubject != null;
        assert btnProgressOverview != null;
        assert btnTimer != null;
        assert btnNewSession != null;
        assert btnCreateSubject != null;

        btnTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TimerActivity.class));
            }
        });

        btnProgressOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProgressOverviewActivity.class));
            }
        });

        btnNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddLearningSessionActivity.class));
            }
        });

        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddSubjectActivity.class));
            }
        });

        btnCreateSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateSubject.class));
            }
        });

        btnEditSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditSubject.class));
            }
        });

        DataConnector.initDbData();



        ComponentName componentName = new ComponentName(this, NotifierJob.class);
        JobInfo info = new JobInfo.Builder(111, componentName)
                .setPeriodic(30 * 60 * 1000)
                .setPersisted(true)
                .build();


        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Notify Scheduled ");
        } else {
            Log.d(TAG, "Notify Failed");
        }




    }



}
