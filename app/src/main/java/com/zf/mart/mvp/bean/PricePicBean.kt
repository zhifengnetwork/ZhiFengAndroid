package com.zf.mart.mvp.bean

data class PricePicBean(
    val info:PricePic
)
data class PricePic(
    val price:String,
    val store_count:String,
    val spec_img:String
)