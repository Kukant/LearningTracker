package com.ateam.learningtracker.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ateam.learningtracker.R;
import com.ateam.learningtracker.data.DataConnector;
import com.ateam.learningtracker.data.LearningSessionEntity;
import com.ateam.learningtracker.data.SubjectEntity;
import com.ateam.learningtracker.data.SubsectionEntity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddLearningSessionActivity extends AppCompatActivity {
    AddLearningSessionActivity addLearningSessionActivity;
    Button submitButton;
    Spinner spinnerSubject;
    Spinner spinnerSubsection;
    TimePicker timePickerStart;
    TimePicker timePickerEnd;

    AdapterView.OnItemSelectedListener subjectSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            addLearningSessionActivity.updateSubsectionsSpinner(parent.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_learning_session);

        addLearningSessionActivity = this;

        // prepare the subject spinner
        List<SubjectEntity> allSubjects = SubjectEntity.listAll(SubjectEntity.class);
        String[] subjects = new String[allSubjects.size()];
        int i = 0;
        for (SubjectEntity s: allSubjects) {
            subjects[i++] = s.name;
        }

        spinnerSubject = findViewById(R.id.SpinnerSubject);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(adapter);
        spinnerSubject.setOnItemSelectedListener(subjectSelectListener);

        submitButton = findViewById(R.id.btnSaveSession);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLearningSessionActivity.saveSession();
                finish();
            }
        });

        spinnerSubsection = findViewById(R.id.SpinnerSubsection);
        timePickerStart = findViewById(R.id.timePickerStart);
        timePickerEnd = findViewById(R.id.timePickerEnd);
    }

    private void saveSession() {
        long timeStart, timeEnd;
        SubjectEntity subject = DataConnector.getSubjectByName(spinnerSubject.getSelectedItem().toString());
        assert subject != null;

        SubsectionEntity subsection = DataConnector.getSubsectionByName(spinnerSubsection.getSelectedItem().toString(), subject);
        assert subsection != null;

        Calendar rightNow = Calendar.getInstance();
        long timestampBase = System.currentTimeMillis() / 1000;

        timestampBase -= (rightNow.get(Calendar.HOUR_OF_DAY) * 3600 + rightNow.get(Calendar.MINUTE) * 60);

        timeStart = timePickerStart.getCurrentHour() * 3600 + timePickerStart.getCurrentMinute() * 60 + timestampBase;
        timeEnd = timePickerEnd.getCurrentHour() * 3600 + timePickerEnd.getCurrentMinute() * 60 + timestampBase;


        LearningSessionEntity session = new LearningSessionEntity(timeStart, timeEnd, "", subsection);
        session.save();

        Toast.makeText(this, "Session saved!", Toast.LENGTH_LONG).show();
    }

    private void updateSubsectionsSpinner(String selectedSubject) {
        SubjectEntity subject = DataConnector.getSubjectByName(selectedSubject);
        assert subject != null;

        List<SubsectionEntity> allSubjectSubs = SubsectionEntity.find(SubsectionEntity.class, "subject = ?", subject.getId().toString());
        String[] subsections = new String[allSubjectSubs.size()];
        int i = 0;
        for (SubsectionEntity s: allSubjectSubs) {
            subsections[i++] = s.name;
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item, subsections);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubsection.setAdapter(adapter);
    }
}
