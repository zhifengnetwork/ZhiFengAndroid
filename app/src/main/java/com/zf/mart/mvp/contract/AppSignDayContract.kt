package com.zf.mart.mvp.contract

import com.zf.mart.base.BaseBean
import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AppSignDayBean

interface AppSignDayContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getAppSignDay(bean: AppSignDayBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestAppSignDay()
    }
}