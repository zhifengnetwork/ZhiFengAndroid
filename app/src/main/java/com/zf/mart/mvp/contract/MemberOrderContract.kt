package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.MemberOrderList
import com.zf.mart.mvp.bean.MyMemberOrderBean

interface MemberOrderContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMenberOrder(bean: MyMemberOrderBean)

        fun freshEmpty()

        fun setLoadMore(bean: List<MemberOrderList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)


    }

    interface Presenter : IPresenter<View> {
        fun requestMemberOrder(page: Int?, next_user_id: String)
    }
}
