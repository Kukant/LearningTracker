package com.ateam.learningtracker.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ateam.learningtracker.R;
import com.ateam.learningtracker.data.DataConnector;
import com.ateam.learningtracker.data.SubjectProgress;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ProgressOverviewActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressoverview_main);

        setupUIViews();
        setupListView();
    }

    private void setupUIViews() {
        listView = findViewById(R.id.lvMain);
        assert listView != null;
    }

    private void setupListView() {
        List<SubjectProgress> progressInfo = DataConnector.getSubjectsProgressInfo();

        ArrayList<String> subjects = new ArrayList<>();
        ArrayList<String> percentage = new ArrayList<>();

        for (SubjectProgress sp: progressInfo) {
            subjects.add(sp.name);
            percentage.add(String.format(Locale.ENGLISH,"%d", (int) (sp.overallProgress * 100)));
        }


        SimpleAdapter simpleAdapter = new SimpleAdapter(this, subjects, percentage);
        listView.setAdapter(simpleAdapter);

    }

    public class SimpleAdapter extends BaseAdapter{

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView subject,percentage;
        private ArrayList<String>  subjectArray;
        private ArrayList<String>  percentageArray;

        public SimpleAdapter(Context context, ArrayList<String> subject, ArrayList<String> percentage) {
            mContext = context;
            subjectArray = subject;
            percentageArray = percentage;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return subjectArray.size();
        }

        @Override
        public Object getItem(int position) {
            return subjectArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.progressoverview_single_item, null);
            }

            //hard codes the subjects and percentages into the cardview from the string arrays in string.xml
            subject = convertView.findViewById(R.id.tvMain);
            percentage = convertView.findViewById(R.id.tvPercentage);

            ProgressBar pb = convertView.findViewById(R.id.progressBar);
            pb.setProgress(Integer.valueOf(percentageArray.get(position)));

            subject.setText(subjectArray.get(position));
            percentage.setText(String.format("%s %%", percentageArray.get(position)));

            return convertView;
        }


    }

}