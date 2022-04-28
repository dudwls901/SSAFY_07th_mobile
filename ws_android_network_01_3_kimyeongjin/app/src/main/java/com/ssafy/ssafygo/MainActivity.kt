package com.ssafy.ssafygo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ssafy.ssafygo.dao.StoreDAO
import com.ssafy.ssafygo.databinding.ActivityMainBinding
import com.ssafy.ssafygo.dto.StoreDTO
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class MainActivity() : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityMainBinding
    private lateinit var storeDao: StoreDAO

    private val PROGRESS_CNT = 10
    private val PROGRESS_TICK = 300

    val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDao()
        loadStore()
    }

    private fun getStoreInfo(): StoreDTO?{
        return storeDao.storeSelect(1)
    }

    private fun initDao(){
        storeDao = StoreDAO()
        storeDao.dbOpenHelper(this)
        storeDao.open()
    }

    private fun loadStore()= with(binding){

        launch {
            var progressState=0
            for(i in PROGRESS_CNT downTo 2){
                withContext(Dispatchers.Default){
                    progressState+=rand(4,15)
                }
                progressBar.progress = progressState
                progressStateNumTextView.text=progressBar.progress.toString()
                delay(1000L)
            }
            progressBar.progress = progressBar.max
            progressStateNumTextView.text=progressBar.max.toString()
            setStoreTv()
        }

    }

    private fun setStoreTv()= with(binding){
        val storeDto = getStoreInfo()
        if(storeDto!=null){
            storeNameTextView.text = storeDto.name
            storeTelTextView.text = storeDto.tel
            storeLatTextView.text = storeDto.lat.toString()
            storeLngTextView.text = storeDto.lng.toString()
        }
    }

    private fun rand(start: Int, end: Int) = Random.nextInt(end-start)
}