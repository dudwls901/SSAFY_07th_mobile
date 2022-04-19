package com.ssafy.cleanstore.stuff

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ssafy.cleanstore.R
import com.ssafy.cleanstore.databinding.ActivityStuffEditBinding
import com.ssafy.cleanstore.dto.Stuff
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "StuffEditActivity_싸피"
class StuffEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuffEditBinding
    private val dateShort = DateFormat.getDateInstance(DateFormat.SHORT, Locale.KOREA)
    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuffEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //calendarView에는 날짜 long값을 세팅한다.
        binding.calendarView.date = cal.time.time  //cal.time은 Date를 리턴하고, Date.time은 long값 리턴.
        binding.ymdTextView.text = dateShort.format(Date(cal.timeInMillis))


        binding.calendarView.date = cal.timeInMillis
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Log.d(TAG, "onCreate: $year $month $dayOfMonth")
            // 캘린더 인스턴스에 CalendarView 에서 선택한 날짜 세팅
            cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)

            // TextView 에 날짜 세팅하기
            binding.ymdTextView.text = dateShort.format(cal.time)
        }

        val today = Date()

        Log.d(TAG, "SHORT : ${dateShort.format(today)}")


        // 버튼
        val btnSave = findViewById<Button>(R.id.btn_stuff_edit_save)
        val btnDelete = findViewById<Button>(R.id.btn_stuff_edit_delete)
        val btnCancel = findViewById<Button>(R.id.btn_stuff_edit_cancel)

        // List에서 넘어오는 값
//        val name = intent.getStringExtra("Name")
//        val count = intent.getIntExtra("Count", -1)
        val stuff = intent.getSerializableExtra("stuff") as Stuff
        val position = intent.getIntExtra("Position", -1)
        val actionFlag = intent.getSerializableExtra("ActionFlag")

        when (actionFlag) {
            ActionFlag.REGISTER -> {
                btnDelete.visibility = View.GONE
            }
            ActionFlag.MODIFY -> {
                btnDelete.visibility = View.VISIBLE

                // 넘어온 값 보여주기
                binding.etStuffName.setText(stuff.name)
                binding.etStuffCount.setText(stuff.count.toString())
                binding.ymdTextView.text = stuff.regDate
                var (y,m,d) = stuff.regDate.split('/').map{it.toInt()}
                y+= 2000
                cal.set(y,m,d)
                binding.calendarView.date = cal.timeInMillis
            }
        }

        // 저장
        btnSave.setOnClickListener {

            // 값이 다 채워졌다면
            if (binding.etStuffName.text!=null && binding.etStuffCount.text!=null) {

                // 값 전달 Intent 생성
                Intent(this, StuffActivity::class.java).apply {

                    // 전달할 값 넣기
//                    putExtra("OName", etStuffName.text.toString())
//                    putExtra("OCount", etStuffCount.text.toString().toInt())
                    val stuff = Stuff(binding.etStuffName.text.toString(), binding.etStuffCount.text.toString().toInt(), dateShort.format(cal.time) )
                    putExtra("stuff", stuff)
                    putExtra("OPosition", position)
                    putExtra("OActionFlag", actionFlag)
                    setResult(Activity.RESULT_OK, this)
                }

                // 해당 엑티비티 종료
                finish()
            }
            else {
                Toast
                    .makeText(this, "모든 빈칸을 채워주세요", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // 삭제
        btnDelete.setOnClickListener {

            // 값 전달 Intent 생성
            Intent(this, StuffActivity::class.java).apply {

                // 전달할값 넣기
                putExtra("OPosition", position)
                putExtra("OActionFlag", ActionFlag.DELETE)
                setResult(Activity.RESULT_OK, this)
            }

            // 해당 엑티비티 종료
            finish()
        }

        // 취소
        btnCancel.setOnClickListener {
            // 해당 엑티비티 종료
            finish()
        }
    }
}