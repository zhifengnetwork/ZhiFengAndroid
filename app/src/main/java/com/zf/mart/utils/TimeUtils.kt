package com.zf.mart.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun getCountTime(time: Long): String {
        val format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return format.format(time)
    }

    //时间戳转时间
    fun myOrderTime(time: Long): String {
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        return format.format(time * 1000)
    }

    fun auctionTime(startTime: Long): String {
        //活动未开始，显示开始时间
        val format = SimpleDateFormat("MM月dd号 HH:mm", Locale.getDefault())
        return format.format(startTime * 1000)
    }

}