package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.UserInfoBean

interface UserInfoContract {
    interface View : IBaseView {

        fun setUserInfo(bean:UserInfoBean)

        fun showError(msg: String, code: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestUserInfo()
    }
}