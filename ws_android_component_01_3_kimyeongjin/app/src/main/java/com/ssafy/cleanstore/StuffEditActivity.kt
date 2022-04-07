package com.ssafy.cleanstore

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ssafy.cleanstore.databinding.ActivityStuffEditBinding

class StuffEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStuffEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuffEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() = with(binding){

        val type = intent.getStringExtra("actionType")
        val idx = intent.getIntExtra("idx",-1)
        when(type){
            "edit"->{
                val name = intent.getStringExtra("name")
                val count = intent.getIntExtra("count",0)

                productNameEditText.setText(name)
                productCountEditText.setText(count.toString())
            }
            else->{
                deleteButton.visibility = View.GONE
            }
        }

        initButtons(type!!,idx)
    }

    private fun initButtons(type: String, idx: Int) = with(binding){

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
            intent.putExtra("name",productNameEditText.text.toString())
            intent.putExtra("count",productCountEditText.text.toString().toInt())
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