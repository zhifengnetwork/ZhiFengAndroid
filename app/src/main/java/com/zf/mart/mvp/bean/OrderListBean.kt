package com.zf.mart.mvp.bean

data class OrderListBean(
    val order_id: String,
    val order_amount: String,
    val add_time: String,
    val goods_name: String,
    val goods_num: String,
    val goods_price: String,
    val spec_key_name: String,
    val seller_name: String,
    val original_img: String
)

