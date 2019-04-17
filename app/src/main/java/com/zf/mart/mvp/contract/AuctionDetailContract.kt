package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AuctionDetailBean
import com.zf.mart.mvp.bean.AuctionPriceBean

interface AuctionDetailContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setAuctionDetail(bean: AuctionDetailBean)

        fun setAuctionPrice(bean: AuctionPriceBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestAuctionDetail(id: String)

        fun requestAuctionPrice(id:String)

    }

}