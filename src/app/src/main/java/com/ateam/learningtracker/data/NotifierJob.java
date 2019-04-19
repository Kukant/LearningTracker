package com.ateam.learningtracker.data;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class NotifierJob extends JobService {
    private static final String TAG = "NotifierService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Notifier Started");
        doNotify(params);
        return true;
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