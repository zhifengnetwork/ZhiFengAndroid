package com.zf.mart.mvp.bean

data class GoodEvaBean(
        val commentlist: List<GoodEvaList>
)

data class GoodEvaList(
        val comment_id: String,
        val goods_id: String,
        val email: String,
        val username: String,
        val content: String,
        val add_time: Long,
        val ip_address: String,
        val is_show: String,
        val parent_id: String,
        val user_id: String,
        val head_pic: String,
        val goods_rank: Int,
        val service_rank: Int,
        val deliver_rank: Int,
        val order_id: String,
        val img: List<String>?
)