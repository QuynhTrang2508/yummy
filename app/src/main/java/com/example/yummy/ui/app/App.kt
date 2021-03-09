package com.example.yummy.ui.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                CHANNEL_1_ID,
                NAME_SYSTEM_1,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is channel 1"

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.apply {
                createNotificationChannel(channel1)
            }
        }
    }

    companion object {
        const val CHANNEL_1_ID = "channel1"
        const val NAME_SYSTEM_1 = "Yummy1"
    }
}
