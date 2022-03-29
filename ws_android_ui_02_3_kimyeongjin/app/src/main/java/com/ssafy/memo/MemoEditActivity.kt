package com.ssafy.memo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.ssafy.memo.databinding.ActivityMemoEditBinding
import com.ssafy.memo.util.Utils

private val TAG = MemoEditActivity::class.java.simpleName

class MemoEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemoEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemoEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()


    }

    private fun initViews() = with(binding){

        val type = intent.getStringExtra("actionType")
        val idx = intent.getIntExtra("idx",-1)
        when(type){
            "edit"->{
                val head = intent.getStringExtra("head")
                val content = intent.getStringExtra("content")
                val date = intent.getStringExtra("date")

                memoHeadEditText.setText(head)
                memoContentEditText.setText(content)
                memoTimeEditText.setText(date)
            }
            else->{
                timeLayout.visibility = View.GONE
                deleteButton.visibility = View.GONE
            }
        }

        initButtons(type,idx)

    }

    private fun initButtons(type: String?, idx: Int) = with(binding){
        Log.d(TAG, "initButtons: $type $idx")
        val intent = Intent(this@MemoEditActivity, MainActivity::class.java)
        saveButton.setOnClickListener {
            when(type){
                "save"->{
                    intent.putExtra("actionType","save")
                }
                "edit"->{
                    if(idx>=0) {
                        intent.putExtra("idx",idx)
                        intent.putExtra("actionType", "edit")
                    }
                }
            }
            intent.putExtra("head",memoHeadEditText.text.toString())
            intent.putExtra("content",memoContentEditText.text.toString())
            intent.putExtra("date", Utils.formatter().format(System.currentTimeMillis()))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        deleteButton.setOnClickListener {
            if(idx>=0) {
                intent.putExtra("actionType", "delete")
                intent.putExtra("idx",idx)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        cancelButton.setOnClickListener {
            finish()
        }
    }
}