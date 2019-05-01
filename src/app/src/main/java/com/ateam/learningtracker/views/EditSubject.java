package com.ateam.learningtracker.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ateam.learningtracker.R;
import com.ateam.learningtracker.data.DataConnector;
import com.ateam.learningtracker.data.SubjectEntity;
import com.ateam.learningtracker.data.SubsectionEntity;

import java.util.List;

public class EditSubject extends AppCompatActivity {
    Button editSubjectButton, deleteSubsectionButton, addSubsectionButton;
    EditText editTextSubjectName, editTextStudyTime, editTextAddSubsection, editTextSubImportancy;
    Spinner spinnerSubject;
    Spinner spinnerSubsections;
    SubjectEntity selectedSubject;
    EditSubject that;
    ToggleButton toggleButton;

    AdapterView.OnItemSelectedListener subjectSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //To be used when entering data to db
            String selectedSubjectName = parent.getSelectedItem().toString();
            selectedSubject = DataConnector.getSubjectByName(selectedSubjectName);
            assert  selectedSubject != null;
            editTextSubjectName.setText(selectedSubject.name);
            editTextStudyTime.setText(String.valueOf(selectedSubject.studyTime));

            setupSubsections();

            toggleButton.setChecked(selectedSubject.active);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_subject);

        that = this;
        selectedSubject = null;
        editTextSubjectName = findViewById(R.id.editTextSubjectName);
        editTextStudyTime = findViewById(R.id.editTextStudyTime);
        editTextAddSubsection = findViewById(R.id.editTextAddSubsection);
        editTextSubImportancy = findViewById(R.id.editTextSubImportancy);
        editSubjectButton = findViewById(R.id.editSubjectButton);
        spinnerSubsections = findViewById(R.id.spinnerSubsections);
        deleteSubsectionButton = findViewById(R.id.deleteSubsectionButton);
        addSubsectionButton = findViewById(R.id.addSubsectionButton);
        toggleButton = findViewById(R.id.togglebutton);


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
                if (selectedSubject == null) {
                    Toast.makeText(getApplicationContext(), "Please select a subject.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String newSubjectName = editTextSubjectName.getText().toString();
                if (newSubjectName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Subject needs to have a name.", Toast.LENGTH_SHORT).show();
                } else if (!newSubjectName.equals(selectedSubject.name) && DataConnector.getSubjectByName(newSubjectName) != null) {
                    Toast.makeText(getApplicationContext(), "Subject with this name already exists.", Toast.LENGTH_SHORT).show();
                } else if (editTextStudyTime.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Subject needs to have study time.", Toast.LENGTH_SHORT).show();
                } else {
                    selectedSubject.name = editTextSubjectName.getText().toString();
                    selectedSubject.studyTime = Integer.parseInt(editTextStudyTime.getText().toString());
                    selectedSubject.save();

                    Toast.makeText(getApplicationContext(), "Subject saved.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        addSubsectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSubject == null) {
                    Toast.makeText(getApplicationContext(), "Please select a subject.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String newSubName = editTextAddSubsection.getText().toString();

                if (newSubName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Subsection needs to have a name.", Toast.LENGTH_SHORT).show();
                } else if (editTextSubImportancy.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Subsection needs an importancy.", Toast.LENGTH_SHORT).show();
                } else {
                    float importancy = ((float) Integer.parseInt(editTextSubImportancy.getText().toString())) / 100f;
                    SubsectionEntity newSub = new SubsectionEntity(newSubName, importancy, selectedSubject);
                    newSub.save();

                    Toast.makeText(getApplicationContext(), String.format("Subsection %s created.", newSubName), Toast.LENGTH_SHORT).show();

                    editTextSubImportancy.setText("");
                    editTextAddSubsection.setText("");
                    setupSubsections();
                }

            }
        });

        deleteSubsectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSubject == null) {
                    Toast.makeText(getApplicationContext(), "Please select a subject.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String selectedSubsection = spinnerSubsections.getSelectedItem().toString();
                SubsectionEntity sub =  DataConnector.getSubsectionByName(selectedSubsection, selectedSubject);
                assert sub != null;
                sub.delete();

                Toast.makeText(getApplicationContext(), String.format("Subsection %s deleted.", selectedSubsection), Toast.LENGTH_SHORT).show();

                setupSubsections();
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selectedSubject == null) {
                    Toast.makeText(getApplicationContext(), "Please select a subject.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String message = "Subject was set to " + (isChecked ? "active." : "inactive.");
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                selectedSubject.active = isChecked;
                selectedSubject.save();
            }
        });




    }

    void setupSubsections() {
        // prepare the subsection spinner
        List<SubsectionEntity> allSubsections = SubsectionEntity.find(SubsectionEntity.class, "subject = ?", selectedSubject.getId().toString());
        String[] subsections = new String[allSubsections.size()];
        int i = 0;
        for (SubsectionEntity s: allSubsections) {
            subsections[i++] = s.name;
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(that, android.R.layout.simple_spinner_item, subsections);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubsections.setAdapter(adapter);
    }
}