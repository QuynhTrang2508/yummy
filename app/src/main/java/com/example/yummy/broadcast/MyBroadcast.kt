package com.example.yummy.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.yummy.service.AlarmManagerService

class MyBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        AlarmManagerService.enqueueWork(context, intent)
    }
}
