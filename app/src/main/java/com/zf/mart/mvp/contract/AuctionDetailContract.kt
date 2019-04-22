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

        fun setBid()
    }

    interface Presenter : IPresenter<View> {

        //出价
        fun requestBid(id: String, price: String)

        fun requestAuctionDetail(id: String)

        fun requestAuctionPrice(id: String)

    }

}