package com.ssafy.gallery

import android.app.Notification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.navigation.NavController

import androidx.navigation.fragment.NavHostFragment
import androidx.work.*
import com.ssafy.gallery.databinding.ActivityMainBinding

private const val TAG = "MainActivity_sss"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var backButtonTime = 0L
    private lateinit var workManager: WorkManager
    //navigation
    private lateinit var navController: NavController

    companion object {
        private const val WORK_TAG = "WORK_TAG"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment

        navController = navHostFragment.navController
        

        workManager = WorkManager.getInstance(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refreshMenu->{

                val inputData = Data.Builder()
                    .putInt(GetCountWorker.INPUT, GalleryFragment.photoListSize)
                    .build()

                val getCountRequest = OneTimeWorkRequestBuilder<GetCountWorker>()
                    .setInputData(inputData)
                    .addTag(WORK_TAG)
                    .build()



                workManager
                    .beginWith(getCountRequest)
                    .enqueue()

                val status = workManager.getWorkInfoByIdLiveData(getCountRequest.id)
                status.observe(this) { info ->
                    val workFinished = info!!.state.isFinished
                    val result = info.outputData.getInt(GetCountWorker.OUTPUT, 0)
                    when (info.state) {
                        WorkInfo.State.SUCCEEDED,
                        WorkInfo.State.FAILED -> {
                            Log.d(TAG, "observe: work status: ${info.state}, before : ${GalleryFragment.photoListSize} result: $result, finished: $workFinished")
                            if(result!= GalleryFragment.photoListSize){
                                Toast.makeText(this@MainActivity, "?????? ????????? ????????? ????????????.\n${GalleryFragment.photoListSize} to ${result} ", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@MainActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                            }
                            else{
                                Toast.makeText(this@MainActivity, "?????? ????????? ????????? ????????????. ", Toast.LENGTH_SHORT).show()
                            }
                            //??????
                            GalleryFragment.photoListSize = result
                        }
                        else -> {
                            "work status: ${info.state}, finished: $workFinished"
                        }
                    }
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val gapTime = currentTime - backButtonTime
//        val currentFragment = supportFragmentManager.findFragmentById(galleryFragment.id)
        //????????? ???????????? ???????????? ??? ??? ??????
        if(navController.currentDestination!!.id==R.id.galleryFragment){
            if(gapTime in 0..2000){
                //2??? ?????? ??? ??? ???????????? ?????? ??? ??? ??????
                finishAndRemoveTask()
            }
            else{
                backButtonTime = currentTime
                Toast.makeText(this, "???????????? ????????? ??? ??? ??? ????????? ???????????????.", Toast.LENGTH_SHORT).show()
            }
        }
        //????????? ????????? ?????????
        else{
//            replaceFragment(galleryFragment)
            super.onBackPressed()
        }
    }

}