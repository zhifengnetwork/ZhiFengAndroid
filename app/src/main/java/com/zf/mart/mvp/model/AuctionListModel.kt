package com.zf.mart.mvp.model

import com.zf.mart.api.UriConstant
import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.AuctionBean
import com.zf.mart.mvp.bean.ShopList
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class AuctionListModel {
    fun getAuctionList(page:Int): Observable<BaseBean<AuctionBean>> {
        return RetrofitManager.service.getAuctionList(page,UriConstant.PER_PAGE)
            .compose(SchedulerUtils.ioToMain())
    }
}