package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.MyVipBean

interface MyVipContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        fun getMyVip(bean: List<MyVipBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestMyVip()
    }
}