package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.ShopList

interface CartListContract {

    interface View : IBaseView {


        fun setRefreshCart(bean: List<ShopList>)

        fun setLoadMoreCart(bean: List<ShopList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestCartList(page:Int?)

    }

}