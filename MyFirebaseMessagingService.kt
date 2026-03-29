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

/**
 * MyFirebaseMessagingService - Handles Firebase Cloud Messaging (FCM) operations
 * 
 * Responsibilities:
 * - Receive push notifications from Firebase Cloud Messaging
 * - Handle new device tokens for FCM registration
 * - Display notifications to users
 * - Create notification channels (Android 8+)
 * - Handle permission checks (Android 13+)
 * 
 * Lifecycle:
 * - Service runs in background when app is not in foreground
 * - Automatically called by Firebase when message arrives
 * - Should be registered in AndroidManifest.xml with FCM intent filter
 * 
 * @author Roshan Bhagat
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    /**
     * onNewToken - Called when device receives new FCM registration token
     * 
     * When called:
     * - App first installs and initializes FCM
     * - Device token is refreshed
     * - User clears app data or uninstalls/reinstalls
     * 
     * Responsibilities:
     * - Send token to your backend server
     * - Store token for push notification targeting
     * - Update user device mapping in database
     * 
     * @param token Unique device token for this device + app combination
     * 
     * TODO: Implement sending token to backend server for notifications
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
        
        // TODO: Send token to backend server
        // sendTokenToServer(token)
    }

    /**
     * onMessageReceived - Called when device receives a push notification
     * 
     * When called:
     * - App is in foreground when message arrives
     * - Message sent from Firebase Cloud Messaging
     * 
     * For background messages:
     * - Notification is automatically shown if data payload exists
     * - This method NOT called if app is in background
     * 
     * @param remoteMessage Message object containing notification and data
     * 
     * Note: Only handles notification payloads here.
     * For data-only messages, implement custom handling logic.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        // Extract notification and display it
        remoteMessage.notification?.let {
            sendNotification(it.title, it.body)
        }
        
        // TODO: Handle data payload separately if needed
        // remoteMessage.data?.let { dataPayload ->
        //     // Handle custom data (e.g., deep links, custom actions)
        // }
    }

    /**
     * sendNotification - Creates and displays a system notification
     * 
     * Handles:
     * - Creating notification channel for Android 8+
     * - Setting notification content and appearance
     * - Handling click action to open MainActivity
     * - Checking runtime permissions on Android 13+
     * - Avoiding duplicate channel creation
     * 
     * @param title Notification title
     * @param message Notification message body
     * 
     * Android version considerations:
     * - Channel creation: Required for Android 8.0 (API 26)+
     * - Permission check: Required for Android 13.0 (API 33)+
     */
    private fun sendNotification(title: String?, message: String?) {
        // Unique identifier for this notification channel
        val channelId = "fcm_default_channel"
        
        // Unique identifier for this notification
        // Note: Using fixed ID (1) will replace previous notification
        // Consider using unique IDs for multiple notifications
        val notificationId = 1
        
        // Create intent to open MainActivity when notification is tapped
        val intent = Intent(this, MainActivity::class.java).apply {
            // Clear activity stack and bring MainActivity to top
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        
        // Create PendingIntent for the notification click action
        val pendingIntent = PendingIntent.getActivity(
            this, 
            0, 
            intent, 
            // FLAG_ONE_SHOT: Can only be used once
            // FLAG_IMMUTABLE: Immutable - cannot be modified
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build notification with title, message, and click action
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title ?: getString(R.string.app_name)) // Fallback to app name
            .setContentText(message ?: "") // Fallback to empty string
            .setAutoCancel(true) // Dismiss notification when clicked
            .setContentIntent(pendingIntent) // Open MainActivity on click

        // Create notification channel only on Android 8.0+
        val channel = NotificationChannel(
            channelId,
            getString(R.string.app_name), // Channel display name
            NotificationManager.IMPORTANCE_DEFAULT // Importance level
        )
        
        // Get system notification manager
        val systemNotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        
        // Avoid recreating the channel if it already exists (expensive operation)
        if (systemNotificationManager.getNotificationChannel(channelId) == null) {
            systemNotificationManager.createNotificationChannel(channel)
        }

        // Check notification permission only on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Verify POST_NOTIFICATIONS permission is granted
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Lint: Don't show notification if permission not granted
                // User hasn't granted notification permission yet
                return
            }
        }
        
        // Display the notification
        NotificationManagerCompat.from(this).notify(notificationId, builder.build())
    }