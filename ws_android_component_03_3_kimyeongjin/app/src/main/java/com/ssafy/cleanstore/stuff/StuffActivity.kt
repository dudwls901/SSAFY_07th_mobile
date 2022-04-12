package com.ssafy.cleanstore.stuff

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.cleanstore.R
import com.ssafy.cleanstore.dto.Stuff
import com.ssafy.cleanstore.service.BoundService
import com.ssafy.cleanstore.service.MyLocalBinder

enum class ActionFlag {
    REGISTER, DELETE, MODIFY
}

private const val TAG = "StuffActivity_싸피"
class StuffActivity : AppCompatActivity() {

    private lateinit var btnRegister: Button
    private lateinit var stuffList: MutableList<Stuff>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stuff)

        btnRegister = findViewById(R.id.btn_stuff_register)

        // ListView에 들어갈 ArrayList 생성
        stuffList = arrayListOf()
        getList()
        // Adapter 생성
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, stuffList)

        // ListView 연결
        val listView: ListView = findViewById(R.id.listview_stuff_stuff)

        // Adapter와 ListView 연결
        listView.adapter = adapter

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
                        deleteStuff(position)
                    }

                    ActionFlag.REGISTER -> {
                        val name = intent.getStringExtra("OName")
                        val count = intent.getIntExtra("OCount", -1)
                        insertStuff(name!!, count)
                    }

                    ActionFlag.MODIFY -> {
                        val name = intent.getStringExtra("OName")
                        val count = intent.getIntExtra("OCount", -1)
                        updateStuff(position, name!!, count)
                    }
                }
                //리스트 갱신
                getList()
                Log.d(TAG, "onCreate stuff: ${stuffList}")
                adapter.notifyDataSetChanged()
            }
        }

        // 이동위한 Intent 생성
        Intent(this, StuffEditActivity::class.java).apply {

            // 물품 등록
            btnRegister.setOnClickListener {
                // 값의 초기화
                putExtra("Name", "")
                putExtra("Count", -1)
                putExtra("ActionFlag", ActionFlag.REGISTER)
                stuffEditLauncher.launch(this)
            }

            // 물품 수정
            listView.setOnItemClickListener { parent, view, position, id ->
                val stuff = stuffList[position]

                // 원래 값 넘겨주기
                putExtra("Name", stuff.name)
                putExtra("Count", stuff.count)
                putExtra("Position", position)
                putExtra("ActionFlag", ActionFlag.MODIFY)
                stuffEditLauncher.launch(this)
            }
        }
    }
    private fun getList(){
        Log.d(TAG, "getList: stuff ${MyServiceConnection.isBound}")
        if(MyServiceConnection.isBound){
            stuffList = MyServiceConnection.myService.stuffDao.stuffSelectAll()
        }
    }
    private fun insertStuff(name: String, count: Int){
        if(MyServiceConnection.isBound){
            MyServiceConnection.myService.stuffDao.stuffInsert(Stuff(name, count))
        }
    }
    private fun deleteStuff(id: Int){
        if(MyServiceConnection.isBound){
            MyServiceConnection.myService.stuffDao.stuffDelete(id)
        }
    }
    private fun updateStuff(id: Int, name: String, count: Int){
        if(MyServiceConnection.isBound){
            MyServiceConnection.myService.stuffDao.stuffUpdate(Stuff(id,name,count))
        }
    }


    override fun onStart() {
        super.onStart()
        if(!MyServiceConnection.isBound){
            Intent(this, BoundService::class.java).also{
                //결과로 MyServiceConnection의 onServiceConnected 호출
                bindService(it, MyServiceConnection, Context.BIND_AUTO_CREATE)
            }
        }
        Log.d(TAG, "onStart stuff: ${MyServiceConnection.isBound}")
    }

    override fun onDestroy() {
        super.onDestroy()
        if(MyServiceConnection.isBound){
            unbindService(MyServiceConnection)
            MyServiceConnection.isBound = false
        }
        Log.d(TAG, "onStop stuff: ${MyServiceConnection.isBound}")
    }
}

// 서비스가 반환한 바인더 객체(service: IBinder)를 이용해
// 서비스에 접속하거나 접속 종료를 처리하는 ServiceConnection 객체를 생성하기 위한 클래스 생성
object MyServiceConnection: ServiceConnection{

    //바인딩한 서비스 객체
    lateinit var myService: BoundService

    //바인딩 성공 여부
    var isBound = false
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MyLocalBinder
        myService = binder.getService()
        isBound = true
        Log.d(TAG, "onServiceConnected: stuff $isBound")
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        isBound = false
        Log.d(TAG, "onServiceDisconnected: stuff $isBound")
    }
}