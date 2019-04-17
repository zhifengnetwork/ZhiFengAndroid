package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.DiscountBean

interface DiscountContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setDiscount(bean: List<DiscountBean>)

        fun setEmpty()
    }

    interface Presenter : IPresenter<View> {
        fun requestDiscount(status: String)

    }

}