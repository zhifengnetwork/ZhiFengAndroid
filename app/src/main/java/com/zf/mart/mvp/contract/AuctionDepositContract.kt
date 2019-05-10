package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.WXPayBean

interface AuctionDepositContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setWXPay(bean: WXPayBean)

    }

    interface Presenter : IPresenter<View> {

        fun requestDeposit(id: String)

    }

}