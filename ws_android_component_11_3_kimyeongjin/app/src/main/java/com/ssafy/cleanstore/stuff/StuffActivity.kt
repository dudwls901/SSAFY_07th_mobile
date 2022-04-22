package com.ssafy.cleanstore.stuff

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.cleanstore.BoundServiceConnection
import com.ssafy.cleanstore.R
import com.ssafy.cleanstore.databinding.ActivityStuffBinding
import com.ssafy.cleanstore.dto.Stuff
import kotlin.math.sqrt

enum class ActionFlag {
    REGISTER, DELETE, MODIFY
}

private const val TAG = "StuffActivity_싸피"
class StuffActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStuffBinding
    private lateinit var stuffList: MutableList<Stuff>
    private lateinit var listView: ListView

    //센서
    private lateinit var accelerometerSensor: Sensor
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorEventListener: SensorEventListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuffBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // ListView 연결
        listView = findViewById(R.id.listview_stuff_stuff)

        //센서 바인딩
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorEventListener = AccelerometerListener()

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
            binding.btnRegister.setOnClickListener {
                // 값의 초기화
                /*putExtra("Name", "")
                putExtra("Count", -1)*/
                putExtra("stuff", Stuff("", -1,""))
                putExtra("ActionFlag", ActionFlag.REGISTER)
                stuffEditLauncher.launch(this)
            }

            // 물품 수정
            listView.setOnItemClickListener { parent, view, position, id ->
                val stuff = stuffList[position]

                // 원래 값 넘겨주기
                /*putExtra("Name", stuff.name)
                putExtra("Count", stuff.count)*/
                putExtra("stuff", Stuff(stuff.name, stuff.count, stuff.regDate))
                putExtra("Position", position)
                putExtra("ActionFlag", ActionFlag.MODIFY)
                stuffEditLauncher.launch(this)
            }
        }
    }

    //센서 리스너 등록
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            sensorEventListener,
            accelerometerSensor,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    //센서 리스너 해제
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(sensorEventListener)
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

    inner class AccelerometerListener : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            var sum =0.0
            event.values.forEach {
                sum += it*it
            }
            if(sqrt(sum)>30) {
                Toast.makeText(this@StuffActivity, "리스트가 갱신되었습니다. ${event.values.sum()}", Toast.LENGTH_SHORT).show()
                updateListView()
            }
            Log.e(
                TAG,
                "           (x):" + String.format(
                    "%.4f",
                    event.values[0]
                ) + "           (y):" + String.format(
                    "%.4f",
                    event.values[1]
                ) + "           (z):" + String.format(
                    "%.4f",
                    event.values[2]
                )
            +"$sum"
            )
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

}