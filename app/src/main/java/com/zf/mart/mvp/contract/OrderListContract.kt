package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.OrderListBean

interface OrderListContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setOrderList(bean: OrderListBean)

    }

    interface Presenter : IPresenter<View> {

        fun requestOrderList()

    }

}