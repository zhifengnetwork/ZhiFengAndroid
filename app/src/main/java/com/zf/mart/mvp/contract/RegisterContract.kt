package com.zf.mart.mvp.contract

import com.zf.mart.base.BasePresenter
import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter

interface RegisterContract {

    interface View : IBaseView {
        fun registerError(message: String, errorCode: Int)
        fun registerSuccess()
    }

    interface Presenter : IPresenter<View> {
        fun requestRegister(phone: String, password: String)
    }

}