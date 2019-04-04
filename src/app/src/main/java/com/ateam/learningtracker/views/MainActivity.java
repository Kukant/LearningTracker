package com.ateam.learningtracker.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ateam.learningtracker.R;
import com.ateam.learningtracker.data.LearningSessionEntity;
import com.ateam.learningtracker.data.SubjectEntity;
import com.ateam.learningtracker.data.SubsectionEntity;

import java.security.Timestamp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
