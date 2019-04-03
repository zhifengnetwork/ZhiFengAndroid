package com.zf.mart.mvp.bean

data class OrderDetailBean(
    val order_id: String,
    val order_sn: String,
    val order_status: String,
    val consignee: String,
    val country: String,
    val province: String,
    val city: String,
    val district: String,
    val twon: String,
    val address: String,
    val seller_name: String,
    val original_img: String,
    val goods_name: String,
    val spec_key_name: String,
    val goods_price: String,
    val goods_num: String,
    val shipping_price: String,
    val total_amount: String,
    val order_amount: String,
    val pay_time: String,
    val pay_name: String,
    val mobile: String,
    val user_money: String
)