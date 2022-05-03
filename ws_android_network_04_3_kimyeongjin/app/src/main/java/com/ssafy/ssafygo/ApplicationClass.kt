package com.ssafy.ssafygo

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationClass : Application() {
    // ipconfig를 통해 IP 확인하기
    // 핸드폰으로 접속할 때는 같은 인터넷 망에 연결되어 있어야 함 (유, 무선)
    val SERVER_URL = "http://192.168.219.174:8080/"

    companion object {
        // 전역변수 문법을 통해 Retrofit 인스턴스를 앱 실행시 1번만 생성하여 사용 (Singleton)
        lateinit var retrofit: Retrofit

        var writeMode = false
    }

    override fun onCreate() {
        super.onCreate()

        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}