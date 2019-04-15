package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AuctionDetailBean

interface AuctionDetailContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setAuctionDetail(bean: AuctionDetailBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestAuctionDetail(id: String)

    }

}