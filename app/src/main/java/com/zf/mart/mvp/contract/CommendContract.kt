package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.AppSignBean
import com.zf.mart.mvp.bean.CartBean
import com.zf.mart.mvp.bean.CommendBean

interface CommendContract {

    interface View : IBaseView {


        fun setRefreshCommend(bean: CommendBean)

        fun setLoadMoreCommend(bean: CommendBean)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
        //签到
        fun appSignSuccess(bean: AppSignBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestCommend(type: String, page: Int?)

        fun requestAppSign()

    }

}