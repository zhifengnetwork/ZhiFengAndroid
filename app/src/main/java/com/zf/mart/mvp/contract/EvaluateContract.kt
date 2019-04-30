package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import okhttp3.RequestBody

interface EvaluateContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setEvaluate()

    }

    interface Presenter : IPresenter<View> {

        fun requestEvaluate(info: String)

    }

}