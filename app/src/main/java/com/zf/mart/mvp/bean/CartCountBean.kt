package com.zf.mart.mvp.bean

data class CartCountBean(
        val cartId: String,
        val sum: String,
        val shopPosition: Int? = 0,
        val goodsPosition: Int? = 0
)