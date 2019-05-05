package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import okhttp3.MultipartBody

interface EvaluateContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setEvaluate()

        fun setUploadImg(url: String)

    }

    interface Presenter : IPresenter<View> {

        fun requestEvaluate(info: String, orderId: String)

        fun requestUploadImg(partList: MultipartBody.Part)
    }

}