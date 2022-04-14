package com.ssafy.cleanstore.stuff

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.cleanstore.BoundServiceConnection
import com.ssafy.cleanstore.R
import com.ssafy.cleanstore.dto.Stuff

enum class ActionFlag {
    REGISTER, DELETE, MODIFY
}

private const val TAG = "StuffActivity_싸피"
class StuffActivity : AppCompatActivity() {

    private lateinit var btnRegister: Button
    private lateinit var stuffList: MutableList<Stuff>
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stuff)

        btnRegister = findViewById(R.id.btn_stuff_register)

        // ListView 연결
        listView = findViewById(R.id.listview_stuff_stuff)

        updateListView()

        // Intent 사용
        val stuffEditLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult() // StartActivityForResult 처리를 담당
        ) { activityResult -> // 넘어갔다 돌아오는 Result 값을 받는 인자
            if (activityResult.resultCode == Activity.RESULT_OK) {  // 잘 넘어온지 확인

                val intent = activityResult.data
                val actionFlag = intent!!.getSerializableExtra("OActionFlag")
                val position = intent.getIntExtra("OPosition", -1)

                when (actionFlag) {
                    ActionFlag.DELETE -> {
                        //stuffList.removeAt(position)
                        val stuff = stuffList[position]
                        BoundServiceConnection.myService.stuffDelete(stuff.id)
                    }

                    ActionFlag.REGISTER -> {
                        /*val name = intent.getStringExtra("OName")
                        val count = intent.getIntExtra("OCount", -1)
                        stuffList.add(Stuff(name, count))*/
                        val stuff = intent.getSerializableExtra("stuff") as Stuff
                        BoundServiceConnection.myService.stuffInsert(stuff)
                    }

                    ActionFlag.MODIFY -> {
//                        stuffList[position].apply {
//                            val name = intent.getStringExtra("OName")
//                            val count = intent.getIntExtra("OCount", -1)
//                            this.name = name
//                            this.count = count
//                        }
                        val stuff = stuffList[position]
                        /*stuff.name = intent.getStringExtra("OName")
                        stuff.count = intent.getIntExtra("OCount", -1)*/
                        val returnStuff = intent.getSerializableExtra("stuff") as Stuff
                        stuff.name = returnStuff.name
                        stuff.count = returnStuff.count
                        BoundServiceConnection.myService.stuffUpdate(stuff)
                    }
                }

                updateListView()
            }
        }

        // 이동위한 Intent 생성
        Intent(this, StuffEditActivity::class.java).apply {

            // 물품 등록
            btnRegister.setOnClickListener {
                // 값의 초기화
                /*putExtra("Name", "")
                putExtra("Count", -1)*/
                putExtra("stuff", Stuff("", -1))
                putExtra("ActionFlag", ActionFlag.REGISTER)
                stuffEditLauncher.launch(this)
            }

            // 물품 수정
            listView.setOnItemClickListener { parent, view, position, id ->
                val stuff = stuffList[position]

                // 원래 값 넘겨주기
                /*putExtra("Name", stuff.name)
                putExtra("Count", stuff.count)*/
                putExtra("stuff", Stuff(stuff.name, stuff.count))
                putExtra("Position", position)
                putExtra("ActionFlag", ActionFlag.MODIFY)
                stuffEditLauncher.launch(this)
            }
        }
    }

    // ListView와 Adapter 연결하고 ListView 내용 갱신하기
    private fun updateListView() {
        // ListView에 들어갈 ArrayList 생성
        stuffList = BoundServiceConnection.myService.stuffSelectAll()

        // Adapter 생성
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stuffList)

        // Adapter와 ListView 연결
        listView.adapter = adapter

        // ListView 변경 적용
        adapter.notifyDataSetChanged()
    }
}