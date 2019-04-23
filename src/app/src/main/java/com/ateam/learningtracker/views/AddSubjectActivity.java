package com.ateam.learningtracker.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ateam.learningtracker.R;
import com.ateam.learningtracker.data.SubjectEntity;

import java.util.List;

public class AddSubjectActivity extends AppCompatActivity {

    ListView listViewSelectSubjects;
    Button buttonAddSubjects;
    List<SubjectEntity> inactiveSubjects;
    AddSubjectActivity addSubjectActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        // setup all variables
        addSubjectActivity = this;

        listViewSelectSubjects = findViewById(R.id.listViewSelectSubjects);
        buttonAddSubjects = findViewById(R.id.BtnAddSubjects);
        assert listViewSelectSubjects != null;
        assert buttonAddSubjects != null;

        buttonAddSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this will go over all selected subjects and sets them active
                int len = listViewSelectSubjects.getCount();
                SparseBooleanArray checked = listViewSelectSubjects.getCheckedItemPositions();
                for (int i = 0; i < len; i++) {
                    if (checked.get(i)) {
                        SubjectEntity item = inactiveSubjects.get(i);
                        item.active = true;
                        item.save();
                    }
                }

                // refill the listview
                fillListView();
            }
        });

        fillListView();

    }

    /*
    This method will fill the ListView with subjects that are not currently active.
     */
    void fillListView() {
        inactiveSubjects = SubjectEntity.find(SubjectEntity.class, "active = ?", "0");
        String[] inactiveSubjectsNames = new String[inactiveSubjects.size()];
        int i = 0;
        for (SubjectEntity s: inactiveSubjects) {
            inactiveSubjectsNames[i++] = s.name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, inactiveSubjectsNames);
        listViewSelectSubjects.setAdapter(adapter);
        listViewSelectSubjects.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        if (inactiveSubjects.size() == 0) {
            TextView textViewAddSubject = findViewById(R.id.textViewAddSubject);
            textViewAddSubject.setText("No inactive subjects.");
        }
    }

}
