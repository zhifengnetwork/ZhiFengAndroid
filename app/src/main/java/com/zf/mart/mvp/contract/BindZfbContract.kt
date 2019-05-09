package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter

interface BindZfbContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun bindZfbSuccess(msg: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestBindZfb(zfb_account: String, realname: String)
    }
}