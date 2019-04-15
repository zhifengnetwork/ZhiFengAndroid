package com.zf.mart.mvp.bean

data class AuctionDetailBean(
        val auction: AuctionDetail,
        val bondCount: String,
        val bondUser: List<BondUser>
)

data class AuctionDetail(
        val id: String,
        val goods_id: String,
        val activity_name: String,
        val goods_name: String,
        val start_price: String,
        val start_time: Long,
        val end_time: Long,
        val increase_price: String,
        val auction_status: String,
        val delay_time: String,
        val delay_num: String,
        val original_img: String,
        val delay_end_time: String,
        val isBond: String
)

data class BondUser(
        val id: String,
        val user_id: String,
        val offer_price: String
)