package com.example.firstapp

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat

const val NOTIFICATION_ID: Int = 0;
const val CHANNEL_ID: String = "com.example.firstapp.EMS_CHANNEL_ID";

class AlarmReceiver: BroadcastReceiver {

    constructor()

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        val intent = Intent(context, SearchHistoryActivity::class.java)
        val contentPendingIntent = PendingIntent
            .getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("EMS tracker")
            .setContentText("We've received an update for your search item.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(contentPendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL);

        notificationManager?.notify(NOTIFICATION_ID, builder.build())
    }

}