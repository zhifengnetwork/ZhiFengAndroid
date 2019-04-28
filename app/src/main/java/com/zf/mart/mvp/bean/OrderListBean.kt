package com.zf.mart.mvp.bean

data class OrderListBean(
        val order_id: String,
        val order_amount: String,
        val add_time: Long,
        val goods_name: String,
        val num: String,
        val goods_price: String,
        val spec_key_name: String,
        val store_name: String,
        val original_img: String,
        val pay_status:String, //pay_status 0未支付 1已支付 2部分支付 3已退款 4拒绝退款
        //订单状态
        val order_status: String,
        //发货状态
        val shipping_status: String,
        //是否货到付款
        val pay_code: String,
        val goods: List<OrderGoodsList>
)

data class OrderGoodsList(
        val goods_id: String,
        val original_img: String,
        val final_price: String,
        val goods_name: String,
        val spec_key_name: String,
        val goods_num: String
)

