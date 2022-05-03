package com.ssafy.ssafygo.storeMenu

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.ssafygo.R
import com.ssafy.ssafygo.dto.StoreMenuDTO

class StoreMenuDetailActivity : AppCompatActivity() {
    private val REGISTER = 0
    private val DELETE = 1
    private val MODIFY = 2

    private lateinit var tv_name: TextView
    private lateinit var tv_price: TextView

    private var storeId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_menu_detail)

        tv_name = findViewById(R.id.menu_name)
        tv_price = findViewById(R.id.menu_price)

        val storeReview = intent.getSerializableExtra("StoreReview") as StoreMenuDTO

        tv_name.setText(storeReview.name)
        tv_price.setText(storeReview.price.toString())


    }

}