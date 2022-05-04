package com.ssafy.ssafygo.service

import com.ssafy.ssafygo.dto.StoreMenuDTO
import retrofit2.Call
import retrofit2.http.*

interface StoreMenuService {
    // 가맹점 메뉴 정보 반환
    @GET("store/{storeID}/menus")
    fun getStoreMenu(@Path("storeID") storeId: Int): Call<List<StoreMenuDTO>>

}