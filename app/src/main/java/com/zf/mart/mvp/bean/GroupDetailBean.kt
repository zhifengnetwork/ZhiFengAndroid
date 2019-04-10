package com.zf.mart.mvp.bean

data class GroupDetailBean(
    val team_found_num: String,
    val info: GroupDetailInfo
)

data class GroupDetailInfo(
    val act_name: String
)