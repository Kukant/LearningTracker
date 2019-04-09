package com.ateam.learningtracker.views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ateam.learningtracker.R;

public class ProgressOverviewActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUIViews();
        setupListView();
    }

    private void setupUIViews() {
        listView = (ListView) findViewById(R.id.lvMain);
    }

    private void setupListView() {

        String[] subject = getResources().getStringArray(R.array.Subjects);
        String[] percentage = getResources().getStringArray(R.array.Percentage);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, subject, percentage);
        listView.setAdapter(simpleAdapter);
    }

    public class SimpleAdapter extends BaseAdapter{

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView subject,percentage;
        private String[] subjectArray;
        private  String[] percentageArray;

        public SimpleAdapter(Context context, String[] subject, String[] percentage) {
            mContext = context;
            subjectArray = subject;
            percentageArray = percentage;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return subjectArray.length;
        }

        @Override
        public Object getItem(int position) {
            return subjectArray[position];
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
            subject = (TextView)convertView.findViewById(R.id.tvMain);
            percentage = (TextView)convertView.findViewById(R.id.tvPercentage);

            subject.setText(subjectArray[position]);
            percentage.setText(percentageArray[position]);

            return convertView;
        }
    }

}