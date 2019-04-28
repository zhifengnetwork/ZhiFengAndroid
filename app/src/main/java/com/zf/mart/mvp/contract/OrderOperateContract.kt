package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter

interface OrderOperateContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setCancelOrder()

        fun setConfirmReceipt()
    }

    interface Presenter : IPresenter<View> {

        fun requestCancelOrder(orderId: String)

        fun requestConfirmReceipt(orderId: String)
    }

}