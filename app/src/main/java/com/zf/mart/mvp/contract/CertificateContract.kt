package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter


interface CertificateContract {
    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {

        fun requestBankAccount(attribute: String)
    }
}