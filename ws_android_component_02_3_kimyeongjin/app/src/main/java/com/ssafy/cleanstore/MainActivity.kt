package com.ssafy.cleanstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.ssafy.cleanstore.databinding.ActivityMainBinding
import com.ssafy.cleanstore.fragment.MainFragment
import com.ssafy.cleanstore.fragment.StoreFragment
import com.ssafy.cleanstore.stuff.StuffActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragments(MainFragment.newInstance("homefragment","first"))
        initViews()
    }

    private fun initViews() = with(binding) {

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {
                    0 -> {
                        replaceFragments(MainFragment.newInstance("homeFragment", "homretabclicked"))
                    }
                    1 -> {
                        replaceFragments(StoreFragment.newInstance())
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }

    private fun replaceFragments(fragment: Fragment) = with(binding) {
        supportFragmentManager.beginTransaction().apply {
            replace(frameLayout.id, fragment)
            commit()
        }
    }
}