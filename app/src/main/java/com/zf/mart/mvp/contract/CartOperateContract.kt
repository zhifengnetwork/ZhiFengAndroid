package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter

interface CartOperateContract {

    interface View : IBaseView {

        fun cartOperateError(msg: String, errorCode: Int)

        fun setCount()
    }

    interface Presenter : IPresenter<View> {
        fun requestCount(id: String, num: String)

    }

}