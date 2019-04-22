package com.zf.mart.mvp.bean

data class ShopList(
    val seller_name: String, //商家名字
    var data: ArrayList<CartGoodsList>, //该商家的商品列表
    var selected: String //商家是否选中
)

data class CartGoodsList(
    val id: String,          //商品id
    val goods_price: String,
    val goods_num: String,
    val goods: Goods,
    val cat_id: String,
    var selected: String //商品是否选中
)

data class Goods(
    val goods_name: String, //商品名字
    val original_img: String, //商品图片
    val cat_id: String,
    val goods_id: String

)