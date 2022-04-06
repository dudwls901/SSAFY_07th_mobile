package com.ssafy.memo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import com.ssafy.memo.util.Utils

private const val TAG = "MemoReceiver_싸피"
class MemoReceiver : BroadcastReceiver() {

    // sms을 수신한 경우 onReceive가 호출
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        // 들어온 메시지를 리스트 형태로 전환
        val message = parseMessage(bundle)
        if (message.isNotEmpty()) {
            // 최신 메시지
            val sender = message[0]?.originatingAddress.toString()
            val contents = message[0]?.messageBody.toString()
            val date = message[0]?.timestampMillis

            Log.d(TAG, "onReceive: $sender $contents")
            sendToMainActivity(context, sender, contents, date!!)
        }
    }

    // 정형화된 Message Parser Format -> 메시지를 리스트 형태로 반환한다.
    private fun parseMessage(bundle: Bundle?): Array<SmsMessage?> {
        val objects = bundle?.get("pdus") as Array<*>
        val messages = arrayOfNulls<SmsMessage>(objects.size)
        for (i in messages.indices) {
            // 안드로이드 버전확인
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val format = bundle.getString("format")
                messages[i] = SmsMessage.createFromPdu(objects[i] as ByteArray, format)
            }
        }

        Log.d(TAG, "parseMessage: $messages")
        return messages
    }

    private fun sendToMainActivity(context: Context?, sender: String, contents: String, date: Long) {
        val intent = Intent(context, MainActivity::class.java)
        // 엑티비티 흐름을 제어하기 위한 IntentFlag
        // FLAG_ACTIVITY_NEW_TASK : 새로운 Task 생성
        // FLAG_ACTIVITY_SINGLE_TOP : Activity가 이미 Stack의 최상단에 있을 경우 다시 시작하지 않고 재사용
        // FLAG_ACTIVITY_CLEAR_TOP : 실행하는 Activity가 Stack에 있을 경우 새로 시작하지 않고 상위 스택 모두 제거
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // Intent에 메시지 담기
        intent.putExtra("SMSSender", sender)
        intent.putExtra("SMSContents", contents)
        intent.putExtra("SMSDate", date)
        intent.putExtra("SMSstate", true)

        Log.d(TAG, "sendToMainActivity: $sender $contents")
        
        context!!.startActivity(intent)
    }
}