package com.zf.mart.mvp.contract

import com.zf.mart.base.IBaseView
import com.zf.mart.base.IPresenter
import com.zf.mart.mvp.bean.FollowShopList
import com.zf.mart.mvp.bean.MyFollowShopBean

interface MyFollowShopContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        //获得关注店铺列表
        fun getMyFollowShop(bean: MyFollowShopBean)

        //添加或删除店铺
        fun delMyFollowShop(msg: String)

        fun freshEmpty()

        fun setLoadMore(bean: List<FollowShopList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)

        fun setBuyError(msg: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestMyFollowShop(page: Int?, num: Int)

        fun requestDelMyFollowShop(seller_id: String, type: String, collect_id: String)
    }
}