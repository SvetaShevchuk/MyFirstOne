package com.kidsPlayerPrincess.kidsplayerprincess;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class ApplicationClass extends Application {
    public static final String CHANNEL_ID_1 = "channel1";
    public static final String CHANNEL_ID_2 = "channel2";
    public static final String ACTION_PREVIOUS = "actionPrevious";
    public static final String ACTION_NEST = "action_next";
    public static final String ACTION_PLAY = "actionPlay";

    @Override
    public void onCreate() {
        createNotificationChannel();
        super.onCreate();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID_1, "channel(1)",
                    NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Channel 1 Desc..");
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID_2, "channel(2)",
                    NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Channel 2 Desc..");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel1);
            notificationManager.createNotificationChannel(channel2);
        }

    }
}

