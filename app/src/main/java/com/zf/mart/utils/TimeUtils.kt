package com.zf.mart.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun getCountTime(time: Long): String {
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return format.format(time)
    }

}