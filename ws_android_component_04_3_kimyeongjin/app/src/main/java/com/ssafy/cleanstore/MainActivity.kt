package com.ssafy.cleanstore

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.tabs.TabLayout
import com.ssafy.cleanstore.databinding.ActivityMainBinding
import com.ssafy.cleanstore.dto.Stuff
import com.ssafy.cleanstore.fragment.MainFragment
import com.ssafy.cleanstore.fragment.StoreFragment
import com.ssafy.cleanstore.service.BoundService
import com.ssafy.cleanstore.stuff.StuffActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment

        val navController = navHostFragment.navController

        //네비게이션과 바텀네비 연결
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

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