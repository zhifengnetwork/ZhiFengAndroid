package com.zf.mart.utils

import android.content.Context
import android.widget.ImageView
import com.zf.mart.GlideApp


class GlideUtils {

    companion object {
        fun loadUrlImage(context: Context, url: String, imageView: ImageView) {
            GlideApp.with(context)
                .asBitmap()
                .load(url)
                .into(imageView)
        }
    }

}
