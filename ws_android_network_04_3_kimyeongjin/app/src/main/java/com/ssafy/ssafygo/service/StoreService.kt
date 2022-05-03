package com.ssafy.ssafygo.service

import com.ssafy.ssafygo.dto.StoreDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreService {
    // 가맹점 정보 가져오기
    @GET("store/{storeId}")
    fun getStore(@Path("storeId") storeId: Int): Call<StoreDTO>
}