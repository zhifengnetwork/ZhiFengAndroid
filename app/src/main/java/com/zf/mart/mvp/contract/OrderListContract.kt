package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.OrderListBean

interface OrderListContract {

    interface View : IBaseView {

        fun loadMoreError(msg: String, errorCode: Int)

        fun showError(msg: String, errorCode: Int)

        fun setFinishRefresh(bean: List<OrderListBean>)

        fun setFinishLoadMore(bean: List<OrderListBean>)

        fun setEmptyOrder()

        fun setLoadComplete()

    }

    interface Presenter : IPresenter<View> {

        fun requestOrderList(type: String, page: Int?, keyWord: String)

    }

}