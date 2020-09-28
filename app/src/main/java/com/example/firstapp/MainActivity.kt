package com.example.firstapp

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.time.Instant
import java.time.format.DateTimeFormatter


const val SEARCH_EMS_ID = "com.example.firstApp.EMS_ID"
private const val ALARM_REQUEST_ID = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DbHandler.initialize(applicationContext)
        createNotificationChannel()

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val alarmIntent = Intent(this, AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getService(
            this,
            ALARM_REQUEST_ID,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val triggerTime = (SystemClock.elapsedRealtime() + 10 * 1000) // ten seconds for demo/testing purposes
        val repeatTime = 60*1000L // one minute for demo/testing purposes
        alarmManager?.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            triggerTime,
            repeatTime,
            pendingIntent)
    }

    fun search(view: View) {
        val emsInputComponent = findViewById<EditText>(R.id.emsIdTxt);
        val emsId = emsInputComponent.text.toString();
        val apiCallResult =  DataFetcher().execute(emsId).get();

        if (DbHandler.db == null) {
            AlertDialog.Builder(applicationContext)
                .setTitle("An error has occurred.")
                .setMessage("A DB connection could not be opened")
                .show();
        }

        val dbItem = DbHandler.GetByIdExecutor().execute(emsId).get();
        if (dbItem == null) {
            DbHandler.InsertExecutor().execute(
                SearchHistoryItem(
                    emsId, DateTimeFormatter.ISO_INSTANT.format(
                        Instant.now()
                    ), apiCallResult.size.toString()
                )
            );
        }

        val intent = Intent(this, SearchResultActivity::class.java).apply {
            putExtra(SEARCH_EMS_ID, apiCallResult)
        }
        startActivity(intent)
    }

    fun history(view: View) {
        val intent = Intent(this, SearchHistoryActivity::class.java)
        startActivity(intent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Ems_tracker_notficication_channer"
            val descriptionText = "A channel used to notify users of their EMS parcels statuses"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
