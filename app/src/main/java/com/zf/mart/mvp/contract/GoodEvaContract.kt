package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.GoodEvaList

interface GoodEvaContract {

    interface View : IBaseView {


        fun setGoodEva(bean: List<GoodEvaList>)

        fun setLoadMore(bean: List<GoodEvaList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestGoodEva(goodId:String,type:Int,page: Int?)

    }

}