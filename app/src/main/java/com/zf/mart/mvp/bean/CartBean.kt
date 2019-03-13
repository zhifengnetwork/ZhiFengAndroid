package com.zf.mart.mvp.bean

data class CartBean(
    val shopList: ArrayList<ShopList>
)

data class ShopList(
    val shopName: String,
    val goodsList: ArrayList<CartGoodsList>
)

data class CartGoodsList(
    val id: Int,
    val goodsName: String
)