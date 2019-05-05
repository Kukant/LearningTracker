package com.ateam.learningtracker.data;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.orm.SugarApp;

import java.util.Collections;

public class Channel extends SugarApp {
    public static final String CHANNEL_1_ID = "StudyWarning";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Study Warnings",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("This is to warn users that they have not done enough study.");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }
}
