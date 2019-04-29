package com.ateam.learningtracker.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ateam.learningtracker.R;
import com.ateam.learningtracker.data.SubjectEntity;

import java.util.List;

public class EditSubject extends AppCompatActivity {
    Button editSubjectButton;
    EditText editTextSubjectName, editTextStudyTime, editTextAddSubsection;
    Spinner spinnerSubject;
    String selectedSubject;

    AdapterView.OnItemSelectedListener subjectSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //To be used when entering data to db
            selectedSubject = parent.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}

    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_subject);

        editTextSubjectName = findViewById(R.id.editTextSubjectName);
        editTextStudyTime = findViewById(R.id.editTextStudyTime);
        editTextAddSubsection = findViewById(R.id.editTextAddSubsection);
        editSubjectButton = findViewById(R.id.editSubjectButton);

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

        editSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SubjectEntity subjectEntity = new SubjectEntity(name, studyTime);
                //subjectEntity.save();

                String newSubjectName = editTextSubjectName.getText().toString();
                if(newSubjectName.equals("") /*Not sure if it will be saved as null or empty string, test this with db*/) {
                    //save new name to db
                }
                else {
                    //dont change name in the db
                }

                try{
                    int newTime = Integer.parseInt(editTextStudyTime.getText().toString());
                    //change the total study time in the db
                }
                catch (NumberFormatException e) {
                    //don't change total study time
                }

                String newSubsectionName = editTextAddSubsection.getText().toString();
                if(!newSubsectionName.equals("") /*Not sure if it will be saved as null or empty string, test this with db*/) {
                    //save new subsection to db
                }
                else {
                    //dont add any subsction in the db
                }
            }
        });
    }
}