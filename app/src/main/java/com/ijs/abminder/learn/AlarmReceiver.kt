package com.ijs.abminder.learn

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.ijs.abminder.MainActivity
import com.ijs.abminder.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context : Context, intent : Intent) {
        val studyPlanTitle = intent.getStringExtra("study_plan_title")

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "study_plan_channel",
            "Study Plan Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "study_plan_channel")
            .setSmallIcon(R.drawable.ic_logo_modified)
            .setContentTitle("Study Plan Reminder")
            .setContentText("It's time to study $studyPlanTitle")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(0, notification.build())
    }
}