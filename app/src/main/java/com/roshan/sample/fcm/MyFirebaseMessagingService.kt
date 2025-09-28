package com.roshan.sample.fcm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.roshan.sample.MainActivity
import com.roshan.sample.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        remoteMessage.notification?.let {
            sendNotification(it.title, it.body)
        }
    }

    private fun sendNotification(title: String?, message: String?) {
        val channelId = "fcm_default_channel"
        val notificationId = 1
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title ?: getString(R.string.app_name))
            .setContentText(message ?: "")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        // Create notification channel only on Android O and above
        val channel = NotificationChannel(
            channelId,
            getString(R.string.app_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val systemNotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // Avoid recreating the channel if it already exists
        if (systemNotificationManager.getNotificationChannel(channelId) == null) {
            systemNotificationManager.createNotificationChannel(channel)
        }

        // Check notification permission only on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Lint: Don't show notification if permission not granted
                return
            }
        }
        NotificationManagerCompat.from(this).notify(notificationId, builder.build())
    }


}
