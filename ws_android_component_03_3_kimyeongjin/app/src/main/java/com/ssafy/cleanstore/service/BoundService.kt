package com.ssafy.cleanstore.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.ssafy.cleanstore.dao.StuffDao

private const val TAG = "BoundService"
class BoundService : Service() {
    private lateinit var myLocalBinder: MyLocalBinder
    val stuffDao= StuffDao()

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: $intent")
        myLocalBinder = MyLocalBinder()
//        stuffDao = StuffDao()
        stuffDao.dbOpenHelper(this)
        stuffDao.open()
        Log.d(TAG, "onBind: $stuffDao")
        return myLocalBinder
    }

//    fun getDB() = stuffDao

}

//Binder는 IBinder의 구현체로 onBind를 통해 서비스 클라이언트에게 전달되며
//클라이언트는 이 객체를 이용해 서비스에 선언된 기능을 호출
class MyLocalBinder: Binder(){
    //외부 객체인 BoundService 객체를 반환하는 함수
    fun getService(): BoundService = BoundService()
}