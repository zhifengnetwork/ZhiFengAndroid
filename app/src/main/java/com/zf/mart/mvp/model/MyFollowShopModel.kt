package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.MyFollowShopBean
import com.zf.mart.mvp.bean.ShopListBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class MyFollowShopModel {
    fun getShopList(page: Int, num: Int, goodsnum: Int): Observable<BaseBean<ShopListBean>> {
        return RetrofitManager.service.getShopList(page, num, goodsnum).compose(SchedulerUtils.ioToMain())
    }

    fun getMyFollowShop(page: Int, num: Int): Observable<BaseBean<MyFollowShopBean>> {
        return RetrofitManager.service.getMyFollowShop(page, num).compose(SchedulerUtils.ioToMain())
    }

    fun delMyFollowShop(seller_id: String, type: String, collect_id: String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.delMyFollowShop(seller_id, type, collect_id).compose(SchedulerUtils.ioToMain())
    }
}