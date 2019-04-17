package com.zf.mart.mvp.bean

data class AuctionPriceBean(
        val buy_num: String,
        val price_num: String,
        val max_price: List<PriceList>
)

data class PriceList(
        val id: String,
        val user_id: String,
        val offer_price: String,
        val offer_time: String,
        val user_name: String,
        val head_pic: String,
        val pay_status: String
)