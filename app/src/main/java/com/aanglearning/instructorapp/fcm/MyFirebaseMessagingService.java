package com.aanglearning.instructorapp.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.aanglearning.instructorapp.App;
import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.chat.ChatActivity;
import com.aanglearning.instructorapp.chathome.ChatsActivity;
import com.aanglearning.instructorapp.dashboard.DashboardActivity;
import com.aanglearning.instructorapp.model.MessageEvent;
import com.aanglearning.instructorapp.util.AppGlobal;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        AppGlobal.setSqlDbHelper(getApplicationContext());

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            if(App.isActivityVisible()) {
                EventBus.getDefault().post(new MessageEvent(remoteMessage.getData().get("message"),
                        Long.valueOf(remoteMessage.getData().get("sender_id"))));
            } else {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_stat_notify)
                                .setContentTitle(remoteMessage.getData().get("sender_name"))
                                .setContentText(remoteMessage.getData().get("message"))
                                .setDefaults(Notification.DEFAULT_SOUND)
                                .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("message")));

                mBuilder.setAutoCancel(true);

                Intent resultIntent = new Intent(getApplicationContext(), ChatActivity.class);
                resultIntent.putExtra("recipientId", Long.valueOf(remoteMessage.getData().get("sender_id")));
                resultIntent.putExtra("recipientName", remoteMessage.getData().get("sender_name"));
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                stackBuilder.addParentStack(ChatsActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(123, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());
            }

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}
