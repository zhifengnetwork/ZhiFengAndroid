package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.CommendList

interface RecommendGoodsContract {
    interface View : IBaseView {
        fun showError(message: String, errorCode: Int)

        fun getRecommendGoods(bean: List<CommendList>)
    }

    interface Presenter : IPresenter<View> {
        fun requestRecommendGoods(id: String, sort_asc: String, page: Int)
    }

}