package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.PostOrderBean

interface PostOrderContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setPostOrder(bean: PostOrderBean)

        fun setConfirmOrder(bean: PostOrderBean)
    }

    interface Presenter : IPresenter<View> {

        fun requestPostOrder(act: Int,
                             address_id: String,
                             invoice_title: String,
                             taxpayer: String,
                             invoice_desc: String,
                             coupon_id: String,
                             pay_points: String,
                             user_money: String,
                             user_note: String,
                             pay_pwd: String,
                             goods_id: String,
                             goods_num: String,
                             item_id: String,
                             action: String,
                             shop_id: String,
                             take_time: String,
                             consignee: String,
                             mobile: String)

    }

}