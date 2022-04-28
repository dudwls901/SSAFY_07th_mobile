package com.ssafy.gallery

import android.content.Context
import android.os.SystemClock
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GetCountWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val INPUT = "INPUT"
        const val OUTPUT = "OUTPUT"
    }

    override fun doWork(): Result {
        val inputCount = inputData.getInt(INPUT, 0)

        val outputCount = GalleryRepository.INSTANCE?.countPhoto()!!

        val outputData = Data.Builder()
            .putInt(OUTPUT, outputCount)
            .build()

        return Result.success(outputData)
    }
}