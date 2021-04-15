package com.example.zoointro.widget

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.zoointro.R

class DataBindingHelper {
    companion object {
        /**
         * 讀取user avatar url
         */
        @BindingAdapter("url_img")
        @JvmStatic
        fun imgFromUrl(imageView: ImageView, url: String?) {
            url?.let {
                Glide.with(imageView.context)
                    .load(it)
                    .placeholder(R.drawable.no_image_icon)
                    .error(R.drawable.no_image_icon)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView)
            }
        }
    }
}