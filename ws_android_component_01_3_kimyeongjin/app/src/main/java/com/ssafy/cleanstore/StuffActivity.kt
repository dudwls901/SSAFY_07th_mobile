package com.ssafy.cleanstore

import android.R
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.cleanstore.databinding.ActivityStuffBinding
import com.ssafy.cleanstore.databinding.ActivityStuffEditBinding
import com.ssafy.cleanstore.dto.Stuff

class StuffActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStuffBinding

    private lateinit var stuffList: ArrayList<Stuff>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initStuffList()
        initViews()
    }

    private fun initViews() = with(binding) {

        //리스트뷰 데이터 갱신
        updateListView()

        //리스트뷰 초기화
        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@StuffActivity, StuffEditActivity::class.java)
            intent.putExtra("actionType", "edit")
            intent.putExtra("idx", position)
            intent.putExtra("name", stuffList[position].productName)
            intent.putExtra("count", stuffList[position].productCount)
            stuffEditActivityListener.launch(intent)
        }

        //버튼 초기화
        addButton.setOnClickListener {
            val intent = Intent(this@StuffActivity, StuffEditActivity::class.java)
            intent.putExtra("actionType", "save")
            stuffEditActivityListener.launch(intent)
        }
    }

    //아이템 리스트 초기화
    private fun initStuffList() {
        stuffList = ArrayList()
        stuffList.add(Stuff("사과", 10))
        stuffList.add(Stuff("참외", 11))
    }

    //리스트뷰 데이터 갱ㅅ니
    private fun updateListView(){
        val strData = Array(stuffList.size){""}
        for(i in stuffList.indices){
            strData[i] = stuffList[i].toString()
        }
        val adapter = ArrayAdapter(this@StuffActivity, R.layout.simple_list_item_1,
            strData)
        binding.listView.adapter = adapter
    }

    //람다
    private val stuffEditActivityListener : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            val intent = it.data
            val returnType = intent!!.getStringExtra("actionType")
            val productName = intent!!.getStringExtra("name")
            val productCount = intent!!.getIntExtra("count",0)
            val idx = intent!!.getIntExtra("idx",-1)
            when(returnType){
                "save" ->{
                    stuffList.add(Stuff(productName?:"", productCount))
                }
                "edit" ->{
//                    Log.d(TAG, "$idx")
                    if(idx>=0) {
                        stuffList[idx] = Stuff(productName?:"", productCount)
                    }
                }
                "delete"->{
                    if(idx>=0) {
                        stuffList.removeAt(idx)
                    }
                }
                //cancel은 할 거 없음
            }
//            (binding.listView.adapter as ArrayAdapter<MemoItem>).notifyDataSetChanged()
            updateListView()
        }
    }


}