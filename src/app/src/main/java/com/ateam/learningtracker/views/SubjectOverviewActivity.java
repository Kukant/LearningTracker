package com.ateam.learningtracker.views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ateam.learningtracker.R;
import com.ateam.learningtracker.data.DataConnector;
import com.ateam.learningtracker.data.SubjectProgress;
import com.ateam.learningtracker.data.SubsectionProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SubjectOverviewActivity extends AppCompatActivity {

    private ListView listView;
    private TextView textView;
    private String subjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjectoverview);
        //calling methods
        subjectName = getIntent().getStringExtra("subjectName");

        setupUIView(subjectName);
    }
    //connect to view
    private void setupUIView(String subjectName){
        textView = findViewById(R.id.tvSubjectName);
        listView = findViewById(R.id.lvSubject);

        textView.setText(subjectName);
        assert listView != null;
    }
}
