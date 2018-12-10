package com.tritpo.mymedicine;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Remind");
        builder.setContentText("Beep");
        builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS | NotificationCompat.DEFAULT_SOUND);


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
