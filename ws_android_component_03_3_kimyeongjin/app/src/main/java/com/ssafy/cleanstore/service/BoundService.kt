package com.ssafy.cleanstore.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.ssafy.cleanstore.dao.StuffDao
import com.ssafy.cleanstore.dto.Stuff

private const val TAG = "BoundService"

class BoundService : Service() {
//    private lateinit var myLocalBinder:MyLocalBinder
    private val myLocalBinder = MyLocalBinder()
//    private lateinit var stuffDao: StuffDao
    private val stuffDao = StuffDao()
    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: $intent")
//        myLocalBinder = MyLocalBinder()

        Log.d(TAG, "onBind: $stuffDao")
        return myLocalBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate: stuff")
//        stuffDao = StuffDao()
        stuffDao.dbOpenHelper(this)
        stuffDao.open()
        stuffDao.create()
        super.onCreate()
    }

    fun getDB(): StuffDao{
        Log.d(TAG, "getDB: stuff")
        return stuffDao
    } 

    override fun onDestroy() {
        stuffDao.close()
        super.onDestroy()
    }

    // 물품 추가
    fun stuffInsert(stuff: Stuff): Long {
        return stuffDao.stuffInsert(stuff)
    }

    // 물품 모두 조회
    fun stuffSelectAll(): MutableList<Stuff> {
        return stuffDao.stuffSelectAll()
    }

    // 물품 정보 변경
    fun stuffUpdate(stuff: Stuff): Int{
        return stuffDao.stuffUpdate(stuff)
    }

    // 물품 정보 삭제
    fun stuffDelete(stuffId: Int): Int{
        return stuffDao.stuffDelete(stuffId)
    }


    //Binder는 IBinder의 구현체로 onBind를 통해 서비스 클라이언트에게 전달되며
//클라이언트는 이 객체를 이용해 서비스에 선언된 기능을 호출
    inner class MyLocalBinder : Binder() {
        //외부 객체인 BoundService 객체를 반환하는 함수
        fun getService(): BoundService = this@BoundService
    }

}
