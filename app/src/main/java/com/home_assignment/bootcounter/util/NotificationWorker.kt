package com.home_assignment.bootcounter.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.home_assignment.bootcounter.data.database.BootEventDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val dao = BootEventDatabase.getInstance(applicationContext).bootEventDao()

        CoroutineScope(Dispatchers.IO).launch {
            val events = dao.getBootEvent()
            val result = if (events.isEmpty()) {
                "No boots detected"
            } else {
                // todo: calculate diff between two boots and handle the single event
                events.mapIndexed { index, event ->
                    "$index - ${event.timestamp}"
                }.joinToString { "\n" }
            }

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Boot Counter")
                .setContentText(result)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notification = notificationBuilder.build()
            notificationManager.notify(NOTIFICATION_ID, notification)

        }
        return Result.success()
    }

    companion object {
        private const val CHANNEL_ID = "BootCounterChannel"
        private const val NOTIFICATION_ID = 1
    }
}