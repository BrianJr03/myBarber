package jr.brian.mybarber.model.data.remote.firebase

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import jr.brian.mybarber.R
import jr.brian.mybarber.view.activities.home.HomeActivity

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FcmService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.title?.let {
            val aptNo = message.data["aptNo"]
            Log.i("TAG", it)
            showNotification(
                message.notification?.title,
                message.notification?.body,
                aptNo
            )
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag", "ObsoleteSdkInt")
    fun showNotification(
        title: String?,
        message: String?,
        apptNo: String?
    ) {
        val intent = Intent(this, HomeActivity::class.java)
        val channelId = "notification_channel"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            baseContext,
            channelId
        )
            .setSmallIcon(R.drawable.cut_24)
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContentTitle(title)
            .setContentText("$apptNo\n$message")
            .setSmallIcon(R.drawable.cut_24)

        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                channelId, "myBarber",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager.notify(0, builder.build())
    }
}