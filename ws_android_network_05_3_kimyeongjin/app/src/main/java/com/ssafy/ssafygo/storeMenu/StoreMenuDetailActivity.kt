package com.ssafy.ssafygo.storeMenu

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.ssafygo.ApplicationClass
import com.ssafy.ssafygo.R
import com.ssafy.ssafygo.databinding.ActivityStoreMenuDetailBinding
import com.ssafy.ssafygo.dto.ReceiptDTO
import com.ssafy.ssafygo.dto.StoreMenuDTO
import com.ssafy.ssafygo.service.ReceiptService
import com.ssafy.ssafygo.service.StoreService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class StoreMenuDetailActivity : AppCompatActivity() {
    private val REGISTER = 0
    private val DELETE = 1
    private val MODIFY = 2

    private lateinit var binding: ActivityStoreMenuDetailBinding
    private lateinit var receiptService: ReceiptService
    private var storeId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreMenuDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storeReview = intent.getSerializableExtra("StoreReview") as StoreMenuDTO

        binding.menuName.text = storeReview.name
        binding.menuPrice.text = storeReview.price.toString()

        receiptService = ApplicationClass.retrofit.create(ReceiptService::class.java)

        binding.orderButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("메뉴 주문")
                .setMessage("주문하실 메뉴가 ${binding.menuName.text} 맞습니까?")
                .setPositiveButton("예", DialogInterface.OnClickListener { dialog, which ->
                    // 주문하기
                    addReceipt()
                })
                .setNegativeButton("아니오", DialogInterface.OnClickListener { dialog, which -> })
            val alertDialog = builder.create()
            alertDialog.show()
        }

    }

    private fun addReceipt(){
        val receipt =ReceiptDTO(
            binding.menuName.text.toString(),
            Date(System.currentTimeMillis()).toString(),
            price = binding.menuPrice.text.toString().toLong()
        )
        receiptService.addReceipt(receipt).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                val result = response.body()
                Log.d("result", "onResponse: $result")
                Toast.makeText(this@StoreMenuDetailActivity, "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("Receipt", t.message ?: "주문 통신오류")
            }

        })
    }

}