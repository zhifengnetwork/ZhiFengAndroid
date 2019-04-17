package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.SecKillTimeBean

interface SecKillTimeContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setSecKillTime(bean: SecKillTimeBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestSecKillTime()

    }

}