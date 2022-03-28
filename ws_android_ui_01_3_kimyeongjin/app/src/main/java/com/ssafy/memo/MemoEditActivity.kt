package com.ssafy.memo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ssafy.memo.databinding.ActivityMemoEditBinding
import com.ssafy.memo.databinding.ActivityMemoInfoBinding

class MemoEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancelButton.setOnClickListener {
            finish()
        }

        binding.submitButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val text = binding.memoContentEditText.text.toString()

            intent.putExtra("to_main", text)

            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }
}