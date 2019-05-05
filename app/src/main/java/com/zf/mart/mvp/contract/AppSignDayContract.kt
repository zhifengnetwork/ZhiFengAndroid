package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AppSignBean
import com.zf.mart.mvp.bean.AppSignDayBean

interface AppSignDayContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        //签到
        fun appSignSuccess(bean: AppSignBean)
        //签到列表
        fun getAppSignDay(bean: AppSignDayBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestAppSign()

        fun requestAppSignDay()
    }
}