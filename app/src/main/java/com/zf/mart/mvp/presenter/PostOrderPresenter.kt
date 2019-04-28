package com.zf.mart.mvp.presenter

import com.zf.mart.base.BasePresenter
import com.zf.mart.mvp.contract.PostOrderContract
import com.zf.mart.mvp.model.PostOrderModel
import com.zf.mart.net.exception.ExceptionHandle

/**
 * 结算订单 提交订单
 */
class PostOrderPresenter : BasePresenter<PostOrderContract.View>(), PostOrderContract.Presenter {

    private val model: PostOrderModel by lazy { PostOrderModel() }

    override fun requestPostOrder(
        act: Int,
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
        mobile: String
    ) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestPostOrder(
            act,
            address_id,
            invoice_title,
            taxpayer,
            invoice_desc,
            coupon_id,
            pay_points,
            user_money,
            user_note,
            pay_pwd,
            goods_id,
            goods_num,
            item_id,
            action,
            shop_id,
            take_time,
            consignee,
            mobile
        )
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            if (act == 0) {
                                //结算
                                setPostOrder(it.data)
                            } else if (act == 1) {
                                //提交订单
                                setConfirmOrder(it.data)
                            }

                        }
                        else -> showError(it.msg, it.status)
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

}