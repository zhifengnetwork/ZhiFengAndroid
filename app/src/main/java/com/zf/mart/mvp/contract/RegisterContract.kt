package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter

interface RegisterContract {

    interface View : IBaseView {

        fun showError(message: String, errorCode: Int)

        fun setCode(msg:String)

        fun setRegister()

    }

    interface Presenter : IPresenter<View> {

        fun requestRegister(nickname: String, username: String, password: String, password2: String, code: String)

        fun requestCode(scene: Int, mobile: String)
    }

}