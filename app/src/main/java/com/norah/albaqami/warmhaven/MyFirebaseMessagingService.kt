package com.norah.albaqami.warmhaven

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.norah.albaqami.warmhaven.MainActivity
import com.norah.albaqami.warmhaven.R

const val channel_id ="nana"
const val channel_name ="com.norah.albaqami.warmhaven.notification"
class MyFirebaseMessagingService : FirebaseMessagingService() {
    fun generatorNotification(title : String, message:String){
val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
       var builder : NotificationCompat.Builder =  NotificationCompat.Builder(applicationContext,
           channel_id)
           .setSmallIcon(R.drawable.appicon)
           .setAutoCancel(true)
           .setOnlyAlertOnce(true)
           .setContentIntent(pendingIntent)
        builder = builder.setContent(getRemoteView(title, message))
        val notificationManger= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channel_id, channel_name,NotificationManager.IMPORTANCE_HIGH)
            notificationManger.createNotificationChannel(notificationChannel)
        }
        notificationManger.notify(0,builder.build())
    }

    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title : String, message: String): RemoteViews {
    val remoView = RemoteViews("com.norah.albaqami.warmhaven.notification",R.layout.notification)
        remoView.setTextViewText(R.id.app_name,title)
        remoView.setTextViewText(R.id.message,message)
        remoView.setImageViewResource(R.id.app_logo,R.drawable.appicon)
        return remoView

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
      if(remoteMessage.getNotification() != null ){
          generatorNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!! )
      }


    }
}