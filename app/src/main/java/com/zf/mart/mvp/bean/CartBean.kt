package com.zf.mart.mvp.bean

data class CartBean(
    val shopList: ArrayList<ShopList>
)

data class ShopList(
    val shopName: String, //商家名字
    var goodsList: ArrayList<CartGoodsList>, //该商家的商品列表
    var ifCheck: Boolean = false //商家是否选中
)

data class CartGoodsList(
    val id: Int,          //商品id
    val goodsName: String,   //商品名字
    var ifCheck: Boolean = false  //商品是否选中
)