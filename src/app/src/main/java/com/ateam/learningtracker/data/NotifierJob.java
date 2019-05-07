package com.ateam.learningtracker.data;

import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.ateam.learningtracker.R;
import com.ateam.learningtracker.views.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class NotifierJob extends JobService {
    private static final String TAG = "NotifierService";
    private boolean jobCancelled = false;
    private NotificationManagerCompat notificationManager;
    private int i = 0;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Notifier Started");

        List<SubjectProgress> progressInfo = DataConnector.getSubjectsProgressInfo();

        ArrayList<Integer> percentage = new ArrayList<>();

        for (SubjectProgress sp: progressInfo) {
            percentage.add((int) (sp.overallProgress * 100));
        }

        Integer minPercentage = findMinPercent(percentage);
        System.out.println("min is =" +minPercentage);

        if (i != 0) {
            if (minPercentage < 65) {
            notificationManager = NotificationManagerCompat.from(this);

            Notification notification = new NotificationCompat.Builder(this, Channel.CHANNEL_1_ID)
                    .setContentTitle("study")
                    .setSmallIcon(R.drawable.ic_study_warning)
                    .setContentText("You have subjects you need to study!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setColor(Color.RED)
                    .build();

            notificationManager.notify(1, notification);
                }
            }
            i++;
            doNotify(params);
            return true;

    }

    private Integer findMinPercent(ArrayList<Integer> percentage) {
        Integer min = Integer.MAX_VALUE;
        for (Integer number : percentage) {
            if (number < min) {
                min = number;
            }
        }
        return min;
    }

    private void doNotify(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {

                    Log.d(TAG, "run: " + i);
                    if (jobCancelled) {
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                Log.d(TAG, "Notify finished");
                jobFinished(params, false);

            }
        }).start();
    }



    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before time");
        jobCancelled = true;
        return false;
    }
}
