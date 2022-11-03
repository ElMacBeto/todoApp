package com.practica.todoapp.ui.util.notificaciones

import android.app.*
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.practica.todoapp.MainApplication
import com.practica.todoapp.R
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.ui.activity.notification.FLAG_TASK_ID
import com.practica.todoapp.ui.activity.notification.MyNotificationActivity
import com.practica.todoapp.ui.util.notificaciones.newNotification.*
import java.util.*


class Notifications {

    val context = MainApplication.utilComponent!!.appContext
    var CHANNEL_ID = ""
    var ID = 3

    fun createNotificationChannel(channelId: String) {
        CHANNEL_ID = channelId
        ID = (0..1000).random() + 1
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "taskChannel"
            val descriptionText = "example of notification channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun createExplicitIntentForActivity(task: String, date: String, taskId: Int) {

        val resultIntent = Intent(context, MyNotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(FLAG_TASK_ID, taskId)
        }

        val pendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(
                0,
                FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_task)
            .setContentTitle("Date $date")
            .setContentText("Task: $task!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(ID, builder.build())
        }
    }

    fun setNotification(task: TaskEntity, isPosponer: Boolean = false): Boolean {
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, OnAlarmReceiver::class.java).apply {
            putExtra(NOTIFICATION_DRIVER, task.name)
            putExtra(NOTIFICATION_SERVICE, task.date)
            putExtra(NOTIFICATION_CHANNEL_ID, generateKey())
            putExtra(NOTIFICATION_WORK_ID, task.id)
            action = "BroadcastReceiverAction"
        }

        val pendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(
                    context,
                    task.notificationCode,
                    intent,
                    PendingIntent.FLAG_MUTABLE
                )
            } else {
                PendingIntent.getBroadcast(
                    context,
                    task.notificationCode,
                    intent,
                    PendingIntent.FLAG_ONE_SHOT
                )
            }

        val date = task.date.split("-")
        val year = date[0].toInt()
        val month = date[1].toInt()
        val day = date[2].toInt()

        val time= task.time.split(":")
        val hour = time[0].toInt()
        val minute = time[1].toInt()

        val objCalendar = Calendar.getInstance()

        if (!isPosponer) {
            objCalendar.set(Calendar.YEAR, year)
            objCalendar.set(Calendar.MONTH, month - 1)
            objCalendar.set(Calendar.DAY_OF_MONTH, day)
            objCalendar.set(Calendar.HOUR_OF_DAY, hour)
            objCalendar.set(Calendar.MINUTE, minute - 30)
            objCalendar.set(Calendar.SECOND, 0)
            objCalendar.set(Calendar.MILLISECOND, 0)
        } else {
            val currentMinutes = objCalendar.get(Calendar.MINUTE)
            objCalendar.set(Calendar.MINUTE, currentMinutes + 5)
        }

        var notTotificate = false

        if (objCalendar.get(Calendar.MINUTE) >= minute &&
            objCalendar.get(Calendar.HOUR_OF_DAY) >= hour &&
            objCalendar.get(Calendar.MONTH) >= month - 1
        ) {
            notTotificate = true
        }

        return if (!notTotificate) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, objCalendar.timeInMillis, pendingIntent)
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, objCalendar.timeInMillis, pendingIntent)
            }
            true
        } else false
    }

    fun cancelNotification(work: TaskEntity) {
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val myIntent = Intent(
            context,
            OnAlarmReceiver::class.java
        )
        val pendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(
                    context,
                    work.notificationCode,
                    myIntent,
                    PendingIntent.FLAG_MUTABLE
                )
            } else {
                PendingIntent.getBroadcast(
                    context,
                    work.notificationCode,
                    myIntent,
                    PendingIntent.FLAG_ONE_SHOT
                )
            }
        alarmManager!!.cancel(pendingIntent)
    }

    private fun generateKey(): String {
        return UUID.randomUUID().toString()
    }

}