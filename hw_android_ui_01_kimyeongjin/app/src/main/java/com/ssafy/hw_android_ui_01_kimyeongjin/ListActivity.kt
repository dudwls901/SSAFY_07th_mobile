package com.ssafy.hw_android_ui_01_kimyeongjin

import android.os.Bundle
import android.util.Log
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.hw_android_ui_01_kimyeongjin.databinding.ActivityListBinding
import com.ssafy.hw_android_ui_01_kimyeongjin.databinding.ActivityMainBinding

class ListActivity: AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var movieList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieList = intent.getStringArrayListExtra("movieList") as ArrayList<String>

        initViews()
    }

    private fun initViews(){
        val map = ArrayList<Map<String,String>>()
        for(i in 0 until movieList.size){
            map.add(mapOf("id" to "${i+1}번 영화", "name" to movieList[i]))
        }

        val adapter = CustomListAdapter(this, map, R.layout.list_item_layout)
        binding.listView.adapter = adapter

        binding.backButton.setOnClickListener {
            finish()
        }

    }
}