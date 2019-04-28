package com.zf.mart.mvp.bean

data class MyFollowShop(
    val list:List<FollowShopList>
)
data class FollowShopList(
    val collect_id:String,
    val seller_id:String,
    val seller_name:String,
    val avatar:String
)