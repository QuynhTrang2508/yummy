package com.example.yummy.utlis

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.yummy.broadcast.MyBroadcast

object AlarmManagerUtil {
    fun create(context: Context, cookId: Int, timeChoose: Long) {
        val intent = Intent(context, MyBroadcast::class.java).apply {
            putExtra(AlarmConst.EXTRA_COOK_ID, cookId)
        }
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val pendingIntent =
            PendingIntent.getBroadcast(context, cookId, intent, 0)

        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            timeChoose,
            pendingIntent
        )
    }

    fun cancel(context: Context) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, MyBroadcast::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager?.cancel(pendingIntent)
    }
}
