package com.home_assignment.bootcounter.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Boot Counter")
            .setContentText("Recurring task executed")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notification = notificationBuilder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)

        return Result.success()
    }

    companion object {
        private const val CHANNEL_ID = "BootCounterChannel"
        private const val NOTIFICATION_ID = 1
    }
}