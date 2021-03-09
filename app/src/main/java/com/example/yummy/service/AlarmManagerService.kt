package com.example.yummy.service

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.yummy.R
import com.example.yummy.data.model.CookNotification
import com.example.yummy.data.source.local.utils.OnDataLocalCallback
import com.example.yummy.ui.app.App.Companion.CHANNEL_1_ID
import com.example.yummy.ui.app.App.Companion.NAME_SYSTEM_1
import com.example.yummy.ui.main.MainActivity
import com.example.yummy.utlis.AlarmConst
import com.example.yummy.utlis.RepositoryUtils
import com.example.yummy.utlis.showToast


class AlarmManagerService : JobIntentService() {
    private lateinit var notificationManager: NotificationManagerCompat

    override fun onHandleWork(intent: Intent) {
        val cookId = intent.getIntExtra(AlarmConst.EXTRA_COOK_ID, -2)
        val repository = RepositoryUtils.getCookRepository(this)
        repository.getCookNotification(object : OnDataLocalCallback<List<CookNotification>> {
            override fun onSuccess(data: List<CookNotification>) {
                data.forEach {
                    if (it.id == cookId) {
                        showNotification(it)
                    }
                }
            }

            override fun onFail(exception: Exception) {
                this@AlarmManagerService.showToast(exception.message.toString())
            }
        })
    }

    private fun showNotification(cookNotification: CookNotification) {
        createNotificationChannel(cookNotification)
        deleteNotificationChannel(cookNotification)
    }

    private fun createNotificationChannel(cookNotification: CookNotification) {
        notificationManager = NotificationManagerCompat.from(this)

        val intent = MainActivity.getIntentFromNotification(this)
        val pendingIntent = PendingIntent.getActivity(
            this,
            cookNotification.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_1_ID)
            .setSmallIcon(R.drawable.yummy_logo)
            .setContentTitle(NAME_SYSTEM_1)
            .setContentText(
                String.format(
                    CONTENT_NOTIFICATION,
                    cookNotification.date,
                    cookNotification.time
                )
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_EMAIL)
            .build()
        notificationManager.notify(cookNotification.id, notification)
    }

    private fun deleteNotificationChannel(cookNotification: CookNotification) {
        val repository = RepositoryUtils.getCookRepository(this)
        repository.deleteCookNotification(cookNotification.id,
            object : OnDataLocalCallback<Boolean> {
                override fun onSuccess(data: Boolean) {}

                override fun onFail(exception: Exception) {
                    this@AlarmManagerService.showToast(exception.message.toString())
                }
            })
    }


    companion object {
        private const val CONTENT_NOTIFICATION =
            "Sắp đến giờ rồi! %s - %s chúng ta bắt đầu nấu ăn nhé"

        fun enqueueWork(context: Context, work: Intent) {
            val cookId = work.getIntExtra(AlarmConst.EXTRA_COOK_ID, -2)
            enqueueWork(context, AlarmManagerService::class.java, cookId, work)
        }
    }
}