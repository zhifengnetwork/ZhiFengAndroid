package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.GroupBean

interface GroupContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)

        fun setGroup(bean: List<GroupBean>)

        fun loadMore(bean: List<GroupBean>)

        fun freshEmpty()

        fun loadComplete()
    }

    interface Presenter : IPresenter<View> {
        fun requestGroup(page: Int?)

    }

}