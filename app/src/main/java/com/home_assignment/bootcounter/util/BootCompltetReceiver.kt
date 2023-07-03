package com.home_assignment.bootcounter.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.home_assignment.bootcounter.data.database.BootEventDatabase
import com.home_assignment.bootcounter.data.database.entities.BootEventEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val database = BootEventDatabase.getInstance(context!!)
            val bootEventDao = database.bootEventDao()

            val bootEvent = BootEventEntity(System.currentTimeMillis())

            CoroutineScope(Dispatchers.IO).launch {
                bootEventDao.insertBootEvent(bootEvent)
            }

            scheduleNotificationTask(context)
        }
    }

    private fun scheduleNotificationTask(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val notificationWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(REPEAT_INTERVAL, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniquePeriodicWork(
            "BootCounterNotification",
            ExistingPeriodicWorkPolicy.UPDATE,
            notificationWorkRequest
        )
    }

    companion object {
        private const val REPEAT_INTERVAL = 15L
    }
}