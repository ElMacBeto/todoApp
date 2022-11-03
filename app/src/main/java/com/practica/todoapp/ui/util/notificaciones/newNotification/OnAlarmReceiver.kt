package com.practica.todoapp.ui.util.notificaciones.newNotification

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.practica.todoapp.ui.util.notificaciones.Notifications

const val NOTIFICATION_DRIVER = "notification_driver"
const val NOTIFICATION_SERVICE = "notification_service"
const val NOTIFICATION_CHANNEL_ID = "notification_channel_id"
const val NOTIFICATION_WORK_ID = "notification_work_id"

class OnAlarmReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {

        val task = intent.getStringExtra(NOTIFICATION_DRIVER)
        val date = intent.getStringExtra(NOTIFICATION_SERVICE)
        val channelId = intent.getStringExtra(NOTIFICATION_CHANNEL_ID)
        val workId = intent.getIntExtra(NOTIFICATION_WORK_ID, 0)

        val notification = Notifications()
        notification.createNotificationChannel(channelId!!)
        notification.createExplicitIntentForActivity(
            task!!,
            date!!,
            workId
        )
    }
}