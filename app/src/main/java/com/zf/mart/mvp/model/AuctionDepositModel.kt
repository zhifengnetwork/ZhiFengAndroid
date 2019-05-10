package com.zf.mart.mvp.model

import com.zf.mart.base.BaseBean
import com.zf.mart.mvp.bean.WXPayBean
import com.zf.mart.net.RetrofitManager
import com.zf.mart.scheduler.SchedulerUtils
import io.reactivex.Observable

class AuctionDepositModel {


    fun requestDeposit(id: String): Observable<BaseBean<WXPayBean>> {
        return RetrofitManager.service.requestDeposit(id)
                .compose(SchedulerUtils.ioToMain())
    }
}