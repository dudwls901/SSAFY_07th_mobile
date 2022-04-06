package com.ssafy.memo

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ssafy.memo.databinding.CustomItemBinding

class CustomRecyclerView (context: Context, val attrs: AttributeSet): LinearLayout(context, attrs) {
    private var binding: CustomItemBinding

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_item, this, false)
        binding = CustomItemBinding.bind(view)
        addView(binding.root)
    }


}