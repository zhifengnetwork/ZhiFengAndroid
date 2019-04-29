package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.HomeBean

interface HomeContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setHome(bean: HomeBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestHome()

    }

}