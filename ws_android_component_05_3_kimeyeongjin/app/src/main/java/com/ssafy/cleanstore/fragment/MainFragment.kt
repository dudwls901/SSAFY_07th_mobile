package com.ssafy.cleanstore.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ssafy.cleanstore.R
import com.ssafy.cleanstore.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentMainBinding.inflate(inflater, container, false).apply {
            tvMainStoreName.text = "싸피벅스"
            tvMainStoreCount.text = "1개"
        }.root
    }
}