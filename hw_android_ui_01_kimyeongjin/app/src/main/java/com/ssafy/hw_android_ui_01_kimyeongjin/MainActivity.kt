package com.ssafy.hw_android_ui_01_kimyeongjin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ssafy.hw_android_ui_01_kimyeongjin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var movieList: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieList = ArrayList()
        initViews()
    }

    private fun initViews() = with(binding){

        editButton.setOnClickListener {
            editActivityListener.launch(Intent(this@MainActivity, EditActivity::class.java))
        }

        infoButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ListActivity::class.java)
            intent.putStringArrayListExtra("movieList",movieList)
            startActivity(intent)
        }

    }
    //람다
    private val editActivityListener : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            val intent = it.data
            val returnValue = intent!!.getStringExtra("movie")
            if(returnValue!=null) {
                binding.editText.setText(returnValue)
                movieList.add(returnValue)
            }
            else{
                binding.editText.setText("")
            }
        }
    }
}