package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.AuctionDetailBean
import com.zf.mart.mvp.bean.AuctionPriceBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class AuctionDetailModel {
    fun getAuctionDetail(id: String): Observable<BaseBean<AuctionDetailBean>> {
        return RetrofitManager.service.getAuctionDetail(id)
                .compose(SchedulerUtils.ioToMain())
    }

    fun getAuctionPrice(id: String): Observable<BaseBean<AuctionPriceBean>> {
        return RetrofitManager.service.getAuctionPrice(id)
                .compose(SchedulerUtils.ioToMain())
    }
}