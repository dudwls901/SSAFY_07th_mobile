package com.ssafy.hw_android_ui_01_kimyeongjin

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.hw_android_ui_01_kimyeongjin.databinding.ActivityEditBinding
import com.ssafy.hw_android_ui_01_kimyeongjin.databinding.ActivityMainBinding

class EditActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews()= with(binding){
        submitButton.setOnClickListener {
            intent.putExtra("movie",editText.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        cancelButton.setOnClickListener {
            finish()
        }
    }
}