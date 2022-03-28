package com.ssafy.memo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.memo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //메모 인포 화면 이동 단방향
        binding.memoInfoButton.setOnClickListener {
            startActivity(Intent(this, MemoInfoActivity::class.java))
        }
        //메모 편집 화면 이동 양방향
        binding.memoEditButton.setOnClickListener {
            memoEditActivitListener.launch(Intent(this, MemoEditActivity::class.java))
        }

    }
    //람다
    private val memoEditActivitListener : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            val intent = it.data
            val returnValue = intent!!.getStringExtra("to_main")
            binding.memoEditText.setText(returnValue)
        }
    }
}