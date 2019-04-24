package com.zf.mart.mvp.bean

data class MyFootBean(
    val visit_id:String,
    val goods_id:String,
    val visittime:String,
    val goods_name:String,
    val shop_price:String,
    val cat_id:String,
    val comment_count:String,
    val sales_sum:String,
    val original_img:String,
    val date:String
)
data class MonthList(
    val data: String,
    val goodsList: List<MyFootBean>
)
