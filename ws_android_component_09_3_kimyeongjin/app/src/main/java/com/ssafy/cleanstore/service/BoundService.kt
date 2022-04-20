package com.ssafy.cleanstore.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.ssafy.cleanstore.dao.StuffDao
import com.ssafy.cleanstore.dto.Stuff

private const val TAG = "BoundService_싸피"
class BoundService : Service() {
    // Binder given to clients
    private val myBinder = MyBinder()

    private val stuffDAO = StuffDao()

    inner class MyBinder : Binder() {
        // Service 객체를 return
        fun getService(): BoundService = this@BoundService
    }

    // 물품 추가
    fun stuffInsert(stuff: Stuff): Long {
        return stuffDAO.stuffInsert(stuff)
    }

    // 물품 1개 조회
    fun stuffSelect(stuffId: Int): Stuff? {
        return stuffDAO.stuffSelect(stuffId)
    }

    // 물품 모두 조회
    fun stuffSelectAll(): MutableList<Stuff> {
        return stuffDAO.stuffSelectAll()
    }

    // 물품 정보 변경
    fun stuffUpdate(stuff: Stuff): Int{
        return stuffDAO.stuffUpdate(stuff)
    }

    // 물품 정보 삭제
    fun stuffDelete(stuffId: Int): Int{
        return stuffDAO.stuffDelete(stuffId)
    }

    // Bound Service에서 서비스 연결시 호출
    override fun onBind(intent: Intent?): IBinder {
        return myBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        stuffDAO.dbOpenHelper(this)
        stuffDAO.open()
        stuffDAO.create()
        super.onCreate()
    }

    override fun onDestroy() {
        stuffDAO.close()
        super.onDestroy()
    }
}