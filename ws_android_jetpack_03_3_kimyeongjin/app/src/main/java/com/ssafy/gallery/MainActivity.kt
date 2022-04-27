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
import androidx.navigation.fragment.NavHostFragment
import androidx.work.*
import com.ssafy.gallery.databinding.ActivityMainBinding

private const val TAG = "MainActivity_sss"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private var backButtonTime = 0L
    private lateinit var workManager: WorkManager
    companion object {
        private const val WORK_TAG = "WORK_TAG"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
                                Toast.makeText(this@MainActivity, "새로 변동된 사항이 있습니다.\n${GalleryFragment.photoListSize} to ${result} ", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@MainActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                            }
                            else{
                                Toast.makeText(this@MainActivity, "새로 변동된 사항이 없습니다. ", Toast.LENGTH_SHORT).show()
                            }
                            //갱신
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

//    override fun onBackPressed() {
//        val currentTime = System.currentTimeMillis()
//        val gapTime = currentTime - backButtonTime
//        val currentFragment = supportFragmentManager.findFragmentById(galleryFragment.id)
//        //갤러리 화면이면 뒤로가기 시 앱 종료
//        if(currentFragment==galleryFragment){
//            if(gapTime in 0..2000){
//                //2초 안에 두 번 뒤로가기 누를 시 앱 종료
//                Log.d(TAG, "onBackPressed: 여기????")
//                finishAndRemoveTask()
//            }
//            else{
//                backButtonTime = currentTime
//                Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
//            }
//        }
//        //갤러리 화면이 아니면
//        else{
//            replaceFragment(galleryFragment)
//        }
//
//
//    }

}