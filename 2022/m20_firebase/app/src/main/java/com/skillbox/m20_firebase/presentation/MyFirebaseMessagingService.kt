package com.skillbox.m20_firebase.presentation

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.skillbox.m19_location.R
import com.skillbox.m20_firebase.App
import com.skillbox.m20_firebase.MainActivity
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

// Full code is here
// https://github.com/firebase/snippets-android/blob/27bc88ce71547ea386dcca32e98596c5fb939712/messaging/app/src/main/java/com/google/firebase/example/messaging/kotlin/MyFirebaseMessagingService.kt

//Links:
//https://developers.google.com/oauthplayground/
//https://www.postman.com

//request
//https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages/send
//POST: https://fcm.googleapis.com/v1/projects/{project_id}/messages:send

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val notification = NotificationCompat.Builder(this, App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_attractions)
            .setContentTitle(remoteMessage.data["nickname"])
            .setContentText(
                remoteMessage.data["message"] +
                        " ${convertToData(remoteMessage.data["timestamp"])}"
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) //for tap on notification
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this)
            .notify(AttractionFragment.NOTIFICATION_ID, notification)
    }
    // [END receive_message]

    // [START on_new_token]
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Timber.tag(TAG).d("Refreshed token: %s", token)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Timber.tag(TAG).d("sendRegistrationTokenToServer %s", token)
    }

    private fun convertToData(timestamp: String?): String {
        timestamp ?: return ""
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date(timestamp.toLong() * 1000))
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}