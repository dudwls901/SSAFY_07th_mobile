package com.ssafy.memo

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ssafy.memo.databinding.CustomIntroBinding
import java.util.jar.Attributes

class CustomIntro(context: Context,val attrs: AttributeSet): LinearLayout(context, attrs) {
    //화면 상단 커스텀 뷰 구현
    private var binding: CustomIntroBinding

    init {
        val inflater = LayoutInflater.from(context)
//        val view = inflater.inflate(R.layout.custom_intro, this, false)
        binding = CustomIntroBinding.inflate(inflater)
        addView(binding.root)
        getAttrs()
    }


    // attrs.xml 파일로부터 속성 정보 확보 - typedArray
    private fun getAttrs() {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.customIntroView)
        setTypedArray(typedArray)
    }

    // 속성값을 view 요소들에 연결
    private fun setTypedArray(typedArray: TypedArray) {
        binding.introTextView.text = typedArray.getText(R.styleable.customIntroView_introText)
        binding.introImageView.setImageResource(
            typedArray.getResourceId(
                R.styleable.customIntroView_introImage,
                R.drawable.simple_logo
            )
        )
        typedArray.recycle()
    }

}