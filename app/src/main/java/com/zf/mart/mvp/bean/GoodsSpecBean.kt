package com.zf.mart.mvp.bean

data class GoodsSpecBean(
        val info: GoodsSpecInfo
)

data class GoodsSpecInfo(
        val price: String,
        val store_count: String,
        val spec_img: String
)