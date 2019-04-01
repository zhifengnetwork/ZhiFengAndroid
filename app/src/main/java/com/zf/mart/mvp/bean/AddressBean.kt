package com.zf.mart.mvp.bean

data class AddressBean(
    val address_id: String,
    val user_id: String,
    val consignee: String,
    val email: String,
    val country: String,
    val province: String,
    val city: String,
    val district: String,
    val twon: String,
    val address: String,
    val zipcode: String,
    val mobile: String,
    val is_default: String,
    val longitude: String,
    val latitude: String
)