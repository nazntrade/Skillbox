package ru.skillbox.notifications

import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val user = message.data["user"]
        val messageText = message.data["message"]
        if (user != null && messageText != null) {
            showMessageNotification(user, messageText)
        }
    }

    private fun showMessageNotification(user: String, message: String) {

        val intent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 123, intent, 0)

        val notification = NotificationCompat.Builder(this, NotificationChannels.MESSAGE_CHANNEL_ID)
            .setContentTitle("You have a new message from $user")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(this)
            .notify(12343, notification)
    }
}