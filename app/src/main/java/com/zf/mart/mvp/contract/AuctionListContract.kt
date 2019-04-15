package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AuctionList

interface AuctionListContract {

    interface View : IBaseView {


        fun setAuctionList(bean: List<AuctionList>)

        fun setLoadMore(bean: List<AuctionList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestAuctionList(page: Int?)

    }

}