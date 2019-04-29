package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter

interface HotSearchContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setHotSearch(bean: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestHotSearch()

    }

}