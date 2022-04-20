package com.ssafy.cleanstore

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.ssafy.cleanstore.databinding.ActivityMainBinding
import com.ssafy.cleanstore.dto.Stuff
import com.ssafy.cleanstore.fragment.MainFragment
import com.ssafy.cleanstore.fragment.StoreFragment
import com.ssafy.cleanstore.service.BoundService
import com.ssafy.cleanstore.stuff.StuffActivity
private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 네비게이션 호스트
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController

        // 바인딩
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        val userImageUrl = intent.getStringExtra("userImageUrl")
        val userName = intent.getStringExtra("userName")
        initToolbar(userImageUrl, userName)

    }

    private fun initToolbar(userImageUrl: String?, userName: String?) {

//            setSupportActionBar(binding.toolbarMain)
//            supportActionBar!!.setDisplayShowTitleEnabled(false)
//            supportActionBar!!.setDisplayShowCustomEnabled(true)
        binding.tvToolbar.text = userName
//            supportActionBar!!.hide()
//            supportActionBar!!.setIcon(R.drawable.my_message_bg)
//            binding.toolbarMain.visibility = View.INVISIBLE
        //프로필 사진 설정
        Log.d(TAG, "initToolbar: $userImageUrl $userName")
        val uri =Uri.parse(userImageUrl)
        Glide.with(this)
            .load(uri)
            .transform(CenterCrop(), RoundedCorners(30))
            .into(binding.ivToolbar)
    }

    override fun onStart() {
        super.onStart()

        if (!BoundServiceConnection.isBound) {
            Intent(this, BoundService::class.java).also {
                bindService(it, BoundServiceConnection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (BoundServiceConnection.isBound) {
            unbindService(BoundServiceConnection)
        }
    }
}

// Service Bind
object BoundServiceConnection : ServiceConnection {

    lateinit var myService: BoundService
    var isBound = false

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as BoundService.MyBinder
        myService = binder.getService()
        isBound = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        isBound = false
    }
}