package journal.gratitude.com.gratitudejournal.util.reminders

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import journal.gratitude.com.gratitudejournal.ContainerActivity
import journal.gratitude.com.gratitudejournal.ContainerActivity.Companion.CHANNEL_ID
import journal.gratitude.com.gratitudejournal.R
import journal.gratitude.com.gratitudejournal.util.reminders.NotificationScheduler.Companion.ALARM_TYPE_RTC

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val intentToRepeat = Intent(context, ContainerActivity::class.java)
        intentToRepeat.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP //set flag to restart/relaunch the app
        intentToRepeat.putExtra(fromNotification, true)
        val pendingIntent = PendingIntent.getActivity(
            context,
            ALARM_TYPE_RTC,
            intentToRepeat,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Build notification
        createLocalNotification(context, pendingIntent)
    }

    private fun createLocalNotification(context: Context, pendingIntent: PendingIntent) {

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_app_icon)
            .setContentTitle("Presently Gratitude Reminder")
            .setContentText("What are you grateful for today?")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setCategory(Notification.CATEGORY_REMINDER)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(ALARM_TYPE_RTC, notificationBuilder.build())
    }

    companion object {
        const val fromNotification = "fromNotification"
    }

}