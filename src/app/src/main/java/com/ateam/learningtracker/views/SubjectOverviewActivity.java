package com.ateam.learningtracker.views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
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

    ArrayList<String> subsectionsArray = new ArrayList<>();
    ArrayList<String> subpresentageArray = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjectoverview);
        //calling methods
        subjectName = getIntent().getStringExtra("subjectName");

        setupUIView(subjectName);
        setListView();
    }

    private void setupUIView(String subjectName){
        textView = findViewById(R.id.tvSubjectName);
        listView = findViewById(R.id.lvSubject);

        textView.setText(subjectName);
        assert listView != null;
    }

    private void setListView() {

        //TEST ARRAYS
        subsectionsArray.add("Oral");
        subsectionsArray.add("Writing");
        subsectionsArray.add("Listening");

        subpresentageArray.add(String.format(Locale.ENGLISH, "%d", (int) (20)));
        subpresentageArray.add(String.format(Locale.ENGLISH, "%d", (int) (30)));
        subpresentageArray.add(String.format(Locale.ENGLISH, "%d", (int) (40)));

       /*
        FOR LOOP FOR FETCHING DATA

        for (SubsectionProgress ssp : subprogressInfo) {
            subsections.add(subsectionName);
            subpresentage.add(String.format(Locale.ENGLISH, "%d", (int) (ssp.subsectionProgress * 100)));
        }
        */

        SimpleAdapter simAdapter = new SimpleAdapter(this, subsectionsArray, subpresentageArray);
        listView.setAdapter(simAdapter);

    }

    public class SimpleAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView subsection, subpercentage;
        private ArrayList<String> subpercentageArray;

        public SimpleAdapter(Context context, ArrayList<String> subsections, ArrayList<String> subpercentage) {
            mContext = context;
            subsectionsArray = subsections;
            subpercentageArray = subpercentage;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return subsectionsArray.size();
        }

        @Override
        public Object getItem(int position) {
            return subsectionsArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.subjectoverview_single, null);
            }
            subsection = convertView.findViewById(R.id.tvSubsectionName);
            subpercentage = convertView.findViewById(R.id.tvSubPercentage);

            ProgressBar pb = convertView.findViewById(R.id.SubProgressBar);
            pb.setProgress(Integer.valueOf(subpercentageArray.get(position)));

            subsection.setText(subsectionsArray.get(position));
            subpercentage.setText(String.format("%s %%", subpercentageArray.get(position)));

            return convertView;

        }
    }

}
