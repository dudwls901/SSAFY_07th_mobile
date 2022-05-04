package com.ssafy.ssafygo.service

import com.ssafy.ssafygo.dto.ReceiptDTO
import com.ssafy.ssafygo.dto.StoreMenuDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReceiptService {

    @POST("receipt")
    fun addReceipt(@Body receiptDto : ReceiptDTO) : Call<Boolean>

    @GET("receipt/all")
    fun getAllReceipt(): Call<List<ReceiptDTO>>

    @GET("receipt")
    fun getLastReceipt(): Call<ReceiptDTO>
}