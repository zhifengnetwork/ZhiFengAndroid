package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.SecKillDetailBean

interface SecKillDetailContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setSecKillDetail(bean: SecKillDetailBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestSecKillDetail(id: String)

    }

}