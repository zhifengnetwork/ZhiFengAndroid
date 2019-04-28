package com.zf.mart.mvp.bean

data class SpecBean(
        val list: List<SpecList>
)

data class SpecList(
        val key: String,
        val item: String?,
        val item_id: String,
        val price:String?
)