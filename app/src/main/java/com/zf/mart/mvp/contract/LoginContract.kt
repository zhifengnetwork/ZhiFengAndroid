package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.LoginBean

interface LoginContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun loginSuccess(bean: LoginBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestLogin(phone: String, pwd: String)

    }

}