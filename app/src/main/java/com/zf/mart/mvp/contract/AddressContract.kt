package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AddressBean
import com.zf.mart.mvp.bean.LoginBean

interface AddressContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun getAddress(bean: List<AddressBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestAddress()

    }

}