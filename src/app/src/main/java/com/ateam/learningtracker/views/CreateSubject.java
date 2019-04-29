package com.ateam.learningtracker.views;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Toast;

import com.ateam.learningtracker.R;
import com.ateam.learningtracker.data.SubjectEntity;

public class CreateSubject extends AppCompatActivity {

    Button createNewSubjectButton;
    EditText ETSubjectName, ETStudyTime;

    private void saveData(String newSubjectName, int newStudyTime) {
        //Enter data into the db
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_subject);

        createNewSubjectButton = findViewById(R.id.createNewSubjectButton);
        ETSubjectName = findViewById(R.id.newSubjectName);
        ETStudyTime = findViewById(R.id.newStudyTime);

        createNewSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SubjectEntity subjectEntity = new SubjectEntity(name, studyTime);
                //subjectEntity.save();


                String newSubjectName = ETSubjectName.getText().toString();

                if(newSubjectName.equals("")) {
                    Toast.makeText(CreateSubject.this,"Try Setting A New Subject Name",Toast.LENGTH_SHORT).show();
                }
                else {
                    try{
                        int newStudyTime = Integer.parseInt(ETStudyTime.getText().toString());
                        saveData(newSubjectName,newStudyTime);
                    }
                    catch (NumberFormatException e) {
                        Toast.makeText(CreateSubject.this,"Try Setting A Total Number of Study Hours",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}