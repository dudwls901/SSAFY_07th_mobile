package com.ssafy.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ssafy.gallery.databinding.ActivityMainBinding

private const val TAG = "MainActivity_sss"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var backButtonTime = 0L
    private lateinit var galleryFragment: GalleryFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        galleryFragment = GalleryFragment.newInstance(this)

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, galleryFragment).commit()


    }


    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment).commit()
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val gapTime = currentTime - backButtonTime
        val currentFragment = supportFragmentManager.findFragmentById(galleryFragment.id)
        //갤러리 화면이면 뒤로가기 시 앱 종료
        if(currentFragment==galleryFragment){
            if(gapTime in 0..2000){
                //2초 안에 두 번 뒤로가기 누를 시 앱 종료
                Log.d(TAG, "onBackPressed: 여기????")
                finishAndRemoveTask()
            }
            else{
                backButtonTime = currentTime
                Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
        }
        //갤러리 화면이 아니면
        else{
            replaceFragment(galleryFragment)
        }


    }

}