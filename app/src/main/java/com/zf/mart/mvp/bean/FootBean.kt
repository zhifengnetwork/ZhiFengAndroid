package com.zf.mart.mvp.bean

data class FootBean(
    val monthList: List<MonthList>
)

data class MonthList(
    val month: String,
    val goodsList: List<GoodsList>
)

data class GoodsList(
    val name: String,
    val price: String
)