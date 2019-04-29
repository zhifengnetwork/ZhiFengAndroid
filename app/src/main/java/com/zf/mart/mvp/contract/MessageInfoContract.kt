package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.MessageInfo

interface MessageInfoContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMessageInfo(bean: MessageInfo)
    }

    interface Presenter : IPresenter<View> {
        fun requestMessageInfo(rec_id: String)
    }
}