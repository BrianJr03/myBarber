package jr.brian.mybarber.model.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import jr.brian.mybarber.R
import jr.brian.mybarber.view.activities.HomeActivity

class WorkManagerClass(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: Notification.Builder
    private val channelId = "ChannelId"

    override fun doWork(): Result {
        val message = inputData.getString("message")
        val content = inputData.getString("content")
        if (message != null && content != null) {
            sendNotification(message, content)
        }
        val data = Data.Builder()
        data.putString("Response", "Success")
        return Result.success(data.build())
    }

    private fun getNotificationChannel() {
        notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                channelId,
                "Description of channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun sendNotification(title: String, content: String) {
        val intent = Intent(context, HomeActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        getNotificationChannel()
        notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.notifications_36)
                .setContentIntent(pendingIntent)
        } else {
            Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1, notificationBuilder.build())
    }
}