package com.zf.mart.mvp.contract

import android.view.View
import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter

interface LoginContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun loginSuccess()
    }

    interface Presenter : IPresenter<View> {
        fun requestLogin(phone: String, pwd: String)

    }

}