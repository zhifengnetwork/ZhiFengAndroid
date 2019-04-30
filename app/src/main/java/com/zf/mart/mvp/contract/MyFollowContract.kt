package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.MyFollowBean
import com.zf.mart.mvp.bean.MyFollowList

interface MyFollowContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        //商品关注列表
        fun getMyFollowSuccess(bean: MyFollowBean)
        //删除商品关注
        fun delCollectGoods(msg: String)
        fun freshEmpty()

        fun setLoadMore(bean: List<MyFollowList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)

        fun setBuyError(msg: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestMyFollow(page: Int?, num: Int)

        fun requestDelCollectGoods(goods_id: String)

    }
}