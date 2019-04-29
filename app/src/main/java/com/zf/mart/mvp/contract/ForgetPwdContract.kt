package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter

interface ForgetPwdContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setContract()

        fun setChangePwd()

        fun setCode(msg:String)
    }

    interface Presenter : IPresenter<View> {
        fun requestContract(mobile: String, code: String)

        fun requestChangePwd(mobile: String, password: String, password2: String)

        fun requestCode(scene: Int, mobile: String)
    }

}