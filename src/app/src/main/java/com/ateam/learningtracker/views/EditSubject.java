package com.ateam.learningtracker.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ateam.learningtracker.R;

public class EditSubject extends AppCompatActivity {
    Button updateButton;
    EditText name, studyTime, subsection;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_subject);

        updateButton = (Button)findViewById(R.id.createButton);
        name = (EditText) findViewById(R.id.name);
        studyTime = (EditText) findViewById(R.id.studyTime);
        subsection = (EditText) findViewById(R.id.subsection);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SubjectEntity subjectEntity = new SubjectEntity(name, studyTime);
                //subjectEntity.save();
            }
        });



    }
}
