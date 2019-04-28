package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.ShippingBean

interface ShippingContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setShipping(bean: ShippingBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestShipping(orderId: String)

    }

}