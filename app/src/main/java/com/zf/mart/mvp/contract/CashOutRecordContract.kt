package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.CashOutList

interface CashOutRecordContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getCashOutList(bean: List<CashOutList>)

        fun freshEmpty()

        fun setLoadMore(bean: List<CashOutList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestCashOutList(page: Int?, num: Int)
    }
}