package com.zf.mart.mvp.bean

data class MaterialClassifyBean(
    val list: List<MaterialClassifyList>
)

data class MaterialClassifyList(
    val cat_id: String,
    val cat_name: String
)