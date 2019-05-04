package com.ateam.learningtracker.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ateam.learningtracker.R;
import com.ateam.learningtracker.data.DataConnector;
import com.ateam.learningtracker.data.SubjectProgress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ProgressOverviewActivity extends AppCompatActivity {

    private ListView listView;
    private Spinner timeperiod;
    public ArrayList<String> subjectArray;
    public RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressoverview_main);

        setupUIViews();
        setupListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String subjectName = subjectArray.get(position);
                startActivity(new Intent(ProgressOverviewActivity.this, SubjectOverviewActivity.class).putExtra("subjectName", subjectName));
            }
        });



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

    private void setupSpinnerContent(){
        Spinner spinner = findViewById(R.id.spinnerTimeperiod);
        ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(this, R.array.TimePeriods, android.R.layout.simple_spinner_item);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public class SimpleAdapter extends BaseAdapter{

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView subject,percentage;
        private ArrayList<String>  percentageArray;
        private RelativeLayout relativeLayout;

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
            relativeLayout = convertView.findViewById(R.id.RLprogressOverview);

            ProgressBar pb = convertView.findViewById(R.id.progressBar);
            pb.setProgress(Integer.valueOf(percentageArray.get(position)));

            subject.setText(subjectArray.get(position));
            percentage.setText(String.format("%s %%", percentageArray.get(position)));
            if(Integer.valueOf(percentageArray.get(position)) > 66) {
                relativeLayout.setBackgroundColor(Color.parseColor("#27C231"));
            }
            else if(Integer.valueOf(percentageArray.get(position)) > 33 && Integer.valueOf(percentageArray.get(position)) < 66) {
                relativeLayout.setBackgroundColor(Color.parseColor("#FF8533"));
            }
            else {
                relativeLayout.setBackgroundColor(Color.parseColor("#FD3030"));
            }

            return convertView;
        }
    }
}