package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.MyMemberBean

interface MyMemberContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        fun getMyMember(bean: List<MyMemberBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestMyMember()
    }
}