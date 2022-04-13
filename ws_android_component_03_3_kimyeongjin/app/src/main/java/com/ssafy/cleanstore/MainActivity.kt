package com.ssafy.cleanstore

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.ssafy.cleanstore.databinding.ActivityMainBinding
import com.ssafy.cleanstore.fragment.MainFragment
import com.ssafy.cleanstore.fragment.StoreFragment
import com.ssafy.cleanstore.service.BoundService


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(MainFragment())

        ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            tabLayoutMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val selectedFragment = when (tab!!.position) {
                        0 -> MainFragment()
                        1 -> StoreFragment()
                        else -> null
                    }
                    replaceFragment(selectedFragment!!)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun replaceFragment(f: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout_main, f)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        if(!MyServiceConnection.isBound){
            Intent(this, BoundService::class.java).also{
                //결과로 MyServiceConnection의 onServiceConnected 호출
                bindService(it, MyServiceConnection, Context.BIND_AUTO_CREATE)
            }
        }
        Log.d(TAG, "onStart stuff: ${MyServiceConnection.isBound}")
    }

    override fun onDestroy() {
        super.onDestroy()
        if(MyServiceConnection.isBound){
            unbindService(MyServiceConnection)
        }
        Log.d(TAG, "onStop stuff: ${MyServiceConnection.isBound}")
    }
}

// 서비스가 반환한 바인더 객체(service: IBinder)를 이용해
// 서비스에 접속하거나 접속 종료를 처리하는 ServiceConnection 객체를 생성하기 위한 클래스 생성
object MyServiceConnection: ServiceConnection {

    //바인딩한 서비스 객체
    lateinit var myService: BoundService
    //바인딩 성공 여부
    var isBound = false

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as BoundService.MyLocalBinder
        myService = binder.getService()
        isBound = true
        Log.d(TAG, "onServiceConnected: stuff $isBound")
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        isBound = false
        Log.d(TAG, "onServiceDisconnected: stuff $isBound")
    }
}