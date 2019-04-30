package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AccountDetailList

interface AccountDetailContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getAccountDetail(bean: List<AccountDetailList>)

        fun freshEmpty()

        fun setLoadMore(bean: List<AccountDetailList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)

        fun setBuyError(msg: String)
    }

    interface Presenter : IPresenter<View> {

        fun requestAccountDetail(type: String, page: Int?, num: Int)

    }
}