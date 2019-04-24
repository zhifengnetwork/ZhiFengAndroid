package com.zf.mart.mvp.bean

data class GoodsAttrBean(
    val goods_attr_list:List<AttrList>,
    val goods_attribute:List<AttriBute>
)
data class AttrList(
    val goods_attr_id:String,
    val goods_id:String,
    val attr_id:String,
    val attr_value:String,
    val attr_price:String
)
data class AttriBute(
    val attr_id:String,
    val attr_name:String
)