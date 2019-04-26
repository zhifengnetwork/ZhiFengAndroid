package com.zf.mart.mvp.bean

data class CartBean(
    var list: ArrayList<CartGoodsList>,
    val seller_name: String, //商家名字
    var selected: String? = "", //1选中 0非选中
    val cart_price_info: CartPrice? = null,
    val selected_flag: CheckAllFlag? = null
)

data class CheckAllFlag(
    val all_flag: Int //1全选 2非全选
)

//商品列表，但是也要有商家的名字
data class CartGoodsList(
    val seller_id: String,
    val seller_name: String, //商家名字
    val id: String,          //商品id
    val goods_price: String,
    var goods_num: String,
    val goods: Goods,
    val cat_id: String,
    var selected: String //商品是否选中
)

data class Goods(
    val goods_name: String, //商品名字
    val original_img: String, //商品图片
    val cat_id: String,
    val goods_id: String
)

//data class ShopList(
//        val seller_name: String, //商家名字
////        var data: List<CartGoodsList>, //该商家的商品列表
//        var selected: String //商家是否选中
////        val id: String,  //商品id
////        val goods_price: String,
////        val goods_num: String,
////        val goods: Goods,
////        val cat_id: String
//)