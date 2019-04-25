package com.ateam.learningtracker.views;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import com.ateam.learningtracker.R;
import com.ateam.learningtracker.data.SubjectEntity;

public class CreateSubject extends AppCompatActivity {

    Button createButton;
    EditText name, studyTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_subject);

        createButton = (Button)findViewById(R.id.createButton);
        name = (EditText) findViewById(R.id.name);
        studyTime = (EditText) findViewById(R.id.studyTime);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SubjectEntity subjectEntity = new SubjectEntity(name, studyTime);
                //subjectEntity.save();
            }
        });



    }

}
