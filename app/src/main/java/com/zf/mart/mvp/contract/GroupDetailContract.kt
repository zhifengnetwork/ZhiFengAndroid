package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.GroupDetailBean

interface GroupDetailContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setGroupDetail(bean: GroupDetailBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestGroupDetail(id: String)

    }

}