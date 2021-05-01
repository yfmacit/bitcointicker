package com.yfmacit.cryptocurrencypricetracker.utils.bindingadapters

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.yfmacit.cryptocurrencypricetracker.R

@BindingAdapter("app:imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (url != null && url != "") {
        val context: Context = imageView.context
        Glide.with(context).load(url).placeholder(R.drawable.image_loader).into(imageView)
    }
}