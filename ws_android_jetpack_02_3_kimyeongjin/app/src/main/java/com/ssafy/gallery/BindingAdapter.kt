package com.ssafy.gallery

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    val packageName = "com.ssafy.gallery"
    val resId = view.resources.getIdentifier(url, "drawable", packageName)
    view.setImageResource(resId)
}
