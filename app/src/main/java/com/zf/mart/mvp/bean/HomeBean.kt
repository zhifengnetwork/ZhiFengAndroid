package com.zf.mart.mvp.bean

data class HomeBean(
        val adlist: List<AdList>,
        val articlelist: List<ArticleList>,
        val flash_sale_goods: List<SecKillList>,
        val end_time: Long
)

data class AdList(
        val ad_code: String,
        val ad_link:String,
        val ad_id: String
)

data class ArticleList(
        val article_id: String,
        val title: String
)