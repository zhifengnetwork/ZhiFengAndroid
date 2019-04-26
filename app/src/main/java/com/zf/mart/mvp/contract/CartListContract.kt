package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.CartBean

interface CartListContract {

    interface View : IBaseView {


        fun setRefreshCart(bean: CartBean)

        fun setLoadMoreCart(bean: CartBean)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestCartList(page: Int?)

    }

}