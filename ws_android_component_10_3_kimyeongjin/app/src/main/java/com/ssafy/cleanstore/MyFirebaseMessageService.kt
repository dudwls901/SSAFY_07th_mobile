package com.ssafy.cleanstore

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val TAG = "MyFirebaseService_ssafy"

class MyFirebaseMessageService: FirebaseMessagingService(){

    //새로 갱신된 등록 토큰을 전달받을 때 호출되는 콜백 함수
    //fcm 내부에서 어떠한 기준에 따라 토큰을 갱신시킴
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: $token")
    }



    //앱이 포그라운드 상태에서 Push 메시지를 전달받기 위해 아래 콜백 함수를 작성
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "onMessageReceived: ")

        message.notification.let { message ->
            val messageTitle = message!!.title
            val messageContent = message!!.body

            val mainIntent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val mainPendingIntent =
                PendingIntent.getActivity(this, 0, mainIntent, 0)

            val builder = NotificationCompat.Builder(this, "ssafy_id")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(messageTitle)
                .setContentText(messageContent)
                .setAutoCancel(true)
                .setContentIntent(mainPendingIntent)

            //노티 띄우기
            NotificationManagerCompat.from(this).apply {
                notify(101, builder.build())
            }
        }
    }
}