package com.wordpress.smdaudhilbe.push_notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wordpress.smdaudhilbe.nikah.R
import com.wordpress.smdaudhilbe.nikah.ViewPagerActivity

/**
 * Created by Mohammed Audhil on 06/07/17.
 * Jambav, Zoho Corp
 */

class PushFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Log.d(javaClass.name, "---Firebase : From : " + remoteMessage?.from)
        // Check if message contains a data payload.
        remoteMessage?.data?.size?.let {
            if (remoteMessage.data.isNotEmpty()) {
                Log.d(javaClass.name, "---Firebase Message data payload: " + remoteMessage.data)
            }
        }

        // Check if message contains a notification payload.

        if (remoteMessage?.notification?.body != null) {
            Log.d(javaClass.name, "---Firebase Message Notification Body: " + remoteMessage.notification.body)
            sendNotification(remoteMessage.notification.body as String)
        }
    }

    //  send local notification
    fun sendNotification(msgBody: String) {
        val intent = Intent(this, ViewPagerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.app_icon)
                .setContentTitle("Assalaamu alaikkum!")
                .setContentText(msgBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build());
    }
}