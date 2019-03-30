package com.zf.mart.mvp.contract

import com.zf.mart.base.BaseBean
import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import okhttp3.MultipartBody

interface UploadHeadContract {

    interface View : IBaseView {

        fun setHead(bean: BaseBean<String>)
        fun showError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun upLoadHead(head: MultipartBody.Part)
    }

}